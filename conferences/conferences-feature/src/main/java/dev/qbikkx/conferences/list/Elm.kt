package dev.qbikkx.conferences.list

import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.conferences.domain.GetConferencesUseCase
import dev.qbikkx.core.domain.Result
import dev.qbikkx.coreui.elm.*
import io.reactivex.Observable

internal sealed class Message : ElmMessage {
    object Init : Message()
    object Refresh : Message()
    data class ConferencesResult(val status: Result.Status, val conferences: List<Conference>?) : Message()
}

internal sealed class SideEffect : ElmSideEffect {
    object LoadRequest : SideEffect()
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

internal class ConferencesListReducer : ElmReducer<ConfListState, Message, ElmSideEffect> {

    override fun reduce(state: ConfListState, message: Message): Pair<ConfListState, ElmSideEffect> {
        return when (message) {
            Message.Init, Message.Refresh -> state to SideEffect.LoadRequest
            is Message.ConferencesResult -> onConferencesResult(message, state)
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
                isLoadingConferences = false
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
) : Middleware<ElmSideEffect, Message> {

    override fun bind(effects: Observable<ElmSideEffect>): Observable<Message> {
        return effects.ofType(SideEffect.LoadRequest::class.java)
            .switchMap { getConferencesUseCase.execute() }
            .map { Message.ConferencesResult(status = it.status, conferences = it.data) }
    }
}

internal class ConferencesFeatureStore(
    getConferencesUseCase: GetConferencesUseCase
) : ElmStore<ConfListState, Message, ElmSideEffect, ConfListViewModel>(
    reducer = ConferencesListReducer(),
    middlewares = listOf(LoadConferencesMiddleware(getConferencesUseCase)),
    stateMapper = ConferencesListStateMapper(),
    initialState = ConfListState.initial
)