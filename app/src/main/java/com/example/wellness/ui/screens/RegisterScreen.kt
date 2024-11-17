package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.auth.MessageNotifier
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.data.Sex
import com.example.wellness.data.UserInfo
import com.example.wellness.ui.components.AuthButton
import com.example.wellness.ui.components.AuthEmailField
import com.example.wellness.ui.components.AuthFieldsInvalidation
import com.example.wellness.ui.components.AuthPasswordField
import com.example.wellness.ui.components.AuthTrigger
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.viewModels.AppViewModelProvider
import com.example.wellness.ui.viewModels.RegisterViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onPerformRegister: () -> Unit,
    onLoginClick: () -> Unit
) {
    val messageNotifier = MessageNotifier(LocalContext.current)
    val uiState = viewModel.uiState
    AuthFieldsInvalidation(uiState)

    AuthTrigger(
        authState = viewModel.authStateFlow.collectAsState(),
        onPerformAuth = onPerformRegister
    )

    DefaultScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                ) { status -> messageNotifier.notifyUser(status) }
            }
            TextButton(
                onClick = onLoginClick,
            ) {
                Text(text = stringResource(R.string.have_account))
            }
        }
    }
}

@Composable
fun SexChoice(
    value: Sex,
    onChangeValue: (Sex) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { onChangeValue(Sex.Man) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (value == Sex.Man) Color.Black else Color.Gray
            )
        ) {
            Text(text = stringResource(R.string.man_sex))
        }
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        Button(
            onClick = { onChangeValue(Sex.Woman) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (value == Sex.Woman) Color.Black else Color.Gray
            )
        ) {
            Text(text = stringResource(R.string.woman_sex))
        }
    }
}

@Composable
fun AgeChoice(
    value: Int,
    valueRange: IntRange,
    onChangeValue: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(PaddingValues(horizontal = 60.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = "${stringResource(R.string.age)}: ${value}",
                style = MaterialTheme.typography.titleLarge
            )
            Slider(
                value = value.toFloat(),
                onValueChange = { onChangeValue(it.toInt()) },
                valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
                steps = valueRange.last - valueRange.first - 1
            )
        }
    }
}