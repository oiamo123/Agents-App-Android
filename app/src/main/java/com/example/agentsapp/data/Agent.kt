package com.example.agentsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the Agent DB class
 */
@Entity(tableName = "Agents") // table name
data class Agent(
    // Table properties
    // The question marks allow null
    @PrimaryKey(autoGenerate = true) val agentId: Int = 0,
    var agtFirstName: String?,
    var agtMiddleInitial: String?,
    var agtLastName: String?,
    var agtBusPhone: String?,
    var agtEmail: String?,
    var agtPosition: String?,
    var image: Int,
    var agencyId: Int?
)