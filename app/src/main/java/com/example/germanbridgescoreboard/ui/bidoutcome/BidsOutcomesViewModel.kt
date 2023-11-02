package com.example.germanbridgescoreboard.ui.bidoutcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BidsOutcomesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bids Fragment"
    }
    val text: LiveData<String> = _text
}