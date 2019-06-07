package dev.qbikkx.conferences

import androidx.fragment.app.Fragment
import dev.qbikkx.conferences.list.ConferencesListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ConferencesFlow : SupportAppScreen() {

    override fun getFragment(): Fragment = ConferencesFlowFragment.newInstance()
}

class ConferencesListScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = ConferencesListFragment.newInstance()
}