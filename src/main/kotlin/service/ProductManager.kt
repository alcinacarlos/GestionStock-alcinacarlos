package com.carlosalcina.service

import com.carlosalcina.model.Producto
import com.carlosalcina.model.Proveedor
import com.carlosalcina.repository.ProductoRepository

class ProductManager(private val productoRepository: ProductoRepository) {
    fun altaProducto(
        categoria: String,
        nombreProducto: String,
        descripcion: String,
        precioSinIva: Float,
        stock: Int,
        proveedor: Proveedor
    ): Boolean {
        val producto = Producto(categoria, nombreProducto, descripcion, precioSinIva, stock, proveedor)
        return productoRepository.insertProducto(producto)
    }

    fun bajaProducto(idProducto: String): Boolean {
        return productoRepository.deleteProducto(idProducto)
    }

    fun modificarNombreProducto(idProducto: String, nuevoNombre: String): Boolean {
        val productoOld = productoRepository.getProductoById(idProducto) ?: return false
        val productoNuevo = Producto(
            productoOld.categoría,
            nuevoNombre,
            productoOld.descripcion,
            productoOld.precio_sin_iva,
            productoOld.stock,
            productoOld.proveedor,
            productoOld.precio_con_iva,
            productoOld.fecha_alta,
            productoOld.id
        )
        return productoRepository.updateProducto(productoNuevo)
    }

    fun modificarStockProducto(idProducto: String, nuevoStock: Int): Boolean {
        val productoOld = productoRepository.getProductoById(idProducto) ?: return false
        val productoNuevo = Producto(
            productoOld.categoría,
            productoOld.nombre,
            productoOld.descripcion,
            productoOld.precio_sin_iva,
            nuevoStock,
            productoOld.proveedor,
            productoOld.precio_con_iva,
            productoOld.fecha_alta,
            productoOld.id
        )
        return productoRepository.updateProducto(productoNuevo)
    }

    fun getProducto(idProducto: String):Producto?{
        return productoRepository.getProductoById(idProducto)
    }

    fun getProductosConStock():List<Producto>{
        val productos = productoRepository.getAllProductos()
        return productos.filter { it.stock > 0 }
    }

    fun getProductosSinStock():List<Producto>{
        val productos = productoRepository.getAllProductos()
        return productos.filter { it.stock == 0 }
    }

}