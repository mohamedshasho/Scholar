package com.scholar.center.ui.teachers.teacher

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.scholar.center.R
import com.scholar.center.adapter.TeacherPagerAdapter
import com.scholar.center.databinding.FragmentTeacherBinding
import com.scholar.center.ui.teachers.teacher.materials.TeacherMaterialsFragment
import com.scholar.center.ui.teachers.teacher.personal.PersonalFragment
import com.scholar.center.ui.teachers.teacher.qualifications.QualificationsFragment
import com.scholar.center.unit.Constants.BASE_URL
import com.scholar.center.unit.Constants.TEACHER_ID_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherFragment : Fragment(R.layout.fragment_teacher) {

    private val viewModel: TeacherVM by viewModels()

    private lateinit var binding: FragmentTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTeacherBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val teacherID = arguments?.getInt(TEACHER_ID_KEY)
        binding.teacherProfile.transitionName = teacherID.toString()

        binding.teacherToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
        val tabLayout = binding.teacherTabLayout
        val viewPager = binding.teacherViewPager
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.teacher.collectLatest { teacher ->
                    teacher?.let {

                        val fragments = listOf(
                            PersonalFragment(it), QualificationsFragment(it),
                            TeacherMaterialsFragment()
                        )
                        viewPager.adapter = TeacherPagerAdapter(
                            fragments = fragments,
                            fragmentActivity = requireActivity()
                        )
                        Glide.with(requireContext()).load("${BASE_URL}${it.image}")
                            .placeholder(R.drawable.ic_person)
                            .into(binding.teacherProfile)

                        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                            tab.text = when (position) {
                                0 -> getString(R.string.personal)
                                1 -> getString(R.string.qualifications)
                                2 -> getString(R.string.materials)
                                else -> throw IllegalArgumentException("Invalid position: $position")
                            }
                        }.attach()

                    }
                }
            }
        }


    }
}