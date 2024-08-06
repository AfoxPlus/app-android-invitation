package com.afoxplus.invitation.delivery.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.invitation.R
import com.afoxplus.invitation.delivery.components.bottom_sheet.SearchCodeBottomSheet
import com.afoxplus.invitation.delivery.components.items.InvitationItem
import com.afoxplus.invitation.delivery.models.InvitationItemModel
import com.afoxplus.uikit.designsystem.atoms.UIKitButtonPrimaryLarge
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.molecules.UIKitTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyInvitationScreen(onBackPressed: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            UIKitTopBar(
                title = stringResource(id = R.string.invitation_screen_top_app_bar_title),
                description = stringResource(id = R.string.invitation_screen_top_app_bar_description),
                onBackAction = onBackPressed
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                DataListState()
                SearchCodeBottomSheet(modalBottomSheetState = modalBottomSheetState,
                    onDismiss = {
                    })
            }
        },
        bottomBar = {
            UIKitButtonPrimaryLarge(
                modifier = Modifier.padding(UIKitTheme.spacing.spacing16),
                text = stringResource(id = R.string.invitation_screen_button_title),
                onClick = {
                    coroutineScope.launch {
                        modalBottomSheetState.show()
                    }
                })
        }
    )
}

@Composable
internal fun EmptyListState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            UIKitTheme.spacing.spacing08,
            alignment = Alignment.CenterVertically
        )
    ) {
        UIKitText(
            color = UIKitColorTheme.secondaryColor,
            text = stringResource(id = R.string.invitation_screen_empty_state_title),
            style = UIKitTheme.typography.header04SemiBold
        )
        UIKitText(
            color = UIKitColorTheme.gray600,
            text = stringResource(id = R.string.invitation_screen_empty_state_description),
            style = UIKitTheme.typography.header05
        )
    }
}

@Composable
internal fun DataListState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = UIKitTheme.spacing.spacing12)
    ) {
        val model = InvitationItemModel(
            id = "",
            title = "Mis 18 Años: Victor Pacherres",
            address = "Gelora Bung Karno Stadium, Jakarta",
            date = "31 Septiembre 2024",
            assistants = "50K+ Participantes"
        )
        InvitationItem(model = model)
    }
}