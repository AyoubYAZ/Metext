package com.example.metext

import android.content.Intent
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Surface(color=Color(0xFFFFFFFF), modifier = Modifier.fillMaxSize()){
                Navigation()

            }
        }




    }
}
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            Splashscreen(navController = navController)
        }
        composable("main_screen") {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    navController.navigate("summarize")

                })
                {
                    Text(text = "summarize", fontSize = 18.sp)

                    // Ajoutez d'autres éléments de l'écran de résumé ici
                }
            }
        }
        composable("summarize") {
            Summarize()

        }


    }
}

@Composable
fun Splashscreen(navController: NavController) {
    LaunchedEffect(key1 = true){
        delay(5000L)
        navController.navigate("main_screen")
    }
    Column (modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.trend), "",modifier= Modifier
            .width(200.dp)
            .height(200.dp))

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Summarize(){
    val textState = remember{mutableStateOf("")}
    TextField(value = textState.value,
        onValueChange ={
            textState.value=it
        },label={Text (text="Input")}
    )




}
