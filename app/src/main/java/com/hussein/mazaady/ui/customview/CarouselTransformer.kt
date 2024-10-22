package com.hussein.mazaady.ui.customview

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CarouselTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val absPosition = Math.abs(position)

        // Scale the pages
        page.scaleX = if (absPosition > 1) 0.85f else 1 - (0.15f * absPosition)
        page.scaleY = if (absPosition > 1) 0.85f else 1 - (0.15f * absPosition)

        // Fade the pages
        page.alpha = if (absPosition > 1) 0.5f else 1 - (0.5f * absPosition)

        // You can add more transformations here (e.g., translation)
        // to create different carousel effects.
    }
}