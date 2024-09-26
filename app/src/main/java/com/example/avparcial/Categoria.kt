package com.example.avparcial

data class Categoria(val icone: Int, val elemento: String) {
    override fun toString(): String {
        return elemento
    }
}
