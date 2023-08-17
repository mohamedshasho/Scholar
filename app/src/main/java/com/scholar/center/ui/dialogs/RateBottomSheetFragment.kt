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
        val binding = RateBottomSheetBinding.bind(view)


        binding.reviewRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Handle the new rating value
            // For example, you can show a toast message
            // with the new rating value
            Toast.makeText(requireContext(), rating.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.profileUpdateBtn.setOnClickListener {
            onRate(binding.reviewRatingBar.rating.toInt(), binding.rateComment.toString())
            dismiss()
        }
    }
}