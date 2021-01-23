package com.example.crudkoi

data class User (
    val id : String,
    val nama : String,
    val alamat : String
){
    constructor(): this("","",""){

    }
}