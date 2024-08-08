package com.afoxplus.invitation.data.repository

import com.afoxplus.invitation.data.sources.remote.InvitationNetworkDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InvitationDataRepositoryTest {
    private val invitationNetworkDataSource: InvitationNetworkDataSource = mock()
    private lateinit var invitationDataRepository: InvitationDataRepository

    @Before
    fun setup() {
        invitationDataRepository = InvitationDataRepository(invitationNetworkDataSource)
    }

    @Test
    fun `should use method fetch from network data source when invoke fetch`() {
        runTest {
            invitationDataRepository.fetch()
            verify(invitationNetworkDataSource).fetch()
        }
    }

    @Test
    fun `should use method findByCode from network data source when invoke findByCode`() {
        runTest {
            val code = "AS1D23"
            invitationDataRepository.findByCode(code)
            verify(invitationNetworkDataSource).findByCode(code)
        }
    }
}