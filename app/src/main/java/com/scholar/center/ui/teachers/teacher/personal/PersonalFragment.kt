package com.scholar.center.ui.teachers.teacher.personal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.scholar.center.R
import com.scholar.center.databinding.FragmentTeacherPersonalBinding
import com.scholar.domain.model.Teacher

class PersonalFragment(val teacher: Teacher) : Fragment(R.layout.fragment_teacher_personal) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentTeacherPersonalBinding.bind(view)) {
            teacherPersonalName.text = teacher.fullName
            teacherPersonalBio.text = teacher.bio
            teacherPersonalPhone.text = teacher.phone
            teacherPersonalEmail.text = teacher.email
            teacher.birth?.let {
                teacherPersonalAge.text = it.toString()
            }
        }
    }
}