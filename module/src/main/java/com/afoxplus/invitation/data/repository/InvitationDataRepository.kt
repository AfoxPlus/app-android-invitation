package com.afoxplus.invitation.data.repository

import com.afoxplus.invitation.data.sources.local.cache.InvitationLocalCache
import com.afoxplus.invitation.data.sources.remote.InvitationNetworkDataSource
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.invitation.domain.repository.InvitationRepository
import com.afoxplus.uikit.views.status.ListState
import com.afoxplus.uikit.views.status.ListSuccess
import javax.inject.Inject

internal class InvitationDataRepository @Inject constructor(
    private val invitationNetworkDataSource: InvitationNetworkDataSource,
    private val invitationLocalCache: InvitationLocalCache
) : InvitationRepository {

    override suspend fun fetch(): ListState<Invitation> {
        val result = invitationNetworkDataSource.fetch()
        if (result is ListSuccess) {
            invitationLocalCache.saveInvitations(result.data)
        }
        return result
    }

    override suspend fun findByCode(code: String, useLocalCache: Boolean): Invitation? {
        return if (useLocalCache) {
            invitationLocalCache.getInvitation(code)
        } else invitationNetworkDataSource.findByCode(code)
    }

    override suspend fun setGuestUUID(invitationID: String): Boolean {
        return invitationNetworkDataSource.setGuestUUID(invitationID)
    }
}