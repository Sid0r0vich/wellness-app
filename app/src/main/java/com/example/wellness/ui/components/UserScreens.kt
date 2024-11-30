package com.example.wellness.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wellness.R

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(PaddingValues(LocalBoardPadding.current))
            ) {
                content()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(PaddingValues(10.dp))
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
            }
        }
    }
}

@Composable
fun HomeUserCard(
    modifier: Modifier = Modifier,
    userName: String = stringResource(R.string.user_name),
    content: @Composable ColumnScope.() -> Unit = {}
) {
    UserCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Hello, $userName!",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.padding(PaddingValues(5.dp)))
        Text(
            text = stringResource(R.string.be_wellness),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        content()
    }
}
