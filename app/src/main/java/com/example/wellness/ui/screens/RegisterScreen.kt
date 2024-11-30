package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.auth.AuthStatus
import com.example.wellness.auth.MessageNotifier
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.data.UserInfo
import com.example.wellness.ui.components.AuthButton
import com.example.wellness.ui.components.AuthEmailField
import com.example.wellness.ui.components.AuthFieldsInvalidation
import com.example.wellness.ui.components.AuthPasswordField
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.screens.register.AgeChoice
import com.example.wellness.ui.screens.register.SexChoice
import com.example.wellness.ui.viewModels.RegisterViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onPerformRegister: () -> Unit,
    onLoginClick: () -> Unit
) {
    val messageNotifier = MessageNotifier(LocalContext.current)
    val uiState = viewModel.uiState
    AuthFieldsInvalidation(uiState)

    AuthScreen {
        Header(R.string.signup)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
        AuthEmailField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        AuthPasswordField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        SexChoice(
            value = uiState.selectedSex,
            onChangeValue = { value -> uiState.selectedSex = value }
        )
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        AgeChoice(
            value = uiState.age,
            valueRange = RegisterUiState.AGE_RANGE,
            onChangeValue = { value -> uiState.age = value }
        )
        AuthButton(
            authState = viewModel.authState,
            uiState = uiState,
            textId = R.string.perform_register
        ) {
            viewModel.signUp(
                UserInfo(
                    name = "userName",
                    email = uiState.email,
                    password = uiState.password,
                    sex = uiState.selectedSex,
                    age = uiState.age
                )
            ) { status ->
                messageNotifier.notifyUser(status)
                if (status == AuthStatus.SUCCESS) onPerformRegister()
            }
        }
        TextButton(
            onClick = onLoginClick,
        ) {
            Text(text = stringResource(R.string.have_account))
        }
    }
}