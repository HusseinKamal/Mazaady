package com.hussein.mazaady.presentation.event

import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.category.CategoryX

interface OnCategoryListener {
    fun onCategoryClicked(category: CategoryX)

}