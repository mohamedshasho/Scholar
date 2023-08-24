package com.scholar.center.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scholar.center.R
import com.scholar.center.databinding.RateBottomSheetBinding

class RateBottomSheetFragment(val onRate: (Int, String) -> Unit) :
    BottomSheetDialogFragment(R.layout.rate_bottom_sheet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(RateBottomSheetBinding.bind(view)) {
            profileUpdateBtn.setOnClickListener {
                onRate(reviewRatingBar.rating.toInt(), rateComment.text.toString())
                dismiss()
            }
        }
    }
}