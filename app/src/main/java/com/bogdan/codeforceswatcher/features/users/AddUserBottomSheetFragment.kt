package com.bogdan.codeforceswatcher.features.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.buttons.SmallButton
import com.bogdan.codeforceswatcher.components.compose.textfields.HandleTextField
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.xorum.codeforceswatcher.features.users.redux.UsersRequests
import io.xorum.codeforceswatcher.features.users.redux.UsersState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class AddUserBottomSheetFragment: BottomSheetDialogFragment(), StoreSubscriber<UsersState> {

    private val addUserStatus: MutableState<UsersState.Status?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                AddUserView(
                    addUserStatus = addUserStatus,
                    onAddUser = { store.dispatch(UsersRequests.AddUser(it)) }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.users == newState.users
            }.select { it.users }
        }
    }

    override fun onStop() {
        super.onStop()

        store.unsubscribe(this)
    }

    override fun onNewState(state: UsersState) {
        addUserStatus.value = state.addUserStatus

        if (addUserStatus.value == UsersState.Status.DONE) {
            dismiss()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AddUserView(
    addUserStatus: State<UsersState.Status?>,
    onAddUser: (String) -> Unit
) {
    val addUserState = addUserStatus.value ?: return
    var handle = ""
    val focusRequester = FocusRequester()
    LaunchedEffect(key1 = true) { focusRequester.requestFocus() }

    Column(
        modifier = Modifier
            .background(AlgoismeTheme.colors.primary)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(addUserState.status) {
            LinearProgressIndicator(
                backgroundColor = AlgoismeTheme.colors.secondaryVariant,
                color = AlgoismeTheme.colors.blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }

        HandleTextField(Modifier.focusRequester(focusRequester)) { handle = it }

        SmallButton(
            label = stringResource(R.string.add_user),
            isInverted = false
        ) {
            onAddUser(handle)
            handle = ""
        }
    }
}

private val UsersState.Status.status get() = when(this) {
    UsersState.Status.IDLE -> false
    UsersState.Status.PENDING -> true
    UsersState.Status.DONE -> false
}