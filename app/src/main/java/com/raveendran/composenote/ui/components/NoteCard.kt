package com.raveendran.composenote.ui.components

import Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raveendran.composenote.model.Note

@Composable
fun NoteCard(note: Note, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.NoteDetailsScreen.withArgs(note.title!!))
            }
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.DarkGray)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Forward Arrow")
        }
        Column {
            Text(text = "Title")
            Spacer(modifier = Modifier.height(5.dp))
            note.title?.let {
                Text(
                    text = it, style = TextStyle(
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,

                        )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Description")
            Spacer(modifier = Modifier.height(5.dp))
            note.desc?.let {
                Text(
                    text = it, style = TextStyle(
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
