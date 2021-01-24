package com.example.crudkoi

data class Ikankoi (
    val id : String,
    val nama : String,
    val jumlah : Int
){
      constructor(): this("","",0){
    }
}