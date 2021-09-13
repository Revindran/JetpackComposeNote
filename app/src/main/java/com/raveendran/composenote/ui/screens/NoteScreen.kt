package com.raveendran.composenote.ui.screens

import Screen
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.raveendran.composenote.R
import com.raveendran.composenote.ui.components.EmptyScreen
import com.raveendran.composenote.ui.components.NoteCard
import com.raveendran.composenote.ui.components.SearchBar
import com.raveendran.composenote.ui.components.StaggeredVerticalGrid
import com.raveendran.composenote.ui.viewmodels.NoteViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val list = viewModel.list.collectAsState()
    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.surface,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                text = {
                    Text(text = "Add New", style = TextStyle(color = Color.White))
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar()
            Spacer(modifier = Modifier.height(10.dp))
            if (list.value.isEmpty())
                EmptyScreen()
            else {
                LazyColumn {
                    if (list.value.size > 2)
                        item {
                            StaggeredVerticalGrid(
                                maxColumnWidth = 160.dp,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                list.value.forEach { note ->
                                    val state = rememberDismissState(
                                        confirmStateChange = {
                                            if (it == DismissValue.DismissedToStart) {
                                                viewModel.deleteNote(note = note)
                                                Toast.makeText(
                                                    context,
                                                    "Note Deleted Successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                viewModel.getAll()
                                            }
                                            true
                                        }
                                    )
                                    SwipeToDismiss(
                                        state = state,
                                        background = {
                                            val color = when (state.dismissDirection) {
                                                DismissDirection.StartToEnd -> Color.Transparent
                                                DismissDirection.EndToStart -> Color.Red
                                                null -> Color.Transparent
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(color)
                                                    .padding(8.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.align(Alignment.CenterEnd)
                                                )
                                            }

                                        },
                                        dismissContent = {
                                            NoteCard(note = note, navController)
                                        },
                                        directions = setOf(DismissDirection.EndToStart)
                                    )
                                }

                            }
                        }
                    else
                        item {
                            list.value.forEach { note ->
                                val state = rememberDismissState(
                                    confirmStateChange = {
                                        if (it == DismissValue.DismissedToStart) {
                                            viewModel.deleteNote(note = note)
                                            Toast.makeText(
                                                context,
                                                "Note Deleted Successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.getAll()
                                        }
                                        true
                                    }
                                )
                                SwipeToDismiss(
                                    state = state,
                                    background = {
                                        val color = when (state.dismissDirection) {
                                            DismissDirection.StartToEnd -> Color.Transparent
                                            DismissDirection.EndToStart -> Color.Red
                                            null -> Color.Transparent
                                        }

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(color)
                                                .padding(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.align(Alignment.CenterEnd)
                                            )
                                        }

                                    },
                                    dismissContent = {
                                        NoteCard(note = note, navController)
                                    },
                                    directions = setOf(DismissDirection.EndToStart)
                                )
                            }
                        }
                }
            }
        }
    }
}





