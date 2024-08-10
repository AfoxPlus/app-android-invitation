package com.afoxplus.invitation.delivery.flows

import com.afoxplus.invitation.delivery.models.InvitationModelEvent

fun interface GoToRestaurantFromInvitation {
    operator fun invoke(model: InvitationModelEvent)
}