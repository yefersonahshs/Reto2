package com.example.myapplication.app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.app.main.EditProjectActivity
import com.example.myapplication.app.main.MainActivity
import com.example.myapplication.app.main.ProjectsAdapter
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.data.entity.ProjectEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ProjectViewModel : ViewModel() {


    fun saveProject(projectId: Int, title: String, description: String, startDateString: String, endDateString: String, image: String,context: Context) {
        val projectDao = DatabaseHelper.getDatabase(context).projectDao()
        val startDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDateString)
        val endDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(endDateString)
        val project: ProjectEntity

        if (projectId == -1) {
            project = ProjectEntity(title = title, description = description, startDate = startDate, endDate = endDate, image = image)
        } else {
            project = ProjectEntity(id = projectId, title = title, description = description, startDate = startDate, endDate = endDate, image = image)
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (projectId == -1) {
                projectDao.insert(project)
            } else {
                projectDao.update(project)
            }
        }
    }
    fun deleteProject(projectId: Int, context: EditProjectActivity) {
        val projectDao = DatabaseHelper.getDatabase(context).projectDao()
        viewModelScope.launch {
            val project = projectDao.getProjectById(projectId)
            viewModelScope.launch(Dispatchers.IO) {
                projectDao.delete(project)
            }
        }
    }

    fun getAllProjects(context: MainActivity): LiveData<List<ProjectEntity>> {
            val projectDao = DatabaseHelper.getDatabase(context).projectDao()
            return projectDao.getAllProjects()
        }
}
