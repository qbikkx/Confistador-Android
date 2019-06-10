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

interface Message

object Idle : Message

interface SideEffect

object None : SideEffect

interface StateModel

interface ViewModel

interface MviView<M : Message, V : ViewModel> {
    val messages: Observable<M>
    fun render(viewModel: V)
}

interface Reducer<S : StateModel, M : Message, E : SideEffect> {
    fun reduce(state: S, message: M): Pair<S, E>
}

interface Middleware<E : SideEffect, M : Message> {
    fun bind(effects: Observable<E>): Observable<M>
}

interface StateViewMapper<S : StateModel, V : ViewModel> {
    fun map(state: S): V
}

class Store<S : StateModel, M : Message, E : SideEffect, V : ViewModel>(
    private val reducer: Reducer<S, M, E>,
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