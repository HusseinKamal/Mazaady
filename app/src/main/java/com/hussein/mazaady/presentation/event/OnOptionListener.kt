package com.hussein.mazaady.presentation.event

import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity

interface OnOptionListener {
    fun onOptionClicked(property: Properity, option: Option)
    fun onOptionClicked(property: Properity,option: OptionX)

}