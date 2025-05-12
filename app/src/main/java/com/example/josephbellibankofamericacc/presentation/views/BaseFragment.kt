package com.example.josephbellibankofamericacc.presentation.views

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.josephbellibankofamericacc.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    fun showError(error: String, actionRetry: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.error_dialog_title)
            .setMessage(error)
            .setPositiveButton(R.string.error_positive_button_text) { _, _ ->
                actionRetry()
            }
            .create()
            .show()
    }

}