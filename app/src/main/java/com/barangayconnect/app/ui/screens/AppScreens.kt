package com.barangayconnect.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barangayconnect.app.viewmodel.BarangayViewModel

@Composable
fun AnnouncementsScreen(viewModel: BarangayViewModel) {
    val announcements by viewModel.announcements.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Barangay Announcements", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Official updates, public advisories, and community programs.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (announcements.isEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Campaign, contentDescription = null)
                            Spacer(Modifier.height(8.dp))
                            Text("No announcements at this time.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        } else {
            items(announcements) { announcement ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(announcement.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(announcement.body, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "${announcement.author} • ${announcement.datePosted}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentRequestScreen(viewModel: BarangayViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Request Barangay Documents", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Request Barangay Clearance, Certificate of Residency, Certificate of Indigency, and Business Permits online.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        val availableDocs = listOf(
            "Barangay Clearance" to "For employment, local IDs, and postal transactions (Fee: ₱50.00)",
            "Certificate of Residency" to "Proof of residency within the barangay jurisdiction (Fee: ₱30.00)",
            "Certificate of Indigency" to "For medical, legal, and educational assistance (Free of charge)",
            "Barangay Business Clearance" to "For small business and sari-sari store permits (Fee: ₱150.00)"
        )

        items(availableDocs) { (title, desc) ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Description, contentDescription = null)
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(title, style = MaterialTheme.typography.titleSmall)
                        Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun OfficialsDirectoryScreen(viewModel: BarangayViewModel) {
    val officials = listOf(
        "Hon. Juan Dela Cruz" to "Punong Barangay (Barangay Captain)",
        "Hon. Maria Santos" to "Barangay Kagawad - Peace & Order",
        "Hon. Roberto Gomez" to "Barangay Kagawad - Health & Sanitation",
        "Hon. Elena Bautista" to "Barangay Kagawad - Infrastructure",
        "Hon. Antonio Reyes" to "Barangay Kagawad - Education & Sports",
        "Hon. Patricia Lim" to "SK Chairperson",
        "Engr. Carlos Mendoza" to "Barangay Secretary",
        "Luzviminda Ramos" to "Barangay Treasurer"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Barangay Officials Directory", style = MaterialTheme.typography.headlineSmall)
            Text(
                "List of incumbent public servants of the Barangay Government.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(officials) { (name, position) ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(name, style = MaterialTheme.typography.titleSmall)
                        Text(position, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
