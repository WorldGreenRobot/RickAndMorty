package com.green.robot.rickandmorty.presenter.ui.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.presenter.extensions.getGenderString
import com.green.robot.rickandmorty.presenter.extensions.getStatusString
import com.green.robot.rickandmorty.presenter.ui.components.RadioButtonSingleSelection
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetDialog(
    onDismissRequest: () -> Unit,
    filterCharacters: CharactersState.FilterData?,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        }
    ) {
        RadioButtonSingleSelection(
            modifier = Modifier.fillMaxWidth(),
            radioOptions = Status.entries.map { it.getStatusString() },
        )

        RadioButtonSingleSelection(
            modifier = Modifier.fillMaxWidth(),
            radioOptions = Gender.entries.map { it.getGenderString() },
        )
    }
}

@Composable
@Preview
fun FilterBottomSheetDialogPreview() {
    FilterBottomSheetDialog(
        onDismissRequest = {},
        filterCharacters = CharactersState.FilterData(
            name = "",
            status = "",
            species = "",
            type = "",
            gender = ""
        )
    )
}