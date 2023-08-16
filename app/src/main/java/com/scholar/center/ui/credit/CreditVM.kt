package com.scholar.center.ui.credit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.R
import com.scholar.domain.model.CreditInputValidationType
import com.scholar.domain.model.CreditState
import com.scholar.domain.model.LoginInputValidationType
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import com.scholar.domain.usecase.ValidateCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditVM @Inject constructor(
    private val studentRepository: StudentRepository,
    private val dataStore: DataStorePreference,
    private val validateCreditUseCase: ValidateCreditUseCase
) : ViewModel() {

    var creditState = MutableStateFlow(CreditState())
        private set


    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()


    fun onAmountInputChanged(newValue: Int) {
        creditState.update { currentState ->
            currentState.copy(amountInput = newValue)
        }
        checkInputValidation()
    }

    fun onMethodInputChanged(newValue: Int) {
        creditState.update { currentState ->
            currentState.copy(methodInput = newValue)
        }
        checkInputValidation()
    }

    fun onLinkInputChanged(newValue: Int) {
        creditState.update { currentState ->
            currentState.copy(linkInput = newValue)
        }
        checkInputValidation()
    }

    fun onDescriptionInputChanged(newValue: String) {
        creditState.update { currentState ->
            currentState.copy(descriptionInput = newValue)
        }
    }


    private fun checkInputValidation() {
        val validation = validateCreditUseCase(
            amount = creditState.value.amountInput,
            method = creditState.value.methodInput,
            link = creditState.value.linkInput,
        )
        processInputValidationType(validation)
    }

    fun onSendClick() {
        if (!creditState.value.isInputValid) return
        if (creditState.value.isLoading) return

        creditState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
//            val result = studentRepository.contact(
//                name = contactState.value.nameInput,
//                email = contactState.value.emailInput,
//                phone = contactState.value.phoneInput,
//                subject = contactState.value.subjectInput
//            )
            when (result) {
                is Resource.Success -> {
                    result.data?.let { data ->
                        _message.value = data.message

                        creditState.update { currentState ->
                            currentState.copy(isSuccessfullySend = true, isLoading = false)
                        }
                    }
                }

                is Resource.Error -> {
                    creditState.update { currentState ->
                        currentState.copy(
                            errorMessageLoginProcess = result.message,
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    private fun processInputValidationType(type: CreditInputValidationType) {
        creditState.update { currentState ->
            when (type) {
                CreditInputValidationType.EmptyAmount -> {
                    currentState.copy(
                        errorMessageInput = R.string.empty_amount_left,
                        isInputValid = false
                    )
                }

                CreditInputValidationType.NoMethod -> {
                    currentState.copy(
                        errorMessageInput = R.string.no_select_method,
                        isInputValid = false
                    )
                }
                CreditInputValidationType.NoLink -> {
                    currentState.copy(
                        errorMessageInput = R.string.no_image_added,
                        isInputValid = false
                    )
                }
                CreditInputValidationType.Valid -> {
                    currentState.copy(errorMessageInput = null, isInputValid = true)
                }
            }
        }
    }


}