package dev.qbikkx.conferences

import dev.qbikkx.conferences.di.ConferencesFlowComponent
import dev.qbikkx.conferences.di.ConferencesFlowInitializer
import dev.qbikkx.coreui.FlowFragment

internal class ConferencesFlowFragment : FlowFragment() {

    override fun getLaunchScreen() = ConferencesListScreen()

    override val flowKey = "conferences_flow"

    internal val component: ConferencesFlowComponent by lazy {
        ConferencesFlowInitializer.init(this)
    }

    override fun setupDependencies() = component.inject(this)

    override fun subscribeToEvents() {}

    companion object {
        fun newInstance() = ConferencesFlowFragment()
    }
}