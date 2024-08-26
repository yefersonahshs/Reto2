package com.example.myapplication.app.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.app.viewmodels.ProjectViewModel
import com.example.myapplication.data.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var projectAdapter: ProjectsAdapter
    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var editLayout: ConstraintLayout


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editLayout = findViewById(R.id.main_layout)
        setupLayout()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        projectAdapter = ProjectsAdapter { project ->
            val intent = Intent(this, EditProjectActivity::class.java)
            intent.putExtra("projectId", project.id)
            startActivity(intent)
        }
        recyclerView.adapter = projectAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, EditProjectActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadProjects()
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        loadProjects()
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

    override fun onResume() {
        super.onResume()
        loadProjects()
    }


    override fun onRestart() {
        super.onRestart()
        loadProjects()
    }
    private fun loadProjects() {
        viewModel.getAllProjects(this).observe(this, Observer { projects ->
            projectAdapter.setProjects(projects)
        })
    }
}
