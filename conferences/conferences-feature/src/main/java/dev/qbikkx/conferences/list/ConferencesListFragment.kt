package dev.qbikkx.conferences.list

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import dev.qbikkx.conferences.R
import dev.qbikkx.conferences.list.di.ConferencesListInitializer
import dev.qbikkx.coreui.BaseFragment
import dev.qbikkx.coreui.elm.ElmMessage
import dev.qbikkx.coreui.elm.MviView
import dev.qbikkx.coreui.toPx
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
            layoutManager = object : LinearLayoutManager(activity) {

            }
            adapter = conferencesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener { messagesRelay.accept(Message.Refresh) }
        recyclerView.addOnScrollListener( object : RecyclerView.OnScrollListener() {

            private val elevation: Float = 8.toPx()

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset = recyclerView.computeVerticalScrollOffset()
                if(offset != 0 && statusBarView.elevation == 0f) {
                    statusBarView.elevation = elevation
                } else if (offset == 0 && statusBarView.elevation != 0f){
                    statusBarView.elevation = 0f
                }
            }
        })
        elmContainer.bind(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        elmContainer.unbind()
    }

    override fun onBackPressed() = messagesRelay.accept(Message.BackPressed)

    override fun onApplyWindowInsets(insets: WindowInsets) {
        statusBarView.layoutParams.height = insets.systemWindowInsetTop
    }

    override fun render(viewModel: ConfListViewModel) {
        swipeRefreshLayout.isRefreshing = viewModel.isLoading
        conferencesAdapter.submitList(viewModel.conferences)
    }

    companion object {
        fun newInstance() = ConferencesListFragment()
    }
}