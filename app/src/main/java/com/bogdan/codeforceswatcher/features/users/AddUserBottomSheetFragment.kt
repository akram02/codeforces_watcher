package com.bogdan.codeforceswatcher.features.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.buttons.SmallButton
import com.bogdan.codeforceswatcher.components.compose.textfields.HandleTextField
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.content.res.ResourcesCompat
import com.bogdan.codeforceswatcher.util.showSoftKeyboard
import kotlinx.android.synthetic.main.input_field.*

class AddUserBottomSheetFragment: BottomSheetDialogFragment() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return ComposeView(requireContext()).apply {
            setContent {
                AlgoismeTheme {
                    ContentView()
                }
            }
        }
    }
}

@Composable
private fun ContentView() = Column(
    modifier = Modifier
        .background(AlgoismeTheme.colors.primary)
        .padding(horizontal = 20.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.End
) {
    val focusRequester = FocusRequester()
    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }

    HandleTextField(
        onValueChange = {},
        modifier = Modifier.focusRequester(focusRequester)
    )

    SmallButton(
        label = stringResource(R.string.add_user),
        isInverted = false
    ) {}
}