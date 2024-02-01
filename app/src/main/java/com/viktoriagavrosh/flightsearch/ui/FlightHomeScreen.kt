package com.viktoriagavrosh.flightsearch.ui

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme
import java.util.Locale

@Composable
fun FlightHomeScreen(
    modifier: Modifier = Modifier,
    uiState: FlightUiState,
    onTextChange: (String) -> Unit,
    onAirportClick: (String) -> Unit,
) {

    val resultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val results = result.data?.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            ) as ArrayList<String>

            onTextChange(results[0])
        }
    }

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        TextField(
            value = uiState.inputText,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
                        } else {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                            )
                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()
                            )
                            intent.putExtra(
                                RecognizerIntent.EXTRA_PROMPT, "Speak Something"
                            )
                            resultLauncher.launch(intent)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mic),
                        contentDescription = stringResource(R.string.microphone)
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = MaterialTheme.colorScheme.background
            )
        )
        if (uiState.isSearch) {
            AirportsColumn(
                airports = uiState.listAirports,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
                onAirportClick = onAirportClick
            )
        } else {
            RoutesColumn(
                airport = uiState.selectedAirport,
                listRoutes = uiState.listRoutes,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}

@Preview
@Composable
fun FlightHomeScreenPreview() {
    val mockAirport = Airport(1, "Airport", "AAA", 1)
    FlightSearchTheme {
        FlightHomeScreen(
            uiState = FlightUiState("text", mockAirport, emptyList()),
            onTextChange = {},
            onAirportClick = {}
        )
    }
}