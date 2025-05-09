package com.example.firebaseconcloud

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.firebaseconcloud.ui.theme.FirebaseConCloudTheme

class MainActivity : ComponentActivity() {

    private lateinit var fcmReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mensaje = mutableStateOf("Esperando notificación...")

        // Registrar el BroadcastReceiver
        fcmReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val title = intent?.getStringExtra("title") ?: "Sin título"
                val body = intent?.getStringExtra("body") ?: "Sin mensaje"
                val mensajeCompleto = "$title: $body"
                mensaje.value = mensajeCompleto
                Toast.makeText(this@MainActivity, mensajeCompleto, Toast.LENGTH_LONG).show()
            }
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(fcmReceiver, IntentFilter("FCM_MESSAGE"))

        setContent {
            FirebaseConCloudTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MensajePantalla(mensaje.value)
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcmReceiver)
        super.onDestroy()
    }
}

@Composable
fun MensajePantalla(mensaje: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Último mensaje recibido:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}