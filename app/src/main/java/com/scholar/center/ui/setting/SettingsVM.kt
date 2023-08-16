package com.scholar.center.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.model.Student
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    private val dataStore: DataStorePreference,
    private val studentRepository: StudentRepository,
) : ViewModel() {

    private val _student = MutableStateFlow<Student?>(null)
    val student = _student.asStateFlow()

    private val _isStudentLoggedIn = MutableStateFlow(false)
    val isStudentLoggedIn = _isStudentLoggedIn.asStateFlow()

    init {
        checkAuth()
        getStudent()
    }

    fun changeLanguage(lan: String) {
        viewModelScope.launch {
            dataStore.saveValue(DataStorePreference.language, lan)
        }
    }

    private fun checkAuth() {
        viewModelScope.launch {
            dataStore.readValue(DataStorePreference.isUserLoggedIn).collect {
                _isStudentLoggedIn.value = it ?: false
            }
        }
    }

    private fun getStudent() {
        viewModelScope.launch {
            dataStore.readValue(DataStorePreference.userId).collect { studentId ->
                studentId?.let {
                    _student.value = studentRepository.getStudent(it).first()
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.saveValue(DataStorePreference.userId, 0)
            dataStore.saveValue(DataStorePreference.isUserLoggedIn, false)
        }
    }

}