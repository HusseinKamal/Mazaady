package com.hussein.mazaady.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.CourseItemLayoutBinding
import com.hussein.mazaady.databinding.TagItemLayoutBinding

class CourseAdapter (private val context: Context, private val items: ArrayList<String>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CourseItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private var selectedPositions:String=""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<CourseItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.course_item_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
       holder.binding.courseTitle.text = item

        //Top Tag
        holder.binding.lyTag.lyContainer.setBackgroundResource(R.color.yellow)

        //Courses Tags
        val mCoursesList = ArrayList<String>()
        mCoursesList.add("6 Lessons")
        mCoursesList.add("UI/UX")
        mCoursesList.add("Free")
        holder.binding.rvCourseTags.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.rvCourseTags.adapter = TagsColoredAdapter(context,mCoursesList)

    }

    override fun getItemCount() = items.size
}