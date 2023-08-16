package com.scholar.center.ui.credit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.R
import com.scholar.domain.model.CreditInputValidationType
import com.scholar.domain.model.CreditState
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import com.scholar.domain.usecase.ValidateCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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


    private val _selectedUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val selectedUri = _selectedUri.asStateFlow()


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

    fun onLinkInputChanged(newValue: String) {
        creditState.update { currentState ->
            currentState.copy(linkInput = newValue)
        }
        checkInputValidation()
    }


    fun setUri(uri: Uri) {
        _selectedUri.value = uri
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

            val studentId = dataStore.readValue(DataStorePreference.userId).first()
            if (studentId == null || studentId == 0) {
                creditState.update { currentState ->
                    currentState.copy(errorMessageInput = R.string.please_sign_in)
                }
                return@launch
            } else {


                val result = studentRepository.purchaseCredit(
                    filePath = creditState.value.linkInput,
                    amount = creditState.value.amountInput,
                    paymentMethod = creditState.value.methodInput,
                    description = creditState.value.descriptionInput,
                    studentId = studentId
                )
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { data ->
                            _message.value = data
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