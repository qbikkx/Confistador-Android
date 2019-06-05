package dev.qbikkx.coreui

import androidx.core.view.WindowInsetsCompat
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_flow

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment

    @Inject
    internal lateinit var flowRouter: FlowRouter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    //TODO
    abstract val navigator: Navigator

    abstract val flowKey: String

    abstract fun getLaunchScreen(): SupportAppScreen

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun updateInsets(insets: WindowInsetsCompat?): WindowInsetsCompat? {
        return currentFragment?.updateInsets(insets) ?: insets
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}