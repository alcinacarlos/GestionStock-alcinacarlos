package com.carlosalcina.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table
data class Producto(

    @Column(nullable = false, length = 10)
    val categoria:String,

    @Column(length = 50, nullable = false)
    val nombre:String,

    @Column
    val descripcion:String,

    @Column(nullable = false)
    val precio_sin_iva: Float,

    @Column
    val stock:Int,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "proveedor_id")
    val proveedor: Proveedor,

    @Column(nullable = false)
    val precio_con_iva: Float = (precio_sin_iva * 1.21).toFloat(),

    @Column
    val fecha_alta: Date = Date(),

    @Id
    val id: String = categoria.take(3) + nombre.take(3) + proveedor.nombre.take(3) //se forma con cat+nombre+proveedor las 3 primeras letras

) {
    override fun toString(): String {
        return "\nNombre: $nombre\nCategoría: $categoria\nDescripción: $descripcion\nPrecio sin IVA: $precio_sin_iva\nPrecio con IVA: $precio_con_iva\nStock: $stock\nProveedor: ${proveedor.nombre}\nFecha de alta: $fecha_alta\nID: $id"
    }
}