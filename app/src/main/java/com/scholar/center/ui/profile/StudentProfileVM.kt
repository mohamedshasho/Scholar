package com.scholar.center.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.R
import com.scholar.domain.model.LoginInputValidationType
import com.scholar.domain.model.LoginState
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StudentState
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import com.scholar.domain.usecase.ValidateStudentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentProfileVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studentRepository: StudentRepository,
    private val dataStore: DataStorePreference,
    private val validate: ValidateStudentUseCase
) : ViewModel() {

    var studentState = MutableStateFlow(StudentState())
        private set

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.readValue(DataStorePreference.userId).collect { id ->
                if (id != null) {
                    studentRepository.getStudent(id).collect { student ->
                        if (student != null)
                            studentState.update { currentState ->
                                currentState.copy(
                                    fullNameInput = student.fullName,
                                    studentIdInput = student.id,
                                    image = student.image ?: "",
                                    emailInput = student.email ?: ""
                                )
                            }
                    }
                }
            }
        }
    }

    fun onNameInputChanged(newValue: String) {
        studentState.update { currentState ->
            currentState.copy(fullNameInput = newValue)
        }
        checkInputValidation()
    }

    fun onPhoneInputChanged(newValue: String) {
        studentState.update { currentState ->
            currentState.copy(phoneInput = newValue)
        }
        checkInputValidation()
    }

    fun onImageInputChanged(newValue: String) {
        studentState.update { currentState ->
            currentState.copy(imageInput = newValue)
        }
        checkInputValidation()
    }

    fun onBrithInputChanged(newValue: String) {
        studentState.update { currentState ->
            currentState.copy(brithInput = newValue)
        }
        checkInputValidation()
    }


    private fun checkInputValidation() {
        val validation = validate(
            name = studentState.value.fullNameInput,
            phone = studentState.value.phoneInput,
            image = studentState.value.imageInput,
            brith = studentState.value.brithInput
        )
        processInputValidationType(validation)
    }

    fun onUpdateClick() {
        if (!studentState.value.isInputValid) return
        if (studentState.value.isLoading) return

        studentState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            studentState.value.studentIdInput?.let { id ->
                val result = studentRepository.update(
                    studentId = id,
                    phone = studentState.value.phoneInput,
                    birth = studentState.value.brithInput,
                    fullName = studentState.value.fullNameInput,
                    imagePath = studentState.value.imageInput,
                )
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { message ->
                            _message.value = message
                            studentState.update { currentState ->
                                currentState.copy(isSuccessfullyLogin = true, isLoading = false)
                            }
                        }
                    }

                    is Resource.Error -> {
                        studentState.update { currentState ->
                            currentState.copy(
                                errorMessageLoginProcess = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun processInputValidationType(type: LoginInputValidationType) {
        studentState.update { currentState ->
            when (type) {
                LoginInputValidationType.EmptyField -> {
                    currentState.copy(
                        errorMessageInput = R.string.empty_fields_left,
                        isInputValid = false
                    )
                }

                LoginInputValidationType.NonEmail -> {
                    currentState.copy(
                        errorMessageInput = R.string.no_valid_email,
                        isInputValid = false
                    )
                }

                LoginInputValidationType.Valid -> {
                    currentState.copy(errorMessageInput = null, isInputValid = true)
                }
            }
        }
    }

}