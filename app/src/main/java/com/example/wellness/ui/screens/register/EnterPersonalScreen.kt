package com.example.wellness.ui.screens.register

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.data.Sex
import com.example.wellness.ui.components.AuthFieldsInvalidation
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.screens.RegisterStepScreen
import com.example.wellness.ui.viewModels.RegisterViewModel

@Composable
fun EnterPersonalScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onNextClick: () -> Unit
) {
    val uiState = viewModel.uiState
    AuthFieldsInvalidation(uiState)

    RegisterStepScreen(
        buttonTextId = R.string.next,
        onNextClick = onNextClick
    ) {
        Header(R.string.signup)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
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
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
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
            horizontalAlignment = Alignment.CenterHorizontally
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