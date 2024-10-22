/**
 * Creator: Gavin
 * Project overview:
 *
 *     AgentsApp: Manages navigation between screens and keeps track of
 *     selected agents data
 *
 *     Routes and Navigation:
 *          - NavHost: Defines the different screens (routes) that can be accessed
 *     There are 2 primary routes:
 *          - AgentsList: Lists all agents
 *          - AgentsDisplay: Displays data for a selected agent
 *
 *     Agent Selection: The selectedAgent property is initially null. When an agent
 *                      is clicked in AgentsList, the property is updated with that
 *                      agent's data and the app navigates to the AgentsDisplay
 *                      screen
 *
 *     Button Actions: When a user clicks Save or Delete, the app navigates back to
 *     the list and sets the agent to null. The add button passes in a null agent to
 *     AgentsDisplay.
 *
 *     AgentDAO: Similar to DB classes in Java. Defines CRUD methods for agents
 *
 *     Agent: Agent uses Room to a schema similar to Microsoft Entity. It has
 *            annotations such as @PrimaryKey.
 *
 * Some terms and syntax that may be beneficial:
 *    viewModel: Used to retain UI-related data. Unlike traditional Android views
 *               (Activities/Fragments), which are destroyed during screen rotations
 *               the viewModel keeps the data alive.
 *
 *    lambdaFunctions: They're similar to inline functions in Java
 *                     Similar to a method when defining the arguments, you can
 *                     do the same with lambda's:
 *                     val myLambda: (String) -> Unit
 *
 *                     This lambda takes a String, prints it and returns Unit
 *                     which is in a nutshell void.
 *
 *                     Usage: myLambda = { value -> println(value) }
 *
 *                     Alternatively, you can omit "value ->" and write it as:
 *                     myMethod(myLambda = { println(it) })
 *
 *   @Composable functions: A composable function defines UI elements similar to
 *                          React or Flutter.
 *
 *   State: State in Jetpack Compose is handled using "remember" and "mutableStateOf".
 *          Jetpack Compose keeps track of which values it's supposed to remember. When
 *          A variable that's been defined with remember is updated, it triggers a
 *          reload of the Composable function showing the new values.
 * Date: 2024-10-10
 */

package com.example.agentsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.agentsapp.data.Agent
import com.example.agentsapp.data.AppDatabase
import com.example.agentsapp.data.agentDAO
import com.example.agentsapp.ui.theme.AgentsAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * This is the main activity
 * It includes a method that initializes the database
 */
class MainActivity : ComponentActivity() {
    private lateinit var _db: AppDatabase // Variable for database
    private lateinit var _agentDAO: agentDAO // Variable for agentDB class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Builds database
        _db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "agents-database"
        ).build()

        _agentDAO = _db.agentDAO()

        // Check if database is empty and insert initial data
        GlobalScope.launch {
            if (_agentDAO.getAllAgents().isEmpty()) {
                insertInitialAgents()
            }
        }

        enableEdgeToEdge()
        setContent { // sets content of screen
            AgentsAppTheme {
                AgentsApp() // opens the app
            }
        }
    }

    private suspend fun insertInitialAgents() {
        // Creates a list of agents to be inserted
        val initialAgents = listOf(
            Agent(1, "Janet", null, "Delton", "(403) 210-7801", "janet.delton@travelexperts.com", "Senior Agent", R.drawable.blank_profile_image, 1),
            Agent(2, "Judy", null, "Lisle", "(403) 210-7802", "judy.lisle@travelexperts.com", "Intermediate Agent", R.drawable.blank_profile_image, 1),
            Agent(3, "Dennis", "C", "Reynolds", "(403) 210-7843", "dennis.reynolds@travelexperts.com", "Junior Agent", R.drawable.blank_profile_image, 1),
            Agent(4, "John", "D", "Coville", "(403) 210-7823", "john.coville@travelexperts.com", "Intermediate Agent", R.drawable.blank_profile_image, 1)
        )

        // inserts each agent into the database
        initialAgents.forEach{ agent ->
            _agentDAO.insertAgent(agent)
        }
    }
}