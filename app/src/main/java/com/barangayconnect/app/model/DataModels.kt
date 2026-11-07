package com.barangayconnect.app.model

enum class IncidentType {
    ROAD_DAMAGE,
    GARBAGE,
    FLOODING,
    STREETLIGHT,
    OBSTRUCTION
}

enum class IncidentUrgency {
    LOW,
    MODERATE,
    HIGH
}

enum class IncidentStatus {
    SUBMITTED,
    UNDER_REVIEW,
    DISPATCHED,
    RESOLVED
}

data class IncidentReport(
    val id: String,
    val trackingCode: String,
    val type: IncidentType,
    val title: String,
    val description: String,
    val purok: String,
    val street: String,
    val urgency: IncidentUrgency,
    val photoUri: String? = null,
    val status: IncidentStatus = IncidentStatus.SUBMITTED,
    val isAnonymous: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class Announcement(
    val id: String,
    val title: String,
    val category: String,
    val body: String,
    val author: String,
    val datePosted: String,
    val isPinned: Boolean = false
)

data class EmergencyContact(
    val name: String,
    val phone: String,
    val description: String
)

data class EmergencyAlert(
    val id: String,
    val title: String,
    val description: String,
    val severity: String = "HIGH"
)

data class DocumentRequest(
    val id: String,
    val trackingCode: String,
    val documentType: String,
    val applicantName: String,
    val purok: String,
    val purpose: String,
    val fee: Double,
    val status: String
)
