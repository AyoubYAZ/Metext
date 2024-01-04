package com.example.metext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class Summarize : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Surface(color= Color(0xFFFFFFFF), modifier = Modifier.fillMaxSize()){

            }
        }




    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Func(){
    val textState = remember{ mutableStateOf("") }
    TextField(value = textState.value,
        onValueChange ={
            textState.value=it
        },label={ Text (text="Input") }
    )




}