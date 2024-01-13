package com.example.metext

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metext.model.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.FileInputStream


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
fun SummarizeText() {
    var inputText = remember { mutableStateOf("") }
    val outputText = remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
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
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Row {


            OutlinedButton(
                onClick = { scope.launch { drawerState.open() } },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                border = BorderStroke(width = 2.dp, color = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Localised description",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Red
                )


            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Summarize Text",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = inputText.value,
                onValueChange = { inputText.value = it },
                label = { Text("Text Input") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Créez une instance de OkHttpClient.
                val client = OkHttpClient()

                val mediaType = "application/json".toMediaTypeOrNull()
                val body = """
        {
            "language": "english",
            "summary_percent": 10,
            "text": "${inputText.value}"
        }
    """.trimIndent()
                    .toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("https://text-analysis12.p.rapidapi.com/summarize-text/api/v1.1")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader(
                        "X-RapidAPI-Key",
                        "43802b79fbmsh807e705a4347ca8p1c3ea7jsndfade0f9e1b6"
                    )
                    .addHeader("X-RapidAPI-Host", "text-analysis12.p.rapidapi.com")
                    .build()

                // Envoyez une requête à l'API.
                CoroutineScope(Dispatchers.IO).launch {
                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""
                    val apiResponse = Gson().fromJson(responseString, ApiResponse::class.java)
                    outputText.value = apiResponse.summary
                }
            })
            {
                Text("Summarize")
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = outputText.value,
                onValueChange = { outputText.value = it },
                label = { Text("Text Output") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Analyse() {
    val inputText = remember { mutableStateOf("") }
    val outputText = remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
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
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        },
        content = {
            Row {


                OutlinedButton(
                    onClick = { scope.launch { drawerState.open() } },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    border = BorderStroke(width = 2.dp, color = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Localised description",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red
                    )


                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sentiment Analysis ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    color=Color.Red,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputText.value,
                    onValueChange = { inputText.value = it },
                    label = { Text("Text Input") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Créez une instance de OkHttpClient.
                    val client = OkHttpClient()

                    val mediaType = "application/json".toMediaTypeOrNull()
                    val body = """
            {
                "language": "english",
                "text": "${inputText.value}"
            }
        """.trimIndent().toRequestBody(mediaType)

                    val request = Request.Builder()
                        .url("https://text-analysis12.p.rapidapi.com/sentiment-analysis/api/v1.1")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("X-RapidAPI-Key", "43802b79fbmsh807e705a4347ca8p1c3ea7jsndfade0f9e1b6")
                        .addHeader("X-RapidAPI-Host", "text-analysis12.p.rapidapi.com")
                        .build()

                    // Envoyez une requête à l'API.
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = client.newCall(request).execute()
                        val responseString = response.body?.string() ?: ""
                        val apiResponse = Gson().fromJson(responseString, ApiResponse::class.java)
                        outputText.value = apiResponse.sentiment
                    }
                }) {
                    Text("Analyse")
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = outputText.value,
                    onValueChange = { outputText.value = it },
                    label = { Text("Text Output") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtractTextFromFileScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
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
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        },
        content = {
            Row {


                OutlinedButton(
                    onClick = { scope.launch { drawerState.open() } },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    border = BorderStroke(width = 2.dp, color = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Localised description",
                        modifier = Modifier.size(36.dp),
                        tint = Color.Red
                    )


                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Extract Text ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    color=Color.Red,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                UploadAndAnalyzeButton()


                }
            }

    )
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadAndAnalyzeButton() {
    val context = LocalContext.current
    var result by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        // Handle the returned Uri
        coroutineScope.launch {
            val response = uri?.let { sendFileToApi(context, it) }
            if (response != null) {
                result = response.toString() // Update the result state with the response
            }
        }
    }

    OutlinedTextField(
        value = result,
        onValueChange = { result = it }, // Update the result state when the text field changes
        label = { Text("Extracted Text") } ,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    )

    Button(onClick = { launcher.launch("application/pdf") }) {
        Text("Upload Your File")
    }
}

suspend fun sendFileToApi(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val contentResolver = context.contentResolver
    val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
    val fileInputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
    val fileName = DocumentsContract.getDocumentId(uri) + ".pdf"
    val requestBody: RequestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("input_file", fileName, RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fileInputStream.readBytes()))
        .addFormDataPart("language", "english")
        .build()

    val request = Request.Builder()
        .url("https://text-analysis12.p.rapidapi.com/text-mining/api/v1.1")
        .post(requestBody)
        .addHeader("X-RapidAPI-Key", "43802b79fbmsh807e705a4347ca8p1c3ea7jsndfade0f9e1b6")
        .addHeader("X-RapidAPI-Host", "text-analysis12.p.rapidapi.com")
        .build()

    val response = client.newCall(request).execute()
    val responseBody = response.body?.string() ?: ""

    val json = JSONObject(responseBody)

    // Return only the 'text' field from the JSON
    json.optString("text", "")
}
