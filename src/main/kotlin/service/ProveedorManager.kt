package com.carlosalcina.service

import com.carlosalcina.model.Proveedor
import com.carlosalcina.repository.ProveedorRepository

class ProveedorManager(private val proveedorRepository: ProveedorRepository) {
    fun getProveedorProducto(idProducto:String):Proveedor?{
        val proveedores = getTodosProveedores()
        var proveedorEncontrado:Proveedor? = null
        proveedores.forEach { proveedor ->
            val producto = proveedor.productos.find { it.id == idProducto }
            if (producto != null) proveedorEncontrado = proveedor
        }
        return proveedorEncontrado
    }

    fun getTodosProveedores():List<Proveedor>{
        return proveedorRepository.getAllProveedores()
    }
    fun getProveedorByID(id:Long):Proveedor?{
        return proveedorRepository.getProveedorById(id)
    }
}