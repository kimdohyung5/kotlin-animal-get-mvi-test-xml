package com.kimdo.animalmvitest.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kimdo.animalmvitest.api.AnimalApi
import com.kimdo.animalmvitest.api.AnimalRepo

class ViewModelFactory(private val api: AnimalApi): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(AnimalRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}