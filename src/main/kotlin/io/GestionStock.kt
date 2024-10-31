package com.carlosalcina.io

import com.carlosalcina.model.Proveedor
import com.carlosalcina.service.LoginManager
import com.carlosalcina.service.ProductManager
import com.carlosalcina.service.ProveedorManager

class GestionStock(
    private val loginManager: LoginManager,
    private val productManager: ProductManager,
    private val proveedorManager: ProveedorManager,
    private var logged: Boolean = false
) {
    fun login() {
        println("Bienvenido a Gestion de Stock")
        print("\tIntroduce tu usuario: ")
        val usuario = readln()
        print("\tIntroduce tu contraseña: ")
        val password = readln()

        val usuarioLogged = loginManager.login(usuario, password)
        if (usuarioLogged != null) {
            logged = true
            println("Bienvenido $usuarioLogged, has inciado sesión correctamente")
        } else {
            println("Usuario o contraseña incorrectos, inténtelo de nuevo")
        }

    }

    private fun clearTerminal() {
        repeat(20) {
            println("\n")
        }
    }

    private fun mostrarMenu() {
        clearTerminal()
        println("=====================================")
        println("           MENÚ DE OPCIONES          ")
        println("=====================================")
        println("1. Alta de Producto")
        println("   - Registrar un nuevo producto con sus detalles y proveedor.")
        println("-------------------------------------")
        println("2. Baja de Producto")
        println("   - Eliminar un producto dado su ID.")
        println("-------------------------------------")
        println("3. Modificar Nombre de Producto")
        println("   - Cambiar el nombre de un producto existente.")
        println("-------------------------------------")
        println("4. Modificar Stock de Producto")
        println("   - Actualizar la cantidad de stock de un producto.")
        println("-------------------------------------")
        println("5. Obtener Producto")
        println("   - Consultar los detalles de un producto por su ID.")
        println("-------------------------------------")
        println("6. Obtener Productos con Stock Disponible")
        println("   - Listar todos los productos con stock mayor a cero.")
        println("-------------------------------------")
        println("7. Obtener Productos Sin Stock")
        println("   - Listar todos los productos sin stock disponible.")
        println("-------------------------------------")
        println("8. Obtener Proveedor de un Producto")
        println("   - Consultar el proveedor asociado a un producto.")
        println("-------------------------------------")
        println("9. Obtener Todos los Proveedores")
        println("   - Listar todos los proveedores registrados en el sistema.")
        println("=====================================")
        println("10. Salir")
        println("Seleccione una opción: ")
    }


    fun menu() {
        while (!logged){
            login()
        }

        var menu = true
        while (menu){
            mostrarMenu()
            when (readln().toIntOrNull()) {
                1 -> altaProducto()
                2 -> bajaProducto()
                3 -> modificarNombreProducto()
                4 -> modificarStockProducto()
                5 -> obtenerProducto()
                6 -> obtenerProductosConStock()
                7 -> obtenerProductosSinStock()
                8 -> obtenerProveedorProducto()
                9 -> obtenerProveedores()
                10 -> menu = false
                else -> println("Opcion no válida, vuelva a intentarlo.")
            }
            println("\nEjercicio mostrado, espera 5 segundos")
            Thread.sleep(5000)
        }
        println("Hasta pronto!!")
    }

    private fun createProveedor(): Proveedor {
        print("\tNombre del Proveedor: ")
        val nombreProveedor = readln()
        print("\tDirección del Proveedor: ")
        val direccionProveedor = readln()
        return Proveedor(nombreProveedor, direccionProveedor)
    }

    private fun altaProducto() {
        println("Creando producto:")
        print("\tCategoría del producto: ")
        val categoria = readln()
        print("\tNombre del Producto: ")
        val nombreProducto = readln()
        print("\tDescripción del Producto: ")
        val descripcionProducto = readln()
        print("\tPrecio sin IVA: ")
        val precioSinIva = readln().toFloatOrNull() ?: 0.0f
        print("\tStock: ")
        val stock = readln().toIntOrNull() ?: 0

        print("\tExiste ya el proveedor? (y/n)")
        val existe = readln().lowercase()
        var proveedor: Proveedor?
        if (existe == "y") {
            print("\t\tDime el nombre del proveedor")
            val nombreP = readln()
            proveedor = proveedorManager.getTodosProveedores().find { it.nombre == nombreP }
            if (proveedor == null) {
                println("\t\tProveedor no encontrado, creandolo manualmente:")
                proveedor = createProveedor()
            }
        } else {
            proveedor = createProveedor()
        }
        val resultado =
            productManager.altaProducto(categoria, nombreProducto, descripcionProducto, precioSinIva, stock, proveedor)
        if (resultado) println("Producto insertado con éxito") else println("Error al insertar producto, vuelve a intentarlo")
    }

    private fun bajaProducto() {
        println("Introduce la ID del producto a dar de baja")
        val id = readln()
        val resultado = productManager.bajaProducto(id)
        if (resultado) println("Producto dado de baja con éxito") else println("Error dando de baja al producto")
    }

    private fun modificarNombreProducto() {
        println("Introduce el ID del producto a cambiar de nombre: ")
        val id = readln()
        println("Introduce el nuevo nombre para el producto: ")
        val nuevoNombre = readln()
        val resultado = productManager.modificarNombreProducto(id, nuevoNombre)
        if (resultado) println("Nombre del producto modificado con éxito") else println("Error al cambiar de nombre al producto")
    }

    private fun modificarStockProducto() {
        println("Introduce el ID del producto a cambiar de nombre: ")
        val id = readln()
        println("Introduce el nuevo stock para el producto: ")
        val stock = readln().toIntOrNull() ?: 0
        val resultado = productManager.modificarStockProducto(id, stock)
        if (resultado) println("Stock del producto modificado con éxito") else println("Error al cambiar el stock del producto")
    }

    private fun obtenerProducto() {
        println("Introduce el ID del producto a mostrar: ")
        val id = readln()
        val producto = productManager.getProducto(id)
        if (producto != null) {
            println(producto)
        } else {
            println("Producto no encontrado")
        }
    }

    private fun obtenerProductosConStock() {
        val productos = productManager.getProductosConStock()
        productos.forEach { println(it) }
    }

    private fun obtenerProductosSinStock() {
        val productos = productManager.getProductosSinStock()
        productos.forEach { println(it) }
    }

    private fun obtenerProveedorProducto(){
        println("Introduce la ID del producto: ")
        val idProducto = readln()
        val producto = productManager.getProducto(idProducto)
        if (producto != null){
            println(proveedorManager.getProveedorByID(producto.proveedor.id!!))
        }else{
            println("Producto o proveedor no encontrado")
        }
    }

    private fun obtenerProveedores(){
        val proveedores = proveedorManager.getTodosProveedores()
        proveedores.forEach { println(it) }
    }

}