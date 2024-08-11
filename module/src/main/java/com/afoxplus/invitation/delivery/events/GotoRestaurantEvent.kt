package com.afoxplus.invitation.delivery.events

import com.afoxplus.uikit.bus.UIKitEventBus

data class GotoRestaurantEvent(val tableId: String, val restaurantId: String) : UIKitEventBus