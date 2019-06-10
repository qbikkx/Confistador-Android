package dev.qbikkx.coreui.elm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

class ElmContainer<S : StateModel, M : Message, E : SideEffect, V : dev.qbikkx.coreui.elm.ViewModel>(
    private val store: Store<S, M, E, V>
) : ViewModel() {

    private val wiring = store.wire()

    private val viewModelStream: Disposable? = null

    private var viewBinding: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        wiring.dispose()
    }

    fun bind(view: MviView<M, V>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
        viewModelStream?.dispose()
    }
}