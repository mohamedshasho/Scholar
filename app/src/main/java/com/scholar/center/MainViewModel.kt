package com.scholar.center


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject
constructor(
    private val dataStore: DataStorePreference,
    private val studentRepository: StudentRepository,
) : ViewModel() {

    var startDestination = MutableStateFlow<Int?>(null)
        private set

    init {
        viewModelScope.launch {
            val isFirstOpen =
                dataStore.readValue(DataStorePreference.isAppFirstOpen).first() ?: true
            startDestination.value = if (isFirstOpen) {
                R.id.loginFragment
            } else {
                R.id.mainFragment
            }

            val studentId = dataStore.readValue(DataStorePreference.userId).first()
            if (studentId != null && studentId > 0) {
                studentRepository.syncStudent(studentId)
            }
        }
    }
}