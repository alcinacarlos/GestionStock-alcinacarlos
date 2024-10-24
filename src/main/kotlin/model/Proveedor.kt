package com.carlosalcina.model

import jakarta.persistence.*

@Entity
@Table
class Proveedor(
    @Column
    val nombre:String,

    @Column
    val direccion:String,

    @OneToMany(mappedBy = "proveedor", cascade = [CascadeType.ALL])
    val productos: MutableList<Producto> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
) {
    fun addProduct(producto: Producto){
        productos.add(producto)
    }
    fun removeProduct(producto: Producto){
        productos.remove(producto)
    }
}