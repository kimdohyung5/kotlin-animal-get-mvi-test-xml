package com.kimdo.animalmvitest.api

import com.kimdo.animalmvitest.model.Animal
import retrofit2.http.GET

interface AnimalApi {

    @GET("animals.json")
    suspend fun getAnimals(): List<Animal>
}