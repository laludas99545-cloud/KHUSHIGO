package com.luken.khushigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KhushiGoMainScreen()
                }
            }
        }
    }
}

@Composable
fun KhushiGoMainScreen() {
    var selectedPortal by remember { mutableStateOf("Customer Portal") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "KHUSHIGO",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Powered by LUKEN",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { selectedPortal = "Customer Portal" }) { Text("Customer") }
            Button(onClick = { selectedPortal = "Rider Portal" }) { Text("Rider") }
            Button(onClick = { selectedPortal = "Merchant Portal" }) { Text("Merchant") }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Box(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Welcome to $selectedPortal",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
