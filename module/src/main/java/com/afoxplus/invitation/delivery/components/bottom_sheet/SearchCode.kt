package com.afoxplus.invitation.delivery.components.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.invitation.R
import com.afoxplus.uikit.designsystem.atoms.UIKitButtonPrimaryLarge
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.atoms.UIKitTextField
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTypographyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchCodeBottomSheet(
    modalBottomSheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit
) {

    if (modalBottomSheetState.isVisible) {
        ModalBottomSheet(
            sheetState = modalBottomSheetState,
            onDismissRequest = { onDismiss() },
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier.padding(bottom = UIKitTheme.spacing.spacing12),
                verticalArrangement = Arrangement.spacedBy(
                    UIKitTheme.spacing.spacing08,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UIKitText(
                    modifier = Modifier
                        .padding(bottom = UIKitTheme.spacing.spacing16),
                    text = stringResource(id = R.string.invitation_bottom_sheet_title),
                    style = UIKitTypographyTheme.header05SemiBold
                )
                HorizontalDivider(color = UIKitColorTheme.gray300)
                Column(
                    modifier = Modifier
                        .padding(UIKitTheme.spacing.spacing16)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing12)
                ) {
                    UIKitTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = UIKitTheme.spacing.spacing12),
                        placeholder = stringResource(id = R.string.invitation_bottom_sheet_input_code_placeholder)
                    ) {
                    }
                    UIKitButtonPrimaryLarge(
                        text = stringResource(id = R.string.invitation_bottom_sheet_button_title),
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}