package com.scholar.center.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.scholar.center.R
import com.scholar.center.databinding.ConfirmDialogBinding

class ConfirmDialog(val confirm: () -> Unit) : DialogFragment(R.layout.confirm_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ConfirmDialogBinding.bind(view)

        binding.okButton.setOnClickListener {
            confirm()
            dismiss()
        }
        binding.closeButton.setOnClickListener { dismiss() }
    }

}