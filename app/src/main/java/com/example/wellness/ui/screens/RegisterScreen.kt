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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.ui.AppViewModelProvider
import com.example.wellness.ui.components.AuthenticationTrigger
import com.example.wellness.ui.components.EmailField
import com.example.wellness.ui.components.PasswordField
import com.example.wellness.ui.components.collectIsPressedAsStateValue

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onPerformRegister: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState = viewModel.uiState
    if (uiState.emailSource.collectIsPressedAsStateValue())
        uiState.emailIsValidated = false
    if (uiState.passwordSource.collectIsPressedAsStateValue())
        uiState.passwordIsValidated = false

    AuthenticationTrigger(
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
            EmailField(
                value = uiState.email,
                onValueChange = { uiState.email = it; uiState.emailIsValidated = false },
                interactionSource = uiState.emailSource,
                isError = uiState.emailIsValidated && !viewModel.validateEmailFormat(uiState.email)
            )
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            PasswordField(
                value = uiState.password,
                onValueChange = { uiState.password = it; uiState.passwordIsValidated = false },
                interactionSource = uiState.passwordSource,
                isError = uiState.passwordIsValidated && !viewModel.validatePasswordFormat(uiState.password)
            )
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            SexChoice(selected = uiState.selectedSex)
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            AgeChoice(value = uiState.age, valueRange = RegisterUiState.AGE_RANGE)
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
    selected: MutableState<Sex>,
    modifier: Modifier = Modifier
) {
    var selectedSex by selected

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { selectedSex = Sex.Man },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedSex == Sex.Man) Color.Black else Color.Gray
            )
        ) {
            Text(text = stringResource(R.string.man_sex))
        }
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        Button(
            onClick = { selectedSex = Sex.Woman },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedSex == Sex.Woman) Color.Black else Color.Gray
            )
        ) {
            Text(text = stringResource(R.string.woman_sex))
        }
    }
}

@Composable
fun AgeChoice(
    value: MutableState<Int>,
    valueRange: IntRange,
    modifier: Modifier = Modifier,
) {
    var age by value

    Box(
        modifier = modifier.padding(PaddingValues(horizontal = 60.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = "${stringResource(R.string.age)}: ${age}",
                style = MaterialTheme.typography.titleLarge
            )
            Slider(
                value = age.toFloat(),
                onValueChange = { age = it.toInt() },
                valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
                steps = valueRange.last - valueRange.first - 1
            )
        }
    }
}