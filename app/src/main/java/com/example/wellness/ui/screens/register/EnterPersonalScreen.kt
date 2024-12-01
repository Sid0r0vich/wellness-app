package com.example.wellness.ui.screens.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.wellness.R
import com.example.wellness.auth.EnterStepsUiState
import com.example.wellness.data.Sex
import com.example.wellness.ui.components.DefaultPicker
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.screens.RegisterStepScreen
import com.example.wellness.ui.viewModels.EnterStepsViewModel

@Composable
fun EnterPersonalScreen(
    modifier: Modifier = Modifier,
    viewModel: EnterStepsViewModel,
    onNextClick: () -> Unit
) {
    val uiState = viewModel.uiState

    RegisterStepScreen(
        buttonTextId = R.string.next,
        onNextClick = onNextClick
    ) {
        Header(R.string.personal_data)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
        SexChoice(
            value = uiState.selectedSex,
            onChangeValue = { value -> uiState.selectedSex = value }
        )
        DefaultPicker(
            labelId = R.string.age,
            value = uiState.age,
            range = EnterStepsUiState.AGE_RANGE
        ) { uiState.age = it }
    }
}

@Composable
fun AgePicker(uiState: EnterStepsUiState) {
    NumberPicker(
        value = uiState.age,
        range = 14..99,
        onValueChange = {
            uiState.age = it
        }
    )
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