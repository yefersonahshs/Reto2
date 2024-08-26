package com.example.myapplication.app.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.entity.ProjectEntity
import com.example.myapplication.data.model.Project
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale


class ProjectsAdapter(private val onProjectClick: (ProjectEntity) -> Unit) : RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder>() {

    private val projects = mutableListOf<ProjectEntity>()

    fun setProjects(projects: List<ProjectEntity>) {
        this.projects.clear()
        this.projects.addAll(projects)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(projects[position])
        holder.itemView.setOnClickListener {
            onProjectClick(projects[position])
        }
    }

    override fun getItemCount() = projects.size


    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val description: TextView = itemView.findViewById(R.id.tvDescription)
        private val startDate: TextView = itemView.findViewById(R.id.tvStartDate)
        private val endDate: TextView = itemView.findViewById(R.id.tvEndDate)
        private val image: ImageView = itemView.findViewById(R.id.ivImage)

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        fun bind(project: ProjectEntity) {
            title.text = project.title
            description.text = project.description
            startDate.text = dateFormat.format(project.startDate)
            endDate.text = dateFormat.format(project.endDate)
            if (!project.image.isNullOrEmpty()) {
                Picasso.get().load(project.image).into(image)
            }
        }
    }
}
