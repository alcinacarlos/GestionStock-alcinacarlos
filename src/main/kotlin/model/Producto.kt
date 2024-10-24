package com.carlosalcina.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table
data class Producto(

    @Column
    val categoría:String,

    @Column
    val nombre:String,

    @Column
    val descripcion:String,

    @Column
    val precio_sin_iva: Float,

    @Column
    val stock:Int,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "proveedor_id")
    val proveedor: Proveedor,

    @Column
    val precio_con_iva: Float = (precio_sin_iva * 1.21).toFloat(),

    @Column
    val fecha_alta: Date = Date(),

    @Id
    val id: String = categoría.take(3) + nombre.take(3) + proveedor.nombre.take(3) //se forma con cat+nombre+proveedor las 3 primeras letras

) {
}