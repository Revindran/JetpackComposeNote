package com.raveendran.composenote.ui.screens

import Screen
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.raveendran.composenote.R
import com.raveendran.composenote.model.Note
import com.raveendran.composenote.ui.viewmodels.NoteViewModel

@ExperimentalAnimationApi
@Composable
fun NoteDetailScreen(
    navController: NavController,
    id: String?,
    viewModel: NoteViewModel = hiltViewModel()
) {
    viewModel.getNote(id.toString())
    val c = LocalContext.current
    val note = viewModel.note.collectAsState(initial = null).value
    if (note != null) {
        val title by remember { mutableStateOf(note.title) }
        val desc by remember { mutableStateOf(note.desc) }

        var titleText by remember { mutableStateOf(note.title) }
        var noteText by remember { mutableStateOf(note.desc) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.TwoTone.ArrowBack,
//                                painter = painterResource(id = R.drawable.ic_back_btn),
                                contentDescription = null
                            )
                        }

                        Text(text = "Update Note")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            viewModel.deleteNote(note = note)
                            navController.popBackStack()
                            Toast.makeText(c, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = {
                            shareNote(
                                activity = c as Activity,
                                title = titleText!!,
                                note = noteText!!
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = titleText!!,
                    onValueChange = {
                        titleText = it
                    },
                    label = { Text(text = "Enter Title") },
                    textStyle = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = noteText!!,
                    onValueChange = {
                        noteText = it
                    },
                    label = { Text(text = "Enter Description") },
                    textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Light),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (title != titleText!!.trim() || desc != noteText!!.trim()) {
                    Button(
                        onClick = {
                            viewModel.insertNote(
                                note = Note(
                                    id = note.id,
                                    title = titleText,
                                    desc = noteText
                                )
                            )
                            Toast.makeText(c, "Note updated Successfully", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(Screen.NoteScreen.route) {
                                popUpTo(Screen.NoteScreen.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}

fun shareNote(activity: Activity, title: String, note: String) {
    val shareMsg = activity.getString(
        R.string.text_message_share,
        title,
        note
    )
    val intent = ShareCompat.IntentBuilder(activity)
        .setType("text/plain")
        .setText(shareMsg)
        .intent
    activity.startActivity(Intent.createChooser(intent, null))
}