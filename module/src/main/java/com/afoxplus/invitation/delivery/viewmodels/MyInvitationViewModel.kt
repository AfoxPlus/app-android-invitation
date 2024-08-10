package com.afoxplus.invitation.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import com.afoxplus.uikit.views.status.ListLoading
import com.afoxplus.uikit.views.status.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val mNavigationState: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }
    private val mInvitationState: MutableSharedFlow<UIModelState<Invitation>> by lazy { MutableSharedFlow() }

    val invitationsState = mInvitationsState.asStateFlow()
    val navigation = mNavigationState.asSharedFlow()
    val invitationState = mInvitationState.asSharedFlow()

    fun fetch() {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            mInvitationsState.value = repository.fetch()
        }
    }

    fun findByCode(code: String, useLocalCache: Boolean = false) {
        viewModelScope.launch(coroutineDispatcher.getIODispatcher()) {
            loadingState()
            repository.findByCode(code, useLocalCache)?.let { result ->
                mInvitationState.emit(UIModelState.Success(result))
            } ?: emptyState()
        }
    }

    fun navigateToDetail(invitation: Invitation) {
        viewModelScope.launch(coroutineDispatcher.getMainDispatcher()) {
            mNavigationState.emit(Navigation.ToInvitationDetail(invitation))
        }
    }

    private fun emptyState() {
        viewModelScope.launch(coroutineDispatcher.getMainDispatcher()) {
            mInvitationState.emit(UIModelState.NoData())
        }
    }

    private fun loadingState() {
        viewModelScope.launch(coroutineDispatcher.getMainDispatcher()) {
            mInvitationState.emit(UIModelState.Loading())
        }
    }

    sealed interface Navigation {
        data class ToInvitationDetail(val invitation: Invitation) : Navigation
    }

    sealed interface UIModelState<E> {
        class Loading<T> : UIModelState<T>
        class NoData<E> : UIModelState<E>
        data class Success<E>(val data: E) : UIModelState<E>
    }
}