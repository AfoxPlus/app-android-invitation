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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.invitation.R
import com.afoxplus.invitation.delivery.components.ticket.InvitationTicket
import com.afoxplus.invitation.delivery.viewmodels.InvitationDetailViewModel
import com.afoxplus.uikit.designsystem.atoms.UIKitButtonPrimaryLarge
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.molecules.UIKitTopBar
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun InvitationDetailScreen(
    viewModel: InvitationDetailViewModel,
    code: String,
    useLocal: Boolean = false,
    onConfirmSuccess: () -> Unit,
    onBackPressed: () -> Unit
) {
    val invitationState by viewModel.invitation.collectAsState()
    val buttonEnable by viewModel.buttonEnable.collectAsState()
    val buttonName by viewModel.buttonName.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.findInvitation(code, useLocal) }

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
                when (invitationState) {
                    is InvitationDetailViewModel.UIModelState.Loading -> viewModel.disableButton()
                    is InvitationDetailViewModel.UIModelState.NoData -> viewModel.disableButton()
                    is InvitationDetailViewModel.UIModelState.Success -> {
                        viewModel.enableButton()
                        InvitationTicket(
                            invitation = (invitationState as InvitationDetailViewModel.UIModelState.Success).data,
                            modifier = Modifier.height(IntrinsicSize.Min)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(modifier = Modifier.padding(UIKitTheme.spacing.spacing16)) {
                UIKitButtonPrimaryLarge(
                    enabled = buttonEnable,
                    text = stringResource(id = buttonName),
                    onClick = { viewModel.onClickButton() })
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.confirmationActionState.collectLatest { state ->
            when (state) {
                InvitationDetailViewModel.UIActionState.Failure -> viewModel.enableButton()
                InvitationDetailViewModel.UIActionState.Loading -> viewModel.disableButton()
                InvitationDetailViewModel.UIActionState.Success -> onConfirmSuccess()
            }
        }
    }
}

