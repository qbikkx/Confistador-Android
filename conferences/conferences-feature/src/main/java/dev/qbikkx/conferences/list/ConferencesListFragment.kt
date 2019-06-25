package dev.qbikkx.conferences.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxrelay2.PublishRelay
import dev.qbikkx.conferences.R
import dev.qbikkx.conferences.list.di.ConferencesListInitializer
import dev.qbikkx.coreui.BaseFragment
import dev.qbikkx.coreui.elm.ElmMessage
import dev.qbikkx.coreui.elm.MviView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_conferences_list.*
import javax.inject.Inject

internal class ConferencesListFragment : BaseFragment(), MviView<ConfListViewModel> {

    override val layoutRes = R.layout.fragment_conferences_list

    private val messagesRelay = PublishRelay.create<ElmMessage>()

    override val messages: Observable<ElmMessage>
        get() = messagesRelay

    @Inject
    lateinit var elmContainer: ConferencesListElmContainer

    private val conferencesAdapter = ConferencesAdapter()

    override fun setupDependencies() = ConferencesListInitializer.init(this).inject(this)

    override fun subscribeToEvents() {}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = conferencesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener { messagesRelay.accept(Message.Refresh) }
        elmContainer.bind(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        elmContainer.unbind()
    }

    override fun onBackPressed() = messagesRelay.accept(Message.BackPressed)

    override fun render(viewModel: ConfListViewModel) {
        swipeRefreshLayout.isRefreshing = viewModel.isLoading
        conferencesAdapter.submitList(viewModel.conferences)
    }

    companion object {
        fun newInstance() = ConferencesListFragment()
    }
}