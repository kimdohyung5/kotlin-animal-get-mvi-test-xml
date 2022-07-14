package com.kimdo.animalmvitest.view

import com.kimdo.animalmvitest.model.Animal


// MainState로 값을 처리한다... Animals, Error, Loading, Idle
sealed class MainState {

    object Idle: MainState()
    object Loading: MainState()
    data class Animals(val animals: List<Animal>): MainState()
    data class Error(val error: String?): MainState()

}