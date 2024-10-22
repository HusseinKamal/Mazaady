package com.hussein.mazaady.presentation.event

import com.hussein.mazaady.domain.properity.Properity

interface OnProperityListener {
    fun onProperityClicked(properity: Properity)

}