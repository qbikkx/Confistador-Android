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

interface MviView<V : ElmViewModel> {
    val messages: Observable<ElmMessage>
    fun render(viewModel: V)
}

interface ElmReducer<S : ElmStateModel> {
    fun reduce(state: S, message: ElmMessage): Pair<S, ElmSideEffect>
}

interface Middleware {
    fun bind(effects: Observable<ElmSideEffect>): Observable<ElmMessage>
}

interface StateViewMapper<S : ElmStateModel, V : ElmViewModel> {
    fun map(state: S): V
}

open class ElmStore<S : ElmStateModel, V : ElmViewModel>(
    private val reducer: ElmReducer<S>,
    private val middlewares: List<Middleware>,
    private val stateMapper: StateViewMapper<S, V>,
    initialState: S,
    private val initialMessage: ElmMessage?,
    private val execScheduler: Scheduler = Schedulers.from(Executors.newSingleThreadExecutor()),
    private val renderScheduler: Scheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
) {

    private val state = BehaviorRelay.createDefault(initialState)

    private val sideEffects = PublishRelay.create<ElmSideEffect>()

    private val messages = PublishRelay.create<ElmMessage>()

    private val viewModel = BehaviorRelay.create<V>()

    fun wire(): Disposable {
        return CompositeDisposable().apply {
            this += messages
                .filter { message -> message !is Idle }
                .subscribeOn(execScheduler)
                .withLatestFrom<ElmMessage, S, Pair<S, ElmSideEffect>>(state) { message: ElmMessage, state: S ->
                    reducer.reduce(state, message)
                }
                .doOnNext { (newState, sideEffect): Pair<S, ElmSideEffect> ->
                    if (sideEffect !is None) {
                        sideEffects.accept(sideEffect)
                    }
                    state.accept(newState)
                }
                .map { (newState, _): Pair<S, ElmSideEffect> -> stateMapper.map(newState) }
                .distinctUntilChanged()
                .subscribe(viewModel::accept)
            this += Observable.merge(middlewares.map { it.bind(sideEffects) })
                .cast(ElmMessage::class.java)
                .observeOn(execScheduler)
                .subscribe { messages.accept(it) }
            initialMessage?.also { messages.accept(it) }
        }
    }

    fun bind(view: MviView<V>): Disposable {
        return CompositeDisposable().apply {
            this += view.messages.observeOn(execScheduler).subscribe { messages.accept(it) }
            this += viewModel.observeOn(renderScheduler).subscribe(view::render)
        }
    }
}