/**
 * Creator: Gavin
 * Purpose: Display's inputs to either add / edit agent and also has functionality
 *          to delete an agent
 * Date: 2024-10-10
 */

package com.example.agentsapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agentsapp.R
import com.example.agentsapp.data.Agent
import com.example.agentsapp.data.AgentsViewModel

/**
 * Shows the agent and all corresponding data in inputs
 * agent: Agent to be displayed
 * navigate: method that when called, navigates back to the AgentsList page
 */

@Composable
fun AgentsDisplay(
    viewModel: AgentsViewModel = viewModel(),
    agent: Agent?,
    navigate: () -> Unit = {},
) {
    // These are all for state management
    var firstName = remember { mutableStateOf(agent?.agtFirstName ?: "") }
    var middleInitial = remember { mutableStateOf(agent?.agtMiddleInitial ?: "") }
    var lastName = remember { mutableStateOf(agent?.agtLastName ?: "") }
    var busPhone = remember { mutableStateOf(agent?.agtBusPhone ?: "") }
    var email = remember { mutableStateOf(agent?.agtEmail ?: "") }
    var position = remember { mutableStateOf(agent?.agtPosition ?: "") }

    // Places all text fields within a column
    Column (modifier = Modifier.padding(16.dp)) {
        TextFieldComponent(
            label = R.string.agent_first, // label to be shown: "First Name"
            value = firstName.value, // value
            onValueChange = { // validates passed in String -> "it" refers to passed in value
                firstName.value = it // set first name to passed in value
            }
        )

        // Repeat x times for all fields

        TextFieldComponent(
            label = R.string.agent_middle,
            value = middleInitial.value,
            onValueChange = {
                middleInitial.value = it
            }
        )
        TextFieldComponent(
            label = R.string.agent_last,
            value = lastName.value,
            onValueChange = {
                lastName.value = it
            }
        )
        TextFieldComponent(
            label = R.string.agent_phone,
            value = busPhone.value,
            onValueChange = {
                busPhone.value = it
            }
        )
        TextFieldComponent(
            label = R.string.agent_email,
            value = email.value,
            onValueChange = {
                email.value = it
            }
        )
        TextFieldComponent(
            label = R.string.agent_pos,
            value = position.value,
            onValueChange = {
                position.value = it
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth().padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Save button
            Button(onClick = {
                if (agent == null) {
                    val newAgent = Agent( // create an agent if agent is null
                        agtFirstName = firstName.value,
                        agtMiddleInitial = middleInitial.value,
                        agtLastName = lastName.value,
                        agtBusPhone = busPhone.value,
                        agtEmail = email.value,
                        agtPosition = position.value,
                        image = R.drawable.blank_profile_image,
                        agencyId = 1
                    )

                    viewModel.addAgent(newAgent) // add agent
                    navigate() // navigate back to list
                }

                agent?.let { // if agent is not null
                    nonNullAgent ->
                    nonNullAgent.agtFirstName = firstName.value
                    nonNullAgent.agtMiddleInitial = middleInitial.value
                    nonNullAgent.agtLastName = lastName.value
                    nonNullAgent.agtBusPhone = busPhone.value
                    nonNullAgent.agtEmail = email.value
                    nonNullAgent.agtPosition = position.value
                    nonNullAgent.image = R.drawable.blank_profile_image // sets a default image
                    nonNullAgent.agencyId = 1

                    viewModel.updateAgent(nonNullAgent) // update agent
                    navigate() // navigate back to main page
                }
            }) {
                Text(
                    text = stringResource(R.string.button_save)
                )
            }

            // Delete button
            Button(onClick = { // handles on click
                agent?.let { // check if agent is null, if it's not null
                    nonNullAgent ->
                    viewModel.deleteAgent(nonNullAgent) // delete the agent
                    navigate() // navigate back to agent list page
                }
            }) {
                Text( // displays "Delete" in button
                    text = stringResource(R.string.button_delete)
                )
            }
        }
    }
}

/**
 * TextFieldComponent that returns a text field and validation message
 * label: Label that will be provided to input
 * value: Value of input (needs to be tracked using state)
 * onValueChange: Method to be called when value changes (used to validate)
 * message: Validation feed back to be provided
 */

@Composable
fun TextFieldComponent(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row {
        TextField (
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(label)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            singleLine = true
        )
    }
}