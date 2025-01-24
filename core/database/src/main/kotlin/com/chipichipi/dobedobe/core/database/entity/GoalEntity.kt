package com.chipichipi.dobedobe.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chipichipi.dobedobe.core.database.entity.GoalEntity.Companion.TABLE_NAME
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.datetime.Instant

@Entity(tableName = TABLE_NAME)
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val isPinned: Boolean,
    val state: Goal.State,
    val createdAt: Instant,
    val completedAt: Instant?,
) {
    companion object {
        const val TABLE_NAME = "Goal"
    }
}
