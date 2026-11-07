package com.barangayconnect.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.barangayconnect.app.ui.navigation.BarangayAppScaffold
import com.barangayconnect.app.viewmodel.BarangayViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarangayConnectTheme {
                val viewModel: BarangayViewModel = viewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BarangayAppScaffold(viewModel = viewModel)
                }
            }
        }
    }
}

private val BarangayEmerald = Color(0xFF047857)
private val EmergencyRed = Color(0xFFDC2626)

@Composable
fun BarangayConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF10B981),
            secondary = Color(0xFF38BDF8),
            tertiary = EmergencyRed,
            background = Color(0xFF0B1120),
            surface = Color(0xFF1E293B)
        )
    } else {
        lightColorScheme(
            primary = BarangayEmerald,
            secondary = Color(0xFF0284C7),
            tertiary = EmergencyRed,
            background = Color(0xFFF8FAFC),
            surface = Color.White
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
