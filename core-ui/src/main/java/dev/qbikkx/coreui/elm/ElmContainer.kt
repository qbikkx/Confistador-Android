package dev.qbikkx.coreui.elm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

open class ElmContainer<S : ElmStateModel, V : ElmViewModel>(
    private val store: ElmStore<S, V>
) : ViewModel() {

    private val wiring = store.wire()

    private val viewModelStream: Disposable? = null

    private var viewBinding: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        wiring.dispose()
    }

    fun bind(view: MviView<V>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
        viewModelStream?.dispose()
    }
}