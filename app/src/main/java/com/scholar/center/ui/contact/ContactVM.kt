package com.scholar.center.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.R
import com.scholar.domain.model.ContactState
import com.scholar.domain.model.LoginInputValidationType
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.TeacherRepository
import com.scholar.domain.usecase.ValidateContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactVM @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val validateContactUseCase: ValidateContactUseCase,
) : ViewModel(

) {
    var contactState = MutableStateFlow(ContactState())
        private set


    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()


    fun onNameInputChanged(newValue: String) {
        contactState.update { currentState ->
            currentState.copy(nameInput = newValue)
        }
        checkInputValidation()
    }

    fun onEmailInputChanged(newValue: String) {
        contactState.update { currentState ->
            currentState.copy(emailInput = newValue)
        }
        checkInputValidation()
    }

    fun onPhoneInputChanged(newValue: String) {
        contactState.update { currentState ->
            currentState.copy(phoneInput = newValue)
        }
        checkInputValidation()
    }

    fun onSubjectInputChanged(newValue: String) {
        contactState.update { currentState ->
            currentState.copy(subjectInput = newValue)
        }
        checkInputValidation()
    }


    private fun checkInputValidation() {
        val validation = validateContactUseCase(
            name = contactState.value.nameInput,
            email = contactState.value.emailInput,
            phone = contactState.value.phoneInput,
            subject = contactState.value.subjectInput
        )
        processInputValidationType(validation)
    }

    fun onSendClick() {
        if (!contactState.value.isInputValid) return
        if (contactState.value.isLoading) return

        contactState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = teacherRepository.contact(
                name = contactState.value.nameInput,
                email = contactState.value.emailInput,
                phone = contactState.value.phoneInput,
                subject = contactState.value.subjectInput
            )
            when (result) {
                is Resource.Success -> {
                    result.data?.let { data ->
                        _message.value = data.message

                        contactState.update { currentState ->
                            currentState.copy(isSuccessfullySend = true, isLoading = false)
                        }
                    }
                }

                is Resource.Error -> {
                    contactState.update { currentState ->
                        currentState.copy(
                            errorMessageLoginProcess = result.message,
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    private fun processInputValidationType(type: LoginInputValidationType) {
        contactState.update { currentState ->
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