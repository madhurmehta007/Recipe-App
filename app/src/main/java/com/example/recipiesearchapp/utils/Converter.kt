package com.example.recipiesearchapp.utils

import androidx.room.TypeConverter
import com.example.recipiesearchapp.models.Equipment
import com.example.recipiesearchapp.models.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromEquipmentList(value: ArrayList<Equipment>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Equipment>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toEquipmentList(value: String): ArrayList<Equipment> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Equipment>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromIngredientList(value: ArrayList<Ingredient>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Ingredient>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIngredientList(value: String): ArrayList<Ingredient> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Ingredient>>() {}.type
        return gson.fromJson(value, type)
    }

}
