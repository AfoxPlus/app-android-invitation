package com.afoxplus.invitation.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import com.afoxplus.uikit.views.status.ListLoading
import com.afoxplus.uikit.views.status.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MyInvitationViewModel @Inject constructor(
    private val repository: InvitationRepository,
    private val coroutineDispatcher: UIKitCoroutineDispatcher
) :
    ViewModel() {

    private val mInvitationsState: MutableStateFlow<ListState<Invitation>> by lazy {
        MutableStateFlow(ListLoading())
    }
    private val mInvitationState: MutableStateFlow<UIModelState<Invitation>> by lazy {
        MutableStateFlow(UIModelState.Loading())
    }

    val invitationsState = mInvitationsState.asStateFlow()
    val invitationState = mInvitationState.asStateFlow()

    fun fetch() {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            mInvitationsState.value = repository.fetch()
        }
    }

    fun findByCode(code: String) {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            val result = repository.findByCode(code)
            mInvitationState.value =
                result?.let { UIModelState.Success(it) } ?: UIModelState.NoData()
        }
    }

    sealed interface UIModelState<E> {
        class Loading<T> : UIModelState<T>
        class NoData<E> : UIModelState<E>
        data class Success<E>(val data: E) : UIModelState<E>
    }
}