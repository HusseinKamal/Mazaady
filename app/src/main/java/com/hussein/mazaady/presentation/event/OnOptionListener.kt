package com.hussein.mazaady.presentation.event

import com.hussein.mazaady.domain.option.OptionX

interface OnOptionListener {
    fun onOptionClicked(option: OptionX)

}