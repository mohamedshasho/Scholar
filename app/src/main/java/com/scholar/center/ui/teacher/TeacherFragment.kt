package com.scholar.center.ui.teacher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.scholar.center.R
import com.scholar.center.adapter.TeacherPagerAdapter
import com.scholar.center.databinding.FragmentTeacherBinding
import com.scholar.center.ui.teacher.materials.TeacherMaterialsFragment
import com.scholar.center.ui.teacher.personal.PersonalFragment
import com.scholar.center.ui.teacher.qualifications.QualificationsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherFragment : Fragment(R.layout.fragment_teacher) {

    private val viewModel: TeacherVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeacherBinding.bind(view)

        binding.teacherToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.teacher.collectLatest {
                    binding.teacherToolbarTitle.text = it
                }
            }
        }
        val tabLayout = binding.teacherTabLayout
        val viewPager = binding.teacherViewPager

        val fragments = listOf(
            PersonalFragment(), QualificationsFragment(),
            TeacherMaterialsFragment()
        )
        viewPager.adapter = TeacherPagerAdapter(
            fragments = fragments,
            fragmentActivity = requireActivity()
        )

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