package com.example.myapplication.main

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.data.entity.ProjectEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
class EditProjectActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var imageEditText: EditText
    private var projectId: Int? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project)

        titleEditText = findViewById(R.id.etTitle)
        descriptionEditText = findViewById(R.id.etDescription)
        startDateEditText = findViewById(R.id.etStartDate)
        endDateEditText = findViewById(R.id.etEndDate)
        imageEditText = findViewById(R.id.etImage)

        projectId = intent.getIntExtra("projectId", -1)
        if (projectId != -1) {
            loadProject()
        }

        val saveButton = findViewById<FloatingActionButton>(R.id.btnSave)
        saveButton.setOnClickListener {
            saveProject()
        }

        val deleteButton = findViewById<FloatingActionButton>(R.id.btnDelete)
        deleteButton.setOnClickListener {
            deleteProject()
        }
    }

    private fun loadProject() {
        val projectDao = DatabaseHelper.getDatabase(this).projectDao()
        GlobalScope.launch {
            val project = projectDao.getProjectById(projectId!!)
            withContext(Dispatchers.Main) {
                titleEditText.setText(project.title)
                descriptionEditText.setText(project.description)
                startDateEditText.setText(dateFormat.format(project.startDate))
                endDateEditText.setText(dateFormat.format(project.endDate))
                imageEditText.setText(project.image)
            }
        }
    }

    private fun saveProject() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val startDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDateEditText.text.toString())
        val endDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(endDateEditText.text.toString())
        val image = imageEditText.text.toString()

        val project = ProjectEntity(id = projectId!!, title = title, description = description, startDate = startDate, endDate = endDate, image = image)

        val projectDao = DatabaseHelper.getDatabase(this).projectDao()

        GlobalScope.launch {
            projectDao.update(project)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun deleteProject() {
        val projectDao = DatabaseHelper.getDatabase(this).projectDao()

        GlobalScope.launch {
            val project = projectDao.getProjectById(projectId!!)
            projectDao.delete(project)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
