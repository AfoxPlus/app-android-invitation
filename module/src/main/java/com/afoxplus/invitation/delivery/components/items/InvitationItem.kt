package com.afoxplus.invitation.delivery.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.afoxplus.invitation.delivery.models.InvitationItemModel
import com.afoxplus.uikit.designsystem.atoms.UIKitIcon
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitIconTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTypographyTheme

internal typealias InvitationItemModelClicked = (item: InvitationItemModel) -> Unit

@Composable
internal fun InvitationItem(
    modifier: Modifier = Modifier,
    model: InvitationItemModel,
    onClick: InvitationItemModelClicked
) {
    Card(
        modifier = modifier.clickable { onClick(model) },
        colors = CardDefaults.cardColors(containerColor = UIKitColorTheme.gray100),
        shape = RoundedCornerShape(UIKitTheme.spacing.spacing08)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UIKitTheme.spacing.spacing16),
            verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing04)
        ) {
            UIKitText(
                modifier = Modifier.fillMaxWidth(),
                text = model.title,
                style = UIKitTypographyTheme.paragraph01SemiBold,
                color = UIKitColorTheme.secondaryColor
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing08)
            ) {
                UIKitIcon(icon = UIKitIconTheme.icon_pin_location_outline)
                UIKitText(
                    text = model.address,
                    style = UIKitTypographyTheme.paragraph02,
                    color = UIKitColorTheme.secondaryColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing08)
            ) {
                UIKitIcon(icon = UIKitIconTheme.icon_calendar_small_outline)
                UIKitText(
                    text = model.date,
                    style = UIKitTypographyTheme.paragraph02,
                    color = UIKitColorTheme.secondaryColor
                )
            }


            Box(modifier = Modifier.padding(top = UIKitTheme.spacing.spacing12)) {
                UIKitText(
                    text = model.participants,
                    style = UIKitTypographyTheme.paragraph02,
                    color = UIKitColorTheme.secondaryColor
                )
            }
        }

    }
}