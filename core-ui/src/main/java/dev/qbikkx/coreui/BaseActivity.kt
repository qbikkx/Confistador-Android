package dev.qbikkx.coreui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * Base [AppCompatActivity] implementation that defines common behavior for app Activities.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutResId: Int

    protected abstract fun setupDependencies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
        setContentView(layoutResId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else              -> super.onOptionsItemSelected(item)
        }
    }

    fun initActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}