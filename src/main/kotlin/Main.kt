package com.carlosalcina

import com.carlosalcina.model.Producto
import com.carlosalcina.model.Proveedor
import com.carlosalcina.repository.ProductoRepository
import com.carlosalcina.repository.ProveedorRepository
import com.carlosalcina.repository.UsuarioRepository

fun main() {
    val productoRepository = ProductoRepository()
    val proveedorRepository = ProveedorRepository()
    val usuarioRepository = UsuarioRepository()

    val proveedor = Proveedor("Lucas", "amiel")
    val producto1 = Producto("+18", "hola", "despcras", 33f, 44, proveedor)
    proveedor.addProduct(producto1)


    productoRepository.insertProducto(producto1)
    //proveedorRepository.insertProveedor(proveedor)


}