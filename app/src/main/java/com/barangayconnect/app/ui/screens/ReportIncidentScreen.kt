package com.barangayconnect.app.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barangayconnect.app.model.IncidentType
import com.barangayconnect.app.model.IncidentUrgency
import com.barangayconnect.app.viewmodel.BarangayViewModel

@Composable
fun ReportIncidentScreen(viewModel: BarangayViewModel) {
    var selectedType by remember { mutableStateOf(IncidentType.ROAD_DAMAGE) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val purok by remember { mutableStateOf("Purok 3 Mabini") }
    var street by remember { mutableStateOf("") }
    val urgency by remember { mutableStateOf(IncidentUrgency.MODERATE) }
    var isAnonymous by remember { mutableStateOf(false) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedBitmap = bitmap
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Report Community Issue", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Report road potholes, uncollected garbage, or flash flooding directly to the Barangay Hall.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Type Segmented Group
        Text("Incident Category", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = selectedType == IncidentType.ROAD_DAMAGE,
                onClick = { selectedType = IncidentType.ROAD_DAMAGE },
                label = { Text("Road Damage") },
                leadingIcon = { Icon(Icons.Default.Construction, contentDescription = null) }
            )
            FilterChip(
                selected = selectedType == IncidentType.GARBAGE,
                onClick = { selectedType = IncidentType.GARBAGE },
                label = { Text("Garbage") },
                leadingIcon = { Icon(Icons.Default.DeleteOutline, contentDescription = null) }
            )
            FilterChip(
                selected = selectedType == IncidentType.FLOODING,
                onClick = { selectedType = IncidentType.FLOODING },
                label = { Text("Flooding") },
                leadingIcon = { Icon(Icons.Default.WaterDrop, contentDescription = null) }
            )
        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Incident Title / Summary") },
            placeholder = { Text("e.g. Deep pothole front of chapel") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Detailed Description") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = street,
            onValueChange = { street = it },
            label = { Text("Street Name & Landmark") },
            placeholder = { Text("e.g. Rizal St. corner Mabini, near Sari-sari") },
            modifier = Modifier.fillMaxWidth()
        )

        // Camera Action Button
        OutlinedButton(
            onClick = { cameraLauncher.launch(null) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.PhotoCamera, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(if (capturedBitmap != null) "Photo Attached (Retake)" else "Take Photo of Incident")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isAnonymous, onCheckedChange = { isAnonymous = it })
            Spacer(Modifier.width(8.dp))
            Text("Submit Anonymously (Hide Name & Phone)")
        }

        Button(
            onClick = {
                viewModel.submitReport(
                    type = selectedType,
                    title = title,
                    description = description,
                    purok = purok,
                    street = street,
                    urgency = urgency,
                    isAnonymous = isAnonymous
                )
                showSuccessDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = title.isNotBlank() && description.isNotBlank()
        ) {
            Icon(Icons.Default.Send, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Submit Report to Barangay Hall")
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                title = ""
                description = ""
                street = ""
                capturedBitmap = null
            },
            title = { Text("Report Submitted") },
            text = { Text("Your incident report has been forwarded to the Barangay Hall for review and dispatch.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        title = ""
                        description = ""
                        street = ""
                        capturedBitmap = null
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
