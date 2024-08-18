package com.afoxplus.invitation.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.invitation.R
import com.afoxplus.invitation.delivery.events.InvitationToRestaurantEvent
import com.afoxplus.invitation.delivery.models.StrategyInvitationDetail
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
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
    private val eventBus: UIKitEventBusWrapper,
    private val coroutineDispatcher: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mInvitation: MutableStateFlow<UIModelState> by lazy { MutableStateFlow(UIModelState.Loading) }
    private val mConfirmationActionState: MutableSharedFlow<UIActionState> by lazy { MutableSharedFlow() }
    private val mButtonEnable: MutableStateFlow<Boolean> by lazy { MutableStateFlow(true) }
    private val mStrategyScreen: MutableStateFlow<StrategyInvitationDetail> by lazy {
        MutableStateFlow(StrategyInvitationDetail.CARTA)
    }
    private val mButtonName: MutableStateFlow<Int> by lazy { MutableStateFlow(R.string.invitation_new_screen_button_confirm_title) }

    val buttonName = mButtonName.asStateFlow()
    val confirmationActionState = mConfirmationActionState.asSharedFlow()
    val invitation = mInvitation.asStateFlow()
    val buttonEnable = mButtonEnable.asStateFlow()

    fun setStrategyScreen(strategyInvitationDetail: StrategyInvitationDetail) {
        mStrategyScreen.value = strategyInvitationDetail
        mButtonName.value = when (strategyInvitationDetail) {
            StrategyInvitationDetail.CONFIRM -> R.string.invitation_new_screen_button_confirm_title
            StrategyInvitationDetail.CARTA -> R.string.invitation_new_screen_button_carta_title
        }
    }

    fun findInvitation(code: String, useLocal: Boolean) {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            mInvitation.value = repository.findByCode(code, useLocalCache = useLocal)?.let {
                UIModelState.Success(it)
            } ?: UIModelState.NoData
        }
    }

    fun onClickButton() {
        when (mStrategyScreen.value) {
            StrategyInvitationDetail.CONFIRM -> confirmInvitation()
            StrategyInvitationDetail.CARTA -> goToRestaurant()
        }
    }

    private fun confirmInvitation() {
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

    private fun goToRestaurant() {
        viewModelScope.launch(coroutineDispatcher.getMainDispatcher()) {
            val invitation = (mInvitation.value as UIModelState.Success).data
            eventBus.send(
                InvitationToRestaurantEvent(
                    tableId = invitation.table,
                    restaurantId = invitation.restaurantId,
                    guestName = invitation.guest
                )
            )
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