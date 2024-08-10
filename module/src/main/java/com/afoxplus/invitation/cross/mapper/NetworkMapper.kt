package com.afoxplus.invitation.cross.mapper

import com.afoxplus.invitation.data.sources.remote.model.response.InvitationResponse
import com.afoxplus.invitation.domain.entities.Invitation

internal fun InvitationResponse.toDomain(): Invitation {
    return Invitation(
        id = this.id,
        urlBanner = this.urlBanner,
        code = this.code,
        title = this.title,
        date = this.date,
        address = this.address,
        guest = this.guest,
        time = this.time,
        gate = this.gate,
        restaurantId = this.restaurantId ?: "",
        table = this.table,
        urlBarcode = this.ulrBarcode,
        participants = this.participants
    )
}

internal fun List<InvitationResponse>.toDomain(): List<Invitation> {
    return this.map { item -> item.toDomain() }
}