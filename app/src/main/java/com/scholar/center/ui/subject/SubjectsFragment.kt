package com.scholar.center.ui.subject

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.scholar.center.R


class SubjectsFragment : Fragment(R.layout.fragment_subjects) {


    private val viewModel: SubjectsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewModel.getN()
    }

}