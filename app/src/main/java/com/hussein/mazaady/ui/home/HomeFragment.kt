package com.hussein.mazaady.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.FragmentHomeBinding
import com.hussein.mazaady.presentation.adapter.ActiveVideoCallProfileAdapter
import com.hussein.mazaady.presentation.adapter.CourseAdapter
import com.hussein.mazaady.presentation.adapter.TagCourseAdapter
import com.hussein.mazaady.ui.customview.CarouselTransformer

/** This is Home page but I make all page items like course recyclerviews,tags,active users in separate part to make it reusable*/
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvVideoCallProfiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideoCallProfiles.adapter = ActiveVideoCallProfileAdapter(requireContext(),ArrayList<String>(5))
        //init views like recyclerviews and textviews
        initViews()
        bindTagsTitle()
        return root
    }
    /**All these data are demo to show Design not related to specific class model **/
    private fun initViews()
    {
        //Active Users Profiles
        val mUsersList = ArrayList<String>()

        (1..10).forEach {
            mUsersList.add("User${it}")
        }
        binding.rvVideoCallProfiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideoCallProfiles.adapter = ActiveVideoCallProfileAdapter(requireContext(),mUsersList)

        //Courses Tags
        val mCoursesTagsList = ArrayList<String>()
        mCoursesTagsList.add("UI/UI")
        mCoursesTagsList.add("Illustration")
        mCoursesTagsList.add("3D Animation")
        mCoursesTagsList.add("Photoshop")
        mCoursesTagsList.add("Indesign")
        binding.rvCourseTags.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCourseTags.adapter = TagCourseAdapter(requireContext(),mCoursesTagsList)

        //Courses Tags
        val mCoursesList = ArrayList<String>()
        mCoursesList.add("UI/UI")
        mCoursesList.add("Illustration")
        mCoursesList.add("3D Animation")
        mCoursesList.add("Photoshop")
        mCoursesList.add("Indesign")
        binding.rvCourses.adapter = CourseAdapter(requireContext(),mCoursesList)
        // Disable clip padding to allow drawing outside the bounds
        binding.rvCourses.clipToPadding = false
        binding.rvCourses.clipChildren = false
        // Set offscreen page limit for smoother transitions
        binding.rvCourses.offscreenPageLimit = 1
        binding.rvCourses.setPageTransformer(CarouselTransformer())

        setupIndicators()
        setCurrentIndicator(0)

        binding.rvCourses.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        //set max height for carousel
        binding.rvCourses.layoutParams.height = getScreenHeight() / 2

    }
    private fun bindTagsTitle(){
        // Create a SpannableStringBuilder
        val builder = SpannableStringBuilder()
        // Bold text
        val boldText = "Upcoming\t"
        val spannableBold = SpannableString(boldText)
        spannableBold.setSpan(StyleSpan(Typeface.BOLD), 0, boldText.length, 0)
        // Append bold text to the builder
        builder.append(spannableBold)
        // Regular text
        val regularText = "\tcourse of this week"
        builder.append(regularText)
        // Set the styled text to the TextView
        binding.titleTags.text = builder
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(5)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive // Replace with your inactive indicator drawable
                    )
                )
                it.layoutParams = layoutParams
                binding.layoutIndicators.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    if (i == index) R.drawable.indicator_active else R.drawable.indicator_inactive
                )
            )
        }
    }
    private fun getScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}