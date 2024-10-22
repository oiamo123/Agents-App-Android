# Agents App - Android
A small android application I built for an assignment in school. The goal of the application was to display all agents and when an agent was clicked on, to open a new activity and show the agents details. I decided however to go outside of the classes assignment, taught myself some Kotlin and applied it to this project.

Overview:
Project overview:
- AgentsApp: Manages navigation between screens and keeps track ofselected agents data
  
- Routes and Navigation:
  - NavHost: Defines the different screens (routes) that can be accessed
  - There are 2 primary routes:
    - AgentsList: Lists all agents
    - AgentsDisplay: Displays data for a selected agent
  - Agent Selection: The selectedAgent property is initially null. When an
                     agent is clicked in AgentsList, the property is updated
                     with that agent's data and the app navigates to the AgentsDisplay screen
      
- Button Actions: When a user clicks Save or Delete, the app navigates back to
                  the list and sets the agent to null. The add button passes
                  in a null agent toAgentsDisplay.
  
- AgentDAO: Similar to DB classes in Java. Defines CRUD methods for agents
  
- Agent: Agent uses Room to a schema similar to Microsoft Entity. It has
         annotations such as @PrimaryKey.

What I learned:
- viewModel: Used to retain UI-related data. The viewModel keeps data alive between screen rotations unlike traditional activites/fragments
- Kotlin specific lambda functions: You can use the word the keyword "it" to reference the passed in parameter alternative to defining one yourself (ie: myVal -> { println(myVal) })
- Composable functions: Defines UI elements similar to React
- State: Defined using the "remember" keyword and "mutableStateOf", Jetpack Compose keeps track of variables defined using the remember keyword. When these values update, it triggers a reload of the Composable showing the new values
- Room: Very similar to Microsoft Entity, used for data-management
- Data Access Object: Defines methods for SQL operations using annotations

What I'd like to learn:
- I'd like to finish Google's Kotlin course
- Solidify data-management DAO's and Room
- Build another project
