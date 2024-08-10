package com.afoxplus.invitation.demo.global

import android.util.Log
import com.afoxplus.invitation.delivery.flows.GoToRestaurantFromInvitation
import com.afoxplus.invitation.delivery.models.InvitationModelEvent
import javax.inject.Inject

class GoToRestaurantFromInvitationImpl @Inject constructor() : GoToRestaurantFromInvitation {
    override fun invoke(model: InvitationModelEvent) {
        Log.d("LOG_VALE", "GoToRestaurantFromInvitation")
    }
}