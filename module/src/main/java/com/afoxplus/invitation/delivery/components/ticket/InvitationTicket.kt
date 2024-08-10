package com.afoxplus.invitation.delivery.components.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.afoxplus.invitation.R
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTypographyTheme

@Composable
internal fun InvitationTicket(
    invitation: Invitation,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = UIKitColorTheme.gray100),
        shape = RoundedCornerShape(UIKitTheme.spacing.spacing20)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = UIKitTheme.spacing.spacing16)
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .padding(top = UIKitTheme.spacing.spacing24),
                model = invitation.urlBanner,
                contentDescription = "Invitation Banner"
            )
            Spacer(modifier = Modifier.height(UIKitTheme.spacing.spacing16))
            UIKitText(
                text = invitation.title,
                style = UIKitTypographyTheme.header04SemiBold,
                color = UIKitColorTheme.secondaryColor
            )
            UIKitText(
                text = "${invitation.date} ~ ${invitation.address}",
                style = UIKitTypographyTheme.paragraph02,
                color = UIKitColorTheme.gray500
            )
            Spacer(modifier = Modifier.height(UIKitTheme.spacing.spacing22))
            HorizontalDivider(color = UIKitColorTheme.gray300)
            TicketData(invitation)
            Spacer(modifier = Modifier.height(UIKitTheme.spacing.spacing22))
            HorizontalDivider(color = UIKitColorTheme.gray300)
            TicketBarcode(urlBarcode = invitation.urlBarcode)
        }
    }
}

@Composable
internal fun TicketDataItem(modifier: Modifier = Modifier, label: String, value: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing04)
    ) {
        UIKitText(
            text = label,
            style = UIKitTypographyTheme.paragraph02,
            color = UIKitColorTheme.gray500
        )
        UIKitText(
            text = value,
            style = UIKitTypographyTheme.paragraph01SemiBold,
            color = UIKitColorTheme.secondaryColor
        )
    }
}

@Composable
internal fun TicketData(invitation: Invitation, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing16),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = UIKitTheme.spacing.spacing12)
    ) {
        val weightColumnOne = 1.5f
        val wightColumnTwo = 1f

        Row(modifier = Modifier.fillMaxWidth()) {
            TicketDataItem(
                modifier = Modifier.weight(weightColumnOne),
                label = "Nombres", value = invitation.guest
            )

            TicketDataItem(
                modifier = Modifier.weight(wightColumnTwo),
                label = "Código", value = invitation.code
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TicketDataItem(
                modifier = Modifier.weight(weightColumnOne),
                label = "Fecha", value = invitation.date
            )

            TicketDataItem(
                modifier = Modifier.weight(wightColumnTwo),
                label = "Hora", value = invitation.time
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TicketDataItem(
                modifier = Modifier.weight(weightColumnOne),
                label = "Puerta", value = invitation.gate
            )

            TicketDataItem(
                modifier = Modifier.weight(wightColumnTwo),
                label = "Mesa", value = invitation.table
            )
        }
    }
}

@Composable
internal fun TicketBarcode(modifier: Modifier = Modifier, urlBarcode: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = UIKitTheme.spacing.spacing24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing08)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            model = urlBarcode,
            contentDescription = "Barcode"
        )

        UIKitText(
            text = stringResource(id = R.string.invitation_barcode_description),
            style = UIKitTypographyTheme.paragraph02,
            color = UIKitColorTheme.secondaryColor
        )
    }
}