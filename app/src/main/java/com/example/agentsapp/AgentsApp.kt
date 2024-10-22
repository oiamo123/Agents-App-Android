/**
 * Creator: Gavin
 * Purpose: Main controller for the application. Controls navigation and context
 * Date: 2024-10-10
 */

package com.example.agentsapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.agentsapp.data.Agent
import com.example.agentsapp.data.AgentsViewModel
import com.example.agentsapp.ui.AgentsDisplay
import com.example.agentsapp.ui.AgentsList

/**
 * Class used for defining routes
 */
enum class AgentsScreen(@StringRes val title: Int) {
    List(title = R.string.choose_agent),
    Display(title = R.string.edit_agent)
}

/**
 * This is a composable / component for the navigation bar
 * currentScreen: Keeps track of the current screen that's showing for the title
 * canNavigateBack: If canNavigateBack is true, an arrow will appear
 * modifier: Used for styling such as padding, color etc
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentAppBar(
    currentScreen: AgentsScreen, // the current screen for the title
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}, // for navigation between pages
    modifier: Modifier = Modifier // for styling
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) { // event listener for back arrow
                    Icon( // icon to be displayed
                        Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button) // for accessibility
                    )
                }
            }
        }
    )
}

/**
 * Controls navigation
 * viewModel: Provides a data "model" that's controlled using state
 * navController: Allows you to set routes within you application
 */

@Composable
fun AgentsApp(
    viewModel: AgentsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
) {
    viewModel.loadAgents() // Load all agents

    var selectedAgent = remember { mutableStateOf<Agent?>(null) } // Selected agent, value is null to begin with

    val backStackEntry by navController.currentBackStackEntryAsState() // Tracks whether or not you can go back
    val currentScreen = AgentsScreen.valueOf(backStackEntry?.destination?.route ?: AgentsScreen.List.name) // Gets the current screen

    Scaffold(
        topBar = {
            AgentAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost( // Used for navigation between pages
            navController = navController,
            startDestination = AgentsScreen.List.name,
            modifier = Modifier.padding(innerPadding)
        ) { // Has 2 routes, one for AgentsList and another for AgentsDisplay
            composable(route = AgentsScreen.List.name) {
                AgentsList(
                    setAgent = { selectedAgent.value = it }, // method that sets agent before going to AgentDisplay page
                    navigate = { // method that navigates the screen to AgentsDisplay
                        navController.navigate(AgentsScreen.Display.name)
                    },
                )
            }

            composable(route = AgentsScreen.Display.name) {
                AgentsDisplay(
                    agent = selectedAgent.value, // Pass the selected agent to the display screen
                    navigate = { // Method that navigates to AgentsList and deletes previous navigation stack
                        navController.navigate(AgentsScreen.List.name) {
                            popUpTo(AgentsScreen.List.name) { inclusive = true } // Clear the back stack when navigating back
                        }
                    }
                )
            }
        }
    }
}
