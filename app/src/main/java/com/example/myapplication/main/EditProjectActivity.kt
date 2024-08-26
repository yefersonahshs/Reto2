package com.example.myapplication.main

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.data.entity.ProjectEntity
import com.example.myapplication.data.model.Project
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class EditProjectActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var imageEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project)

        titleEditText = findViewById(R.id.etTitle)
        descriptionEditText = findViewById(R.id.etDescription)
        startDateEditText = findViewById(R.id.etStartDate)
        endDateEditText = findViewById(R.id.etEndDate)
        imageEditText = findViewById(R.id.etImage)

        val saveButton = findViewById<FloatingActionButton>(R.id.btnSave)
        saveButton.setOnClickListener {
            saveProject()
        }
    }


    private fun saveProject() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val startDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDateEditText.text.toString())
        val endDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(endDateEditText.text.toString())
        val image = "https://tiyolischool.edu.mx/wp-content/uploads/2019/02/tiyoli-school-las-primeras-tareas-del-nino-como-ayudarlo.jpg"

        val project = ProjectEntity(title = title, description = description, startDate = startDate, endDate = endDate, image = image)

        val projectDao = DatabaseHelper.getDatabase(this).projectDao()

        GlobalScope.launch {
            projectDao.insert(project)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
