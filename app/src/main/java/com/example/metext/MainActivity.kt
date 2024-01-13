package com.example.metext



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.appdistribution.InterruptionLevel
import com.google.firebase.appdistribution.appDistribution
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Firebase.appDistribution.showFeedbackNotification(
            // Text providing notice to your testers about collection and
            // processing of their feedback data
            "test",
            // The level of interruption for the notification
            InterruptionLevel.HIGH)
        //Firebase.appDistribution.startFeedback("Merci Pour le feedback")

        setContent{
            Surface(color=Color(0xFFFFFFFF), modifier = Modifier.fillMaxSize()){
                Navigation()




            }
        }




    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mainscreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(Icons.Default.Build, Icons.Default.Edit, Icons.Default.AddCircle)
    val selectedItem = remember { mutableStateOf(items[0]) }
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DismissibleDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(text = "Summarize") },
                        selected = item == selectedItem.value,
                        onClick = {
                            navController.navigate("summarize")
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        },
        content = {
            Row(
                    
            ) {


                OutlinedButton(onClick = { scope.launch { drawerState.open() } },
                contentPadding=ButtonDefaults.ButtonWithIconContentPadding,
                    border = BorderStroke(width = 2.dp, color = Color.White)) {
                    Icon(imageVector = Icons.Outlined.Menu,
                        contentDescription ="Localised description",
                        modifier=Modifier.size(20.dp),
                        tint = Color.Red
                    )


                }}

            Column (modifier = Modifier.fillMaxSize() ,
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
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
                OutlinedButton( onClick = { navController.navigate("analyse") } ,
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
                OutlinedButton( onClick = { navController.navigate("extract") } ,
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
    )

}

