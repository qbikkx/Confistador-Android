package dev.qbikkx.coreui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment


public const val PROGRESS_TAG = "progress_dialog"

/**
 * Base {@link Fragment} implementation that defines common behavior for app Fragments.
 */
abstract class BaseFragment : Fragment() {

    abstract val layoutRes: LayoutResourceId

    private var instanceStateSaved: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupDependencies()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
    }

    /**
     * The method tells us to setup needed dependencies by injecting specific graphs.
     */
    protected abstract fun setupDependencies()

    protected abstract fun subscribeToEvents()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToEvents()
    }

    protected fun initToolbar(toolbar: Toolbar){
        val activity = activity as BaseActivity
        activity.initActionBar(toolbar)
    }

    protected fun showProgressDialog(progress: Boolean) {
        if (!isAdded || instanceStateSaved) return

        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager, PROGRESS_TAG)
            childFragmentManager.executePendingTransactions()
        }
    }

    open fun onBackPressed() {}

    open fun updateInsets(insets: WindowInsetsCompat?): WindowInsetsCompat? {
        return insets
    }
}