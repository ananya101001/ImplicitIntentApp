package com.example.implicitintent_weblinkandphonecallapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.implicitintent_weblinkandphonecallapp.ui.theme.ImplicitIntentWebLinkAndPhoneCallAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImplicitIntentWebLinkAndPhoneCallAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current  // Get context for launching intents

    var url by remember { mutableStateOf(TextFieldValue("http://www.sjsu.edu")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Implicit Intents", style = MaterialTheme.typography.headlineMedium)

        // URL Input Field
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Enter URL") }
        )

        // Open URL Button
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.text))
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Handle case where no browser is installed
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Launch URL")
        }

        // Phone Input Field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Enter Phone Number") }
        )

        // Dial Phone Number Button
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber.text}"))
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Handle case where no dialer is installed
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Call Number")
        }

        // Close App Button
        Button(
            onClick = { (context as? ComponentActivity)?.finishAffinity() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Close App")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ImplicitIntentWebLinkAndPhoneCallAppTheme {
        MainScreen()
    }
}
