package com.afoxplus.invitation.delivery.events

import com.afoxplus.uikit.bus.UIKitEventBus

data class InvitationToRestaurantEvent(val tableId: String, val restaurantId: String, val guestName: String) : UIKitEventBus