package com.afoxplus.invitation.domain.repository

import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.uikit.views.status.ListState

internal interface InvitationRepository {
    suspend fun fetch(): ListState<Invitation>
    suspend fun findByCode(code: String, useLocalCache: Boolean = false): Invitation?
    suspend fun setGuestUUID(invitationID: String): Boolean
}