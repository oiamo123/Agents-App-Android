package com.example.agentsapp.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

/**
 * This handles queries of the sqlLite data base using the Room framework
 * (Similar to microsoft entity)
 */
@Dao
interface agentDAO {
    // Get's all agents and returns a list
    @Query("Select * from Agents")
    suspend fun getAllAgents(): List<Agent>

    // Get's agents by ID
    @Query("SELECT * FROM Agents WHERE AgentId = :id")
    suspend fun getAgentById(id: Int): Agent?

    // Deletes an agent
    @Delete
    suspend fun deleteAgent(agent: Agent)

    // Inserts an agent
    @Insert
    suspend fun insertAgent(agent: Agent)

    // Updates an agent
    @Update
    suspend fun updateAgent(agent: Agent)
}

/**
 * This is similar to microsoft entities dbContext
 * Entities are the tables and version is the migration number
 * If you wanted to add another table for example you would do
 * @Database(version = 1, entites = { Agent.class, Customers.class })
 */
@Database(entities = [Agent::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // This tells the database to reference the AgentDAO class for queries
    abstract fun agentDAO(): agentDAO
}