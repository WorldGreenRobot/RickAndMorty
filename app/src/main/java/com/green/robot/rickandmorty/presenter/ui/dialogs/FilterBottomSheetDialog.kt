package com.green.robot.rickandmorty.presenter.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.presenter.extensions.getGenderStrings
import com.green.robot.rickandmorty.presenter.extensions.getStatusStrings
import com.green.robot.rickandmorty.presenter.ui.components.ExposedDropdownMenu
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetDialog(
    onDismissRequest: () -> Unit,
    filterCharacters: CharactersState.FilterData?,
    modifier: Modifier = Modifier
) {
    
    val status by remember { mutableStateOf(filterCharacters?.status.orEmpty()) }
    val gender by remember { mutableStateOf(filterCharacters?.gender.orEmpty()) }
    val race by remember { mutableStateOf(filterCharacters?.species.orEmpty()) }
    
    BasicAlertDialog(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(24.dp)
        ),
        onDismissRequest = onDismissRequest,
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                textAlign = TextAlign.Start,
                text = stringResource(R.string.filter_filter),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))

            ExposedDropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                options = Status.entries.getStatusStrings(),
                selectedOption = status,
                onSelectedOption = {

                },
                label = stringResource(R.string.status_filter)
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                options = Gender.entries.getGenderStrings(),
                selectedOption = gender,
                onSelectedOption = {

                },
                label = stringResource(R.string.gender_filter)
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                readOnly = true,
                value = race,
                onValueChange = {

                },
                label = { if (filterCharacters?.species.isNullOrBlank()) Text(stringResource(R.string.race_filter)) else null },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(android.R.string.cancel)
                    )
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(android.R.string.ok)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FilterBottomSheetDialogPreview() {
    FilterBottomSheetDialog(
        onDismissRequest = {},
        filterCharacters = CharactersState.FilterData(
            status = "",
            species = "",
            gender = ""
        )
    )
}