package com.afoxplus.invitation.data.repository

import com.afoxplus.invitation.data.sources.remote.InvitationNetworkDataSource
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.views.status.ListState
import javax.inject.Inject

internal class InvitationDataRepository @Inject constructor(
    private val invitationNetworkDataSource: InvitationNetworkDataSource
) : InvitationRepository {

    override suspend fun fetch(): ListState<Invitation> {
        return invitationNetworkDataSource.fetch()
    }

    override suspend fun findByCode(code: String): Invitation? {
        return invitationNetworkDataSource.findByCode(code)
    }
}