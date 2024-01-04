package com.example.metext

import android.content.Intent
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
fun Mainscreen(navController: NavController){






    Column (modifier= Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = "MeText",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            color=Color.Red,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        Spacer(modifier = Modifier.height(120.dp))
        OutlinedButton( onClick = { navController.navigate("summarize") } ,
            modifier= Modifier
                .height(50.dp)
                .width(180.dp),
            border = BorderStroke(width=1.dp,color=Color.Red),
            colors = ButtonDefaults.buttonColors(
                containerColor=Color.LightGray,
                contentColor = Color.Red
            ),
            shape = RoundedCornerShape(0.dp) ) {
            Text("Summarize Text")
        }
        OutlinedButton( onClick = { navController.navigate("summarize") } ,
            modifier= Modifier
                .height(50.dp)
                .width(180.dp),
            border = BorderStroke(width=1.dp,color=Color.Red),
            colors = ButtonDefaults.buttonColors(
                containerColor=Color.LightGray,
                contentColor = Color.Red
            ),
            shape = RoundedCornerShape(0.dp) ) {
            Text("Sentiment Analysis")
        }
        OutlinedButton( onClick = { navController.navigate("summarize") } ,
            modifier= Modifier
                .height(50.dp)
                .width(180.dp),
            border = BorderStroke(width=1.dp,color=Color.Red),
            colors = ButtonDefaults.buttonColors(
                containerColor=Color.LightGray,
                contentColor = Color.Red
            ),
            shape = RoundedCornerShape(0.dp) ) {
            Text("Extract Text")
        }

    }

        }




