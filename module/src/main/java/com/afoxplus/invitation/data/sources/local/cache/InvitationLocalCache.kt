package com.afoxplus.invitation.data.sources.local.cache

import com.afoxplus.invitation.domain.entities.Invitation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class InvitationLocalCache @Inject constructor() {
    private val myInvitations: MutableList<Invitation> = mutableListOf()

    fun saveInvitations(invitations: List<Invitation>) {
        myInvitations.clear()
        myInvitations.addAll(invitations)
    }

    fun saveInvitation(invitation: Invitation) {
        if (myInvitations.any { item -> item.id == invitation.id }.not()) {
            myInvitations.add(invitation)
        }
    }

    fun getInvitations(): List<Invitation> = myInvitations

    fun getInvitation(code: String): Invitation? =
        myInvitations.find { item -> item.code == code }
}