package com.barangayconnect.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barangayconnect.app.model.Announcement
import com.barangayconnect.app.model.EmergencyAlert
import com.barangayconnect.app.model.EmergencyContact
import com.barangayconnect.app.model.IncidentReport
import com.barangayconnect.app.model.IncidentType
import com.barangayconnect.app.model.IncidentUrgency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class BarangayViewModel : ViewModel() {

    private val _reports = MutableStateFlow<List<IncidentReport>>(emptyList())
    val reports: StateFlow<List<IncidentReport>> = _reports.asStateFlow()

    private val _announcements = MutableStateFlow<List<Announcement>>(emptyList())
    val announcements: StateFlow<List<Announcement>> = _announcements.asStateFlow()

    private val _emergencyContacts = MutableStateFlow<List<EmergencyContact>>(emptyList())
    val emergencyContacts: StateFlow<List<EmergencyContact>> = _emergencyContacts.asStateFlow()

    private val _emergencyAlerts = MutableStateFlow<List<EmergencyAlert>>(emptyList())
    val emergencyAlerts: StateFlow<List<EmergencyAlert>> = _emergencyAlerts.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _emergencyContacts.value = listOf(
            EmergencyContact("Barangay Tanod Desk", "0289201234", "24/7 Community Patrol"),
            EmergencyContact("National Police Emergency", "911", "Tactical & Emergency Response"),
            EmergencyContact("BFP Fire Station", "0289213456", "Fire Suppression & Rescue"),
            EmergencyContact("Health Station Ambulance", "0289205678", "Medical First Aid")
        )
        _emergencyAlerts.value = listOf(
            EmergencyAlert(
                id = "1",
                title = "Active Calamity & Flood Watch",
                description = "San Jose Creek Water Monitoring Level 2. Low-lying Purok 2 Riverside residents on alert."
            )
        )
    }

    fun submitReport(
        type: IncidentType,
        title: String,
        description: String,
        purok: String,
        street: String,
        urgency: IncidentUrgency,
        isAnonymous: Boolean
    ) {
        viewModelScope.launch {
            val trackingCode = "BC-2026-" + (1000..9999).random()
            val newReport = IncidentReport(
                id = UUID.randomUUID().toString(),
                trackingCode = trackingCode,
                type = type,
                title = title,
                description = description,
                purok = purok,
                street = street,
                urgency = urgency,
                isAnonymous = isAnonymous
            )
            _reports.value = listOf(newReport) + _reports.value
        }
    }
}
