package com.example.wellness.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.wellness.ui.navigation.NavDestination

val LocalGridPadding = compositionLocalOf<Dp> { error("no value provided") }
val LocalBoardPadding = compositionLocalOf<Dp> { error("no value provided") }

@Composable
fun DefaultSpacer() {
    Spacer(modifier = Modifier.padding(PaddingValues(LocalGridPadding.current)))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: NavDestination,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currentScreen.label),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
    )
}

@Composable
fun wellnessTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
    )
}

@Composable
fun WellnessTextFiled(
    value: String,
    onValueChange: (String) -> Unit,
    textId: Int,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text( text = stringResource(textId) )
        },
        shape = RoundedCornerShape(20.dp),
        colors = wellnessTextFieldColors(),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        interactionSource = interactionSource,
        isError = isError,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RightArrowButton(
    modifier: Modifier = Modifier,
    textId: Int,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(textId),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun DefaultPicker(
    labelId: Int,
    value: Int,
    range: Iterable<Int>,
    onValueChange: (Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(labelId),
            style = MaterialTheme.typography.titleLarge
        )
        DefaultSpacer()
        NumberPicker(
            value = value,
            range = range,
            onValueChange = onValueChange
        )
    }
}