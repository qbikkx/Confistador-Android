package dev.qbikkx.coreui

import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_flow

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment

    @Inject
    lateinit var parentRouter: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(activity, childFragmentManager, R.id.fragment_container) {
            override fun activityBack() {
                parentRouter.exit()
            }

            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.slide_out_up,
                    0,
                    R.anim.back_stack_out
                )
            }
        }
    }

    abstract val flowKey: String

    abstract fun getLaunchScreen(): SupportAppScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(getLaunchScreen())
        }
    }
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