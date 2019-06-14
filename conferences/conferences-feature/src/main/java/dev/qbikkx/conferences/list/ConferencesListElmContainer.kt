package dev.qbikkx.conferences.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.qbikkx.coreui.elm.ElmContainer
import dev.qbikkx.coreui.elm.ElmSideEffect
import javax.inject.Inject

internal class ConferencesListElmContainer(
    store: ConferencesFeatureStore
) : ElmContainer<ConfListState, Message, ElmSideEffect, ConfListViewModel>(store) {

    init {
        store.wire()
    }
    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val store: ConferencesFeatureStore
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(ConferencesListElmContainer::class.java) ->
                    ConferencesListElmContainer(
                        store
                    ) as T
                else -> throw IllegalArgumentException("Cannot create instance of ${modelClass.simpleName}")
            }
        }
    }
}