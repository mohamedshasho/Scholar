package com.scholar.center.ui.teachers.teacher.qualifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.scholar.center.R
import com.scholar.center.databinding.FragmentTeacherQualificationsBinding
import com.scholar.domain.model.Teacher

class QualificationsFragment(val teacher: Teacher) :
    Fragment(R.layout.fragment_teacher_qualifications) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeacherQualificationsBinding.bind(view)

        binding.teacherQualificationsEducation.text = teacher.education
        binding.teacherQualifications.text = teacher.qualifications

    }
}