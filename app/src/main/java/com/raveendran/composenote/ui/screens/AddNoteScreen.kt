package com.raveendran.composenote.ui.screens

import Screen
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.raveendran.composenote.R
import com.raveendran.composenote.model.Note
import com.raveendran.composenote.ui.viewmodels.NoteViewModel

@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var text by remember {
        mutableStateOf("")
    }
    var desc by remember {
        mutableStateOf("")
    }

    val c = LocalContext.current
    val scrollState = rememberScrollState()
    // Smooth scroll to specified pixels on first composition
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (text.isNotEmpty() && desc.isNotEmpty()) {
                        val note = Note(title = text, desc = desc)
                        viewModel.insertNote(note = note)
                        Toast.makeText(c, "Note Added Successfully", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate(Screen.NoteScreen.route)
                    } else {
                        Toast.makeText(c, "Fill All the fields to continue", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.White,
                    )
                },
                text = {
                    Text(text = "Done", style = TextStyle(color = Color.White))
                }
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_btn),
                            contentDescription = null
                        )
                    }

                    Text(text = "Add New Note")
                }

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    label = { Text(text = "Enter Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    value = desc,
                    onValueChange = {
                        desc = it
                    },
                    label = { Text(text = "Enter Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}