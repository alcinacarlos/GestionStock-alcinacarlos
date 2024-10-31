package com.carlosalcina

import com.carlosalcina.io.GestionStock
import com.carlosalcina.repository.ProductoRepository
import com.carlosalcina.repository.ProveedorRepository
import com.carlosalcina.repository.UsuarioRepository
import com.carlosalcina.service.LoginManager
import com.carlosalcina.service.ProductManager
import com.carlosalcina.service.ProveedorManager

fun main() {
    val productoRepository = ProductoRepository()
    val proveedorRepository = ProveedorRepository()
    val usuarioRepository = UsuarioRepository()

    val productManager = ProductManager(productoRepository)
    val proveedorManager = ProveedorManager(proveedorRepository)
    val loginManager = LoginManager(usuarioRepository)

    val gestionStock = GestionStock(loginManager, productManager, proveedorManager)

    gestionStock.menu()


}