package com.example.myapplication.app.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.EditText
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.app.viewmodels.ProjectViewModel
import com.example.myapplication.data.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
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
    private lateinit var editLayout: ScrollView
    private var projectId: Int? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val viewModel: ProjectViewModel by viewModels()



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project)
        editLayout = findViewById<ScrollView>(R.id.edit_layout)
        setupLayout()
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

    private fun setupLayout() {
        val backgroundUrl = "https://img.freepik.com/vector-premium/fondo-abstracto-puntos-semitono_444390-12787.jpg?w=2000"

        Picasso.get().load(backgroundUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                editLayout.background = BitmapDrawable(resources, bitmap)
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        })
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
        val startDate = startDateEditText.text.toString()
        val endDate = endDateEditText.text.toString()
        val image = "https://tiyolischool.edu.mx/wp-content/uploads/2019/02/tiyoli-school-las-primeras-tareas-del-nino-como-ayudarlo.jpg"

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                viewModel.saveProject(
                    projectId!!,
                    title,
                    description,
                    startDate,
                    endDate,
                    image,
                    this@EditProjectActivity
                )
            }
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun deleteProject() {
        viewModel.deleteProject(projectId!!,context= this)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
