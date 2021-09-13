package com.raveendran.composenote.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.raveendran.composenote.R
import com.raveendran.composenote.ui.viewmodels.NoteViewModel

@Composable
fun SearchBar(viewModel: NoteViewModel = hiltViewModel()) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                performSearch(query = text, viewModel = viewModel)
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = if (text.isNotEmpty()) Color.White else Color.Gray
                )
            },


            placeholder = {
                Text(text = "Search a note")
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private fun performSearch(query: String, viewModel: NoteViewModel) {
    println("Search Action")
    if (query.isEmpty()) {
        println("Search word empty")
        viewModel.getAll()
    } else viewModel.searchANote(query = query.trim())
}