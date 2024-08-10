package com.afoxplus.invitation.delivery.models

internal data class InvitationItemModel(
    val code: String,
    val title: String,
    val address: String,
    val date: String,
    val participants: String
)