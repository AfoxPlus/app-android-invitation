package com.afoxplus.invitation.delivery.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.invitation.R
import com.afoxplus.invitation.cross.mapper.toUIModel
import com.afoxplus.invitation.delivery.components.bottom_sheet.SearchCodeBottomSheet
import com.afoxplus.invitation.delivery.components.items.InvitationItem
import com.afoxplus.invitation.delivery.components.items.InvitationItemModelClicked
import com.afoxplus.invitation.delivery.models.InvitationItemModel
import com.afoxplus.invitation.delivery.viewmodels.MyInvitationViewModel
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.uikit.designsystem.atoms.UIKitButtonPrimaryLarge
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.molecules.UIKitLoading
import com.afoxplus.uikit.designsystem.molecules.UIKitTopBar
import com.afoxplus.uikit.views.status.ListEmptyData
import com.afoxplus.uikit.views.status.ListError
import com.afoxplus.uikit.views.status.ListLoading
import com.afoxplus.uikit.views.status.ListSuccess
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyInvitationScreen(
    modalBottomSheetState: SheetState,
    viewModel: MyInvitationViewModel,
    onBackPressed: () -> Unit,
    onItemInvitationClicked: InvitationItemModelClicked,
) {
    val coroutineScope = rememberCoroutineScope()
    val invitationsState by viewModel.invitationsState.collectAsState()
    val enableButtonFind = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) { viewModel.fetch() }
    HandleInvitationState(enableButtonFind, viewModel)

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
                when (invitationsState) {
                    is ListLoading -> LoadingState()
                    is ListSuccess -> DataListState(
                        invitations = (invitationsState as ListSuccess<Invitation>).data.toUIModel(),
                        onClick = onItemInvitationClicked
                    )

                    is ListEmptyData -> EmptyListState()
                    is ListError -> ErrorListState()
                }
                SearchCodeBottomSheet(
                    modalBottomSheetState = modalBottomSheetState,
                    enabledButton = enableButtonFind.value,
                    onFind = { code ->
                        viewModel.findByCode(code, useLocalCache = false)
                    })
            }
        },
        bottomBar = {
            Box(modifier = Modifier.padding(UIKitTheme.spacing.spacing16)) {
                UIKitButtonPrimaryLarge(
                    text = stringResource(id = R.string.invitation_screen_button_title),
                    onClick = {
                        enableButtonFind.value = true
                        coroutineScope.launch { modalBottomSheetState.show() }
                    })
            }
        }
    )
}


@Composable
internal fun HandleInvitationState(
    enableButtonFind: MutableState<Boolean>,
    viewModel: MyInvitationViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.invitationState.collectLatest { state ->
            when (state) {
                is MyInvitationViewModel.UIModelState.Loading -> {
                    enableButtonFind.value = false
                }

                is MyInvitationViewModel.UIModelState.NoData -> {
                    enableButtonFind.value = true
                }

                is MyInvitationViewModel.UIModelState.Success -> {
                    viewModel.navigateToDetail(state.data)
                }
            }
        }
    }
}

@Composable
internal fun ErrorListState() {
    ScreenMessage(
        title = stringResource(id = R.string.invitation_screen_error_state_title),
        description = stringResource(id = R.string.invitation_screen_error_state_description)
    )
}

@Composable
internal fun EmptyListState() {
    ScreenMessage(
        title = stringResource(id = R.string.invitation_screen_empty_state_title),
        description = stringResource(id = R.string.invitation_screen_empty_state_description)
    )
}

@Composable
internal fun ScreenMessage(modifier: Modifier = Modifier, title: String, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            UIKitTheme.spacing.spacing08,
            alignment = Alignment.CenterVertically
        )
    ) {
        UIKitText(
            color = UIKitColorTheme.secondaryColor,
            text = title,
            style = UIKitTheme.typography.header04SemiBold
        )
        UIKitText(
            color = UIKitColorTheme.gray600,
            text = description,
            style = UIKitTheme.typography.header05
        )
    }
}

@Composable
internal fun LoadingState(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            UIKitTheme.spacing.spacing08,
            alignment = Alignment.CenterVertically
        )
    ) {
        UIKitLoading()
    }
}


@Composable
internal fun DataListState(
    modifier: Modifier = Modifier,
    invitations: List<InvitationItemModel>,
    onClick: InvitationItemModelClicked,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing10),
        contentPadding = PaddingValues(
            horizontal = UIKitTheme.spacing.spacing16,
            vertical = UIKitTheme.spacing.spacing04
        ),
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(invitations) { _, model ->
            InvitationItem(model = model, onClick = onClick)
        }
    }
}