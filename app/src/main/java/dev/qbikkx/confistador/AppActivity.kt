package dev.qbikkx.confistador

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dev.qbikkx.conferences.ConferencesFlow
import dev.qbikkx.confistador.di.AppActivityInitializer
import dev.qbikkx.coreui.BaseActivity
import dev.qbikkx.coreui.BaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class AppActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_app

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun setupDependencies() = AppActivityInitializer.init().inject(this)

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.app_fragment_container) as? BaseFragment

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(this, supportFragmentManager, R.id.app_fragment_container) {

            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setCustomAnimations(
                    dev.qbikkx.coreui.R.anim.slide_in_up,
                    dev.qbikkx.coreui.R.anim.slide_out_up,
                    0,
                    dev.qbikkx.coreui.R.anim.back_stack_out
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: router.newRootScreen(ConferencesFlow())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}
