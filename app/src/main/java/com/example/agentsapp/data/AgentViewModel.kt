package com.example.agentsapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch

class AgentsViewModel(application: Application) : AndroidViewModel(application) {
    private val agentDao: agentDAO = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "agents-database"
    ).build().agentDAO()

    private val _agents = MutableLiveData<List<Agent>>()
    val agents: LiveData<List<Agent>> = _agents

    // fetch agents from the database asynchronously
    fun loadAgents() {
        viewModelScope.launch {
            val agentsList = agentDao.getAllAgents() // retrieve all agents from DB
            _agents.postValue(agentsList) // update LiveData
        }
    }

    // add a new agent
    fun addAgent(agent: Agent) {
        viewModelScope.launch {
            agentDao.insertAgent(agent)
            loadAgents() // refresh the agent list after inserting a new one
        }
    }

    // delete an agent
    fun deleteAgent(agent: Agent) {
        viewModelScope.launch {
            agentDao.deleteAgent(agent)
            loadAgents() // refresh the agent list after deletion
        }
    }

    // update an agent
    fun updateAgent(agent: Agent) {
        viewModelScope.launch {
            agentDao.updateAgent(agent)
            loadAgents() // refresh the agent list after updating
        }
    }
}