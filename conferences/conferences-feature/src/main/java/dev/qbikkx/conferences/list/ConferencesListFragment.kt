package dev.qbikkx.conferences.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxrelay2.PublishRelay
import dev.qbikkx.conferences.R
import dev.qbikkx.conferences.list.di.ConferencesListInitializer
import dev.qbikkx.coreui.BaseFragment
import dev.qbikkx.coreui.elm.MviView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_conferences_list.*
import javax.inject.Inject

internal class ConferencesListFragment : BaseFragment(), MviView<Message, ConfListViewModel> {

    override val layoutRes = R.layout.fragment_conferences_list

    private val messagesRelay = PublishRelay.create<Message>()

    override val messages: Observable<Message>
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
        elmContainer.bind(this)
        messagesRelay.accept(Message.Init)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        elmContainer.unbind()
    }

    override fun render(viewModel: ConfListViewModel) {
        conferencesAdapter.submitList(viewModel.conferences)
    }

    companion object {
        fun newInstance() = ConferencesListFragment()
    }
}