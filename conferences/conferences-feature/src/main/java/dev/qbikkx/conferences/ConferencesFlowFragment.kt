package dev.qbikkx.conferences

import dev.qbikkx.conferences.di.ConferencesFlowInitializer
import dev.qbikkx.coreui.FlowFragment

internal class ConferencesFlowFragment : FlowFragment() {

    override fun getLaunchScreen() = ConferencesListScreen()

    override val flowKey = "conferences_flow"

    override fun setupDependencies() =
        ConferencesFlowInitializer.init(this).inject(this)

    override fun subscribeToEvents() {}

    companion object {
        fun newInstance() = ConferencesFlowFragment()
    }
}