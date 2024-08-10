package com.afoxplus.invitation.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InvitationDetailViewModel @Inject constructor(
    private val repository: InvitationRepository,
    private val coroutineDispatcher: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mInvitation: MutableStateFlow<UIModelState> by lazy { MutableStateFlow(UIModelState.Loading) }
    private val mConfirmationActionState: MutableSharedFlow<UIActionState> by lazy { MutableSharedFlow() }
    private val mButtonEnable: MutableStateFlow<Boolean> by lazy { MutableStateFlow(true) }

    val confirmationActionState = mConfirmationActionState.asSharedFlow()
    val invitation = mInvitation.asStateFlow()
    val buttonEnable = mButtonEnable.asStateFlow()

    fun findInvitation(code: String, useLocal: Boolean) {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            mInvitation.value = repository.findByCode(code, useLocalCache = useLocal)?.let {
                UIModelState.Success(it)
            } ?: UIModelState.NoData
        }
    }

    fun confirmInvitation() {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            if (mInvitation.value is UIModelState.Success) {
                mConfirmationActionState.emit(UIActionState.Loading)
                val invitation = (mInvitation.value as UIModelState.Success).data
                val result = repository.setGuestUUID(invitation.id)
                val stateAction = if (result) UIActionState.Success else UIActionState.Failure
                mConfirmationActionState.emit(stateAction)
            }
        }
    }

    fun enableButton() {
        mButtonEnable.value = true
    }

    fun disableButton() {
        mButtonEnable.value = false
    }

    sealed interface UIModelState {
        object Loading : UIModelState
        object NoData : UIModelState
        data class Success(val data: Invitation) : UIModelState
    }

    sealed interface UIActionState {
        object Loading : UIActionState
        object Failure : UIActionState
        object Success : UIActionState
    }
}