package com.example.wellness.ui.screens.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.wellness.R
import com.example.wellness.auth.AuthStatus
import com.example.wellness.auth.EnterStepsUiState
import com.example.wellness.auth.MessageNotifier
import com.example.wellness.data.UserInfo
import com.example.wellness.ui.components.DefaultPicker
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.screens.RegisterStepScreen
import com.example.wellness.ui.viewModels.EnterStepsViewModel

@Composable
fun EnterAdditionalScreen(
    modifier: Modifier = Modifier,
    viewModel: EnterStepsViewModel,
    onNextClick: () -> Unit
) {
    val messageNotifier = MessageNotifier(LocalContext.current)
    val uiState = viewModel.uiState

    RegisterStepScreen(
        buttonTextId = R.string.signup,
        onNextClick = {
            uiState.emailIsValidated = true
            uiState.passwordIsValidated = true
            uiState.nameIsValidated = true
            viewModel.signUp(
                UserInfo(
                    name = uiState.name,
                    email = uiState.email,
                    password = uiState.password,
                    sex = uiState.selectedSex,
                    age = uiState.age
                )
            ) { status ->
                messageNotifier.notifyUser(status)
                if (status == AuthStatus.SUCCESS) onNextClick()
            }
        }
    )
    {
        Header(R.string.additional)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
        DefaultPicker(
            labelId = R.string.height,
            value = uiState.height,
            range = EnterStepsUiState.HEIGHT_RANGE
        ) { uiState.height = it }
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        DefaultPicker(
            labelId = R.string.weight,
            value = uiState.weight,
            range = EnterStepsUiState.WEIGHT_RANGE
        ) { uiState.weight = it }
    }
}