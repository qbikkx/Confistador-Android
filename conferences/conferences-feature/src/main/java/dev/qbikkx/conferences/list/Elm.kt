package dev.qbikkx.conferences.list

import android.util.Log
import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.conferences.domain.GetConferencesUseCase
import dev.qbikkx.core.domain.Result
import dev.qbikkx.coreui.FlowRouter
import dev.qbikkx.coreui.elm.*
import io.reactivex.Observable

internal sealed class Message : ElmMessage {
    object Init : Message()
    object BackPressed : Message()
    object Refresh : Message()
    data class ConferencesResult(val status: Result.Status, val conferences: List<Conference>?) : Message()
}

internal sealed class SideEffect : ElmSideEffect {
    object LoadRequest : SideEffect()
    object BackPress : SideEffect()
}

internal data class ConfListViewModel(
    val isLoading: Boolean,
    val conferences: List<Conference>
) : ElmViewModel

internal data class ConfListState(
    val isLoadingConferences: Boolean,
    val conferences: List<Conference>
) : ElmStateModel {

    companion object {

        val initial: ConfListState
            get() = ConfListState(
                isLoadingConferences = false,
                conferences = emptyList()
            )
    }
}

internal class ConferencesListReducer : ElmReducer<ConfListState> {

    override fun reduce(state: ConfListState, message: ElmMessage): Pair<ConfListState, ElmSideEffect> {
        Log.d("WOW", "$message")
        return when (message) {
            Message.Init, Message.Refresh -> state to SideEffect.LoadRequest
            is Message.ConferencesResult -> onConferencesResult(message, state)
            Message.BackPressed -> state to SideEffect.BackPress
            else -> throw IllegalStateException("unknown message : $message")
        }
    }

    private fun onConferencesResult(
        message: Message.ConferencesResult,
        state: ConfListState
    ): Pair<ConfListState, ElmSideEffect> {
        return when (message.status) {
            Result.Status.Loading -> state.copy(
                isLoadingConferences = true,
                conferences = message.conferences ?: state.conferences
            )
            Result.Status.Success -> state.copy(
                isLoadingConferences = false,
                conferences = message.conferences!!
            )
            is Result.Status.Error -> state.copy(
                isLoadingConferences = false,
                conferences = message.conferences ?: state.conferences
            )
        } to None
    }
}

internal class ConferencesListStateMapper : StateViewMapper<ConfListState, ConfListViewModel> {

    override fun map(state: ConfListState): ConfListViewModel {
        return with(state) {
            ConfListViewModel(
                isLoading = isLoadingConferences,
                conferences = conferences
            )
        }
    }
}

internal class LoadConferencesMiddleware(
    private val getConferencesUseCase: GetConferencesUseCase
) : Middleware {

    override fun bind(effects: Observable<ElmSideEffect>): Observable<ElmMessage> {
        return effects.ofType(SideEffect.LoadRequest::class.java)
            .switchMap { getConferencesUseCase.execute() }
            .map { Message.ConferencesResult(status = it.status, conferences = it.data) }
    }
}

internal class NavigationMiddleware(
    private val flowRouter: FlowRouter
) : Middleware {

    override fun bind(effects: Observable<ElmSideEffect>): Observable<ElmMessage> {
        return effects.ofType(SideEffect.BackPress::class.java)
            .map {
                flowRouter.exit()
                Idle
            }
    }
}

internal class ConferencesFeatureStore(
    getConferencesUseCase: GetConferencesUseCase,
    flowRouter: FlowRouter
) : ElmStore<ConfListState, ConfListViewModel>(
    reducer = ConferencesListReducer(),
    middlewares = listOf(
        LoadConferencesMiddleware(getConferencesUseCase),
        NavigationMiddleware(flowRouter)
    ),
    stateMapper = ConferencesListStateMapper(),
    initialState = ConfListState.initial,
    initialMessage = Message.Init
)