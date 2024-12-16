package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.data.Document
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.LocalBoardPadding
import com.example.wellness.ui.viewModels.DocumentsViewModel
import java.time.LocalDate

@Composable
fun DocumentsScreen(
    modifier: Modifier = Modifier,
    viewModel: DocumentsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val documents: List<Document> = viewModel.getDocumentStateFlows()
        .map { documentStateFlow -> documentStateFlow.collectAsState().value }

    UserScreen(uiState) {
        documents.forEach { document ->
            item { document.Card() }
        }
    }
}

@Composable
fun DocumentCard(
    name: String,
    date: LocalDate
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(LocalBoardPadding.current),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(PaddingValues(5.dp)))
            Text(text = date.toString(), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun Document.Card() {
    DefaultSpacer()
    DocumentCard(
        name = stringResource(this.nameId),
        date = this.date
    )
}