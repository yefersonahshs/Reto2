package com.example.myapplication.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.entity.ProjectEntity

@Dao
interface ProjectDao {
    @Insert
    fun insert(project: ProjectEntity)

    @Query("SELECT * FROM projects")
    fun getAllProjects(): LiveData<List<ProjectEntity>>
}
