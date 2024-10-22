package com.hussein.mazaady.presentation.event

import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.category.CategoryX
import com.hussein.mazaady.domain.category.Children

interface OnSubCategoryListener {
    fun onSubCategoryClicked(child: Children)

}