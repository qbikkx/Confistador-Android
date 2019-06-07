package dev.qbikkx.conferences.list

import dev.qbikkx.conferences.R
import dev.qbikkx.coreui.BaseFragment

internal class ConferencesListFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_conferences_list

    override fun setupDependencies() {

    }

    override fun subscribeToEvents() {

    }

    companion object {
        fun newInstance() = ConferencesListFragment()
    }
}