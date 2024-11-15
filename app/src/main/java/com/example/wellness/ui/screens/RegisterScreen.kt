package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.auth.AuthState
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.data.Sex
import com.example.wellness.data.UserInfo
import com.example.wellness.ui.AppViewModelProvider
import com.example.wellness.ui.components.AuthEmailField
import com.example.wellness.ui.components.AuthPasswordField
import com.example.wellness.ui.components.AuthTrigger
import com.example.wellness.ui.viewModels.RegisterViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onPerformRegister: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState = viewModel.uiState
    AuthFieldsInvalidation(uiState)

    AuthTrigger(
        authState = viewModel.authState.observeAsState(),
        onPerformAuth = onPerformRegister
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.signup),
                style = MaterialTheme.typography.displayMedium,
            )
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
            Button(
                onClick = {
                    viewModel.signUp(
                        UserInfo(
                            name = "userName",
                            email = uiState.email,
                            password = uiState.password,
                            sex = uiState.selectedSex,
                            age = uiState.age
                        )
                    )
                    uiState.emailIsValidated = true
                    uiState.passwordIsValidated = true
                },
                enabled =
                    uiState.email.isNotEmpty() &&
                    uiState.password.isNotEmpty() &&
                    viewModel.authState.value != AuthState.Loading,
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = stringResource(R.string.perform_register)
                )
            }
            TextButton(
                onClick = onLoginClick,
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = stringResource(R.string.have_account)
                )
            }
        }
    }
}

@Composable
fun SexChoice(
    value: Sex,
    onChangeValue: (Sex) -> Unit,
    modifier: Modifier = Modifier
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