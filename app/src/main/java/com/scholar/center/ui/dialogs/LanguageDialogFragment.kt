package com.scholar.center.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.scholar.center.R
import com.scholar.center.databinding.LanguageDialogBinding
import java.util.Locale

class LanguageDialogFragment(val setLanguage: (Locale) -> Unit) :
    DialogFragment(R.layout.language_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(LanguageDialogBinding.bind(view)) {
            btnApply.setOnClickListener {
                if (radioArabic.isChecked) {
                    setLanguage(Locale("ar", "AE"))
                } else if (radioEnglish.isChecked) {
                    setLanguage(Locale("en", "USA"))
                }
                dismiss()
            }
        }
    }
}