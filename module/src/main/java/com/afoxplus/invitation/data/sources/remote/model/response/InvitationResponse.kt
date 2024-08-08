package com.afoxplus.invitation.data.sources.remote.model.response

import com.google.gson.annotations.SerializedName

internal data class InvitationResponse(
    @SerializedName("id") val id: String,
    @SerializedName("urlBanner") val urlBanner: String,
    @SerializedName("code") val code: String,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("address") val address: String,
    @SerializedName("guest") val guest: String,
    @SerializedName("time") val time: String,
    @SerializedName("gate") val gate: String,
    @SerializedName("table") val table: String,
    @SerializedName("ulrBarcode") val ulrBarcode: String,
    @SerializedName("participants") val participants: String
)