package com.example.agentsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agentsapp.R
import com.example.agentsapp.data.Agent
import com.example.agentsapp.data.AgentsViewModel

/**
 * Component that lists all of the agents
 * viewModel:
 * onAgentClick: method that handles how
 * agents: list of agents to be shown
 * modifier:
 */

@Composable
fun AgentsList(
    viewModel: AgentsViewModel = viewModel(),
    setAgent: (Agent?) -> Unit,
    navigate: () -> Unit = {}
) {
    val agents by viewModel.agents.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) { // group everything in a column (vertical)
        Row( // Separate row for add button
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                // when add button is clicked, navigate to empty DisplayAgent page
                setAgent(null)
                navigate()
            }) { // Icon and text for button
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.button_add)
                )
                Text(
                    text = stringResource(R.string.button_add) // text for add button ("Add")
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn {
                items(agents) { agent ->
                    AgentItem(agent, onItemClick = {
                        setAgent(agent)
                        navigate()
                    })
                }
            }
        }
    }

    // trigger loading of agents when the composable is shown
    LaunchedEffect(Unit) {
        viewModel.loadAgents()
    }
}

/**
 * AgentItem provides an image and the name of the agent
 * agent: Agent to be shown
 * onItemClick: Takes an agent as an argument and then defines what happens
 */
@Composable
fun AgentItem(agent: Agent, onItemClick: (Agent) -> Unit) {
        Column ( // group everything in a column
            // makes column clickable and adds padding
            modifier = Modifier.padding(16.dp).clickable { onItemClick(agent) },
            // center items horizontally
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(agent.image),
                contentDescription = stringResource(R.string.agent_img)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Name: ${agent.agtFirstName} ${agent.agtLastName}"
            )
        }
}