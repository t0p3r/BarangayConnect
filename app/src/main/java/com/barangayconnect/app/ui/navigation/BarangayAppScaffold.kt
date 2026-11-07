package com.barangayconnect.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barangayconnect.app.ui.screens.AnnouncementsScreen
import com.barangayconnect.app.ui.screens.DocumentRequestScreen
import com.barangayconnect.app.ui.screens.EmergencyScreen
import com.barangayconnect.app.ui.screens.OfficialsDirectoryScreen
import com.barangayconnect.app.ui.screens.ReportIncidentScreen
import com.barangayconnect.app.viewmodel.BarangayViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Announcements : Screen("announcements", "News", Icons.Default.Campaign)
    data object Emergency : Screen("emergency", "Emergency", Icons.Default.Warning)
    data object Reports : Screen("reports", "Report", Icons.Default.AddLocationAlt)
    data object Documents : Screen("documents", "Request", Icons.Default.Description)
    data object Directory : Screen("directory", "Directory", Icons.Default.People)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarangayAppScaffold(viewModel: BarangayViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        Screen.Announcements,
        Screen.Emergency,
        Screen.Reports,
        Screen.Documents,
        Screen.Directory
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Barangay Connect", style = MaterialTheme.typography.titleMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Emergency.route) }) {
                        Icon(
                            Icons.Default.Emergency,
                            contentDescription = "SOS",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navItems.forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Announcements.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Announcements.route) { AnnouncementsScreen(viewModel) }
            composable(Screen.Emergency.route) { EmergencyScreen(viewModel) }
            composable(Screen.Reports.route) { ReportIncidentScreen(viewModel) }
            composable(Screen.Documents.route) { DocumentRequestScreen(viewModel) }
            composable(Screen.Directory.route) { OfficialsDirectoryScreen(viewModel) }
        }
    }
}
