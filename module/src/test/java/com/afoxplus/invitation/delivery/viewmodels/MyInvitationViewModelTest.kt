package com.afoxplus.invitation.delivery.viewmodels

import com.afoxplus.invitation.cross.BaseViewModelTests
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.views.status.ListLoading
import com.afoxplus.uikit.views.status.ListState
import com.afoxplus.uikit.views.status.ListSuccess
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class MyInvitationViewModelTest : BaseViewModelTests() {

    private val repository: InvitationRepository = mock()
    private lateinit var myInvitationViewModel: MyInvitationViewModel

    @Before
    fun setup() {
        myInvitationViewModel = MyInvitationViewModel(repository, testDispatcherProvider)
    }

    @Test
    fun `should return invitations when execute method fetch`() {
        runTest {
            whenever(repository.fetch()).thenReturn(ListSuccess(data = listInvitation))
            val resultList = mutableListOf<ListState<Invitation>>()
            val job = launch(testDispatcher) {
                myInvitationViewModel.invitationsState.toList(resultList)
            }
            myInvitationViewModel.fetch()

            assertEquals(listInvitation, (resultList.last() as ListSuccess).data)
            job.cancel()
        }
    }

    @Test
    fun `should return loading state when execute method fetch`() {
        runTest {
            whenever(repository.fetch()).thenReturn(ListLoading())
            val resultList = mutableListOf<ListState<Invitation>>()
            val job = launch(testDispatcher) {
                myInvitationViewModel.invitationsState.toList(resultList)
            }

            myInvitationViewModel.fetch()

            assert(resultList.last() is ListLoading<Invitation>)
            job.cancel()
        }
    }

    @Test
    fun `should return invitation when execute method findByCode`() {
        runTest {
            val code = "asd123"
            whenever(repository.findByCode(code)).thenReturn(invitation)
            val resultList = mutableListOf<MyInvitationViewModel.UIModelState<Invitation>>()
            val job = launch(testDispatcher) {
                myInvitationViewModel.invitationState.toList(resultList)
            }

            myInvitationViewModel.findByCode(code)

            assertEquals(MyInvitationViewModel.UIModelState.Success(invitation), resultList.last())
            job.cancel()
        }
    }

    @Test
    fun `should call fetch invitations from repository when execute method fetch`() {
        runTest {
            myInvitationViewModel.fetch()
            verify(repository).fetch()
        }
    }

    @Test
    fun `should call findByCode invitation from repository when execute method findByCode`() {
        runTest {
            val code = "as1d23"
            myInvitationViewModel.findByCode(code)
            verify(repository).findByCode(code)
        }
    }

    private val invitation = Invitation(
        id = "asd123",
        urlBanner = "link",
        code = "ASF123",
        title = "evento",
        date = "01/02/2024",
        address = "address",
        guest = "Guest",
        time = "08:pm",
        gate = "Five",
        table = "T01",
        urlBarcode = "link",
        participants = "50k"
    )

    private val listInvitation = listOf(invitation)
}