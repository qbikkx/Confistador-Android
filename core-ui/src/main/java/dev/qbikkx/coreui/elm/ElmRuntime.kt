package dev.qbikkx.coreui.elm

import android.os.Looper
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

interface ElmMessage

object Idle : ElmMessage

interface ElmSideEffect

object None : ElmSideEffect

interface ElmStateModel

interface ElmViewModel

interface MviView<M : ElmMessage, V : ElmViewModel> {
    val messages: Observable<M>
    fun render(viewModel: V)
}

interface ElmReducer<S : ElmStateModel, M : ElmMessage, E : ElmSideEffect> {
    fun reduce(state: S, message: M): Pair<S, E>
}

interface Middleware<E : ElmSideEffect, M : ElmMessage> {
    fun bind(effects: Observable<E>): Observable<M>
}

interface StateViewMapper<S : ElmStateModel, V : ElmViewModel> {
    fun map(state: S): V
}

open class ElmStore<S : ElmStateModel, M : ElmMessage, E : ElmSideEffect, V : ElmViewModel>(
    private val reducer: ElmReducer<S, M, E>,
    private val middlewares: List<Middleware<E, M>>,
    private val stateMapper: StateViewMapper<S, V>,
    initialState: S,
    private val execScheduler: Scheduler = Schedulers.from(Executors.newSingleThreadExecutor()),
    private val renderScheduler: Scheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
) {

    private val state = BehaviorRelay.createDefault(initialState)

    private val sideEffects = PublishRelay.create<E>()

    private val messages = PublishRelay.create<M>()

    private val viewModel = BehaviorRelay.create<V>()

    fun wire(): Disposable {
        return CompositeDisposable().apply {
            this += messages
                .filter { message -> message !is Idle }
                .subscribeOn(execScheduler)
                .withLatestFrom(state) { message, state -> reducer.reduce(state, message) }
                .doOnNext { (newState, sideEffect) ->
                    if (sideEffect !is None) {
                        sideEffects.accept(sideEffect)
                    }
                    state.accept(newState)
                }
                .map { (newState, _) -> stateMapper.map(newState) }
                .distinctUntilChanged()
                .subscribe(viewModel::accept)
            this += Observable.merge(middlewares.map { it.bind(sideEffects) }).subscribe(messages::accept)
        }
    }

    fun bind(view: MviView<M, V>): Disposable {
        return CompositeDisposable().apply {
            this += view.messages.observeOn(execScheduler).subscribe(messages::accept)
            this += viewModel.observeOn(renderScheduler).subscribe(view::render)
        }
    }
}