package com.carlosalcina.repository

import com.carlosalcina.ejercicioinstitutos.utils.HibernateUtils
import com.carlosalcina.model.Producto
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction

class ProductoRepository {

    companion object{
        const val PERSITENCENAME = "gestionEmpresa"
    }

    private fun getEntityManager(): EntityManager {
        return HibernateUtils.getEntityManager(PERSITENCENAME)
    }

    fun insertProducto(producto: Producto) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            transaction.begin()
            em.persist(producto)
            transaction.commit()
        } catch (e: Exception) {
            if (transaction.isActive) {
                transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }

    fun getAllProductos(): List<Producto> {
        val em = getEntityManager()
        return try {
            em.createQuery("FROM Producto", Producto::class.java).resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun updateProducto(producto: Producto) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            transaction.begin()
            em.merge(producto)
            transaction.commit()
        } catch (e: Exception) {
            if (transaction.isActive) {
                transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }

    fun getProductoById(id: String): Producto? {
        val em = getEntityManager()
        return try {
            em.find(Producto::class.java, id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun deleteProducto(id: String) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            val producto = getProductoById(id)
            if (producto != null) {
                transaction.begin()
                em.remove(producto)
                transaction.commit()
            }
        } catch (e: Exception) {
            if (transaction.isActive) {
                transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }
}
