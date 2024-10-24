package com.carlosalcina.model

import jakarta.persistence.*

@Entity
@Table
data class Usuario(
    @Id
    val nombreUsuario: String,

    @Column(nullable = false, length = 20)
    val password:String
) {
}