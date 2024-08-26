package com.example.myapplication.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.data.model.Project
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var projectAdapter: ProjectsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        projectAdapter = ProjectsAdapter()
        recyclerView.adapter = projectAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, EditProjectActivity::class.java)
            startActivity(intent)
        }

        loadProjects()
    }

    private fun loadProjects() {
        val projectDao = DatabaseHelper.getDatabase(this).projectDao()
        projectDao.getAllProjects().observe(this, Observer { projects ->
            projectAdapter.setProjects(projects)
        })
    }
}
