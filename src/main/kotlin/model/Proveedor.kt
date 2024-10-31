package com.carlosalcina.model

import jakarta.persistence.*

@Entity
@Table
data class Proveedor(
    @Column(unique = true, length = 50, nullable = false)
    val nombre:String,

    @Column(nullable = false)
    val direccion:String,

    @OneToMany(mappedBy = "proveedor", cascade = [CascadeType.ALL])
    val productos: MutableList<Producto> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
) {
    override fun toString(): String {
        return "\nNombre: $nombre\nDirección: $direccion\nID: $id"
    }
}