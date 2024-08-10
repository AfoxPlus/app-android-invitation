package com.afoxplus.invitation.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Invitation(
    val id: String,
    val urlBanner: String,
    val code: String,
    val title: String,
    val date: String,
    val address: String,
    val guest: String,
    val time: String,
    val gate: String,
    val table: String,
    val urlBarcode: String,
    val participants: String
) : Parcelable