package com.afoxplus.invitation.data.sources.remote.model.request

import com.google.gson.annotations.SerializedName

internal data class InvitationRequest(@SerializedName("invitationId") val invitationId: String)