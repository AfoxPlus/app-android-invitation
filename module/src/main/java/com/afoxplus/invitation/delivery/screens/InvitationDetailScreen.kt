package com.afoxplus.invitation.delivery.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.invitation.R
import com.afoxplus.invitation.delivery.components.ticket.InvitationTicket
import com.afoxplus.uikit.designsystem.atoms.UIKitButtonPrimaryLarge
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.molecules.UIKitTopBar

@Composable
internal fun InvitationDetailScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            UIKitTopBar(
                title = stringResource(id = R.string.invitation_screen_top_app_bar_title),
                description = stringResource(id = R.string.invitation_new_screen_top_app_bar_description),
                onBackAction = onBackPressed
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(UIKitTheme.spacing.spacing16)
                    .verticalScroll(rememberScrollState())
            ) {
                InvitationTicket(modifier = Modifier.height(IntrinsicSize.Min))
            }
        },
        bottomBar = {
            Box(modifier = Modifier.padding(UIKitTheme.spacing.spacing16)) {
                UIKitButtonPrimaryLarge(
                    text = stringResource(id = R.string.invitation_new_screen_button_confirm_title),
                    onClick = {})
            }
        }
    )
}