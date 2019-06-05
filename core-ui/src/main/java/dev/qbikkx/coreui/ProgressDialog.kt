package dev.qbikkx.coreui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class ProgressDialog : DialogFragment() {

    override fun getTheme() = R.style.ProgressDialogTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        LayoutInflater.from(context).inflate(R.layout.fragment_progress, null)

}