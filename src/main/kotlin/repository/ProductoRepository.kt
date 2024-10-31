package com.carlosalcina.repository

import com.carlosalcina.model.Producto
import com.carlosalcina.utils.HibernateUtils
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction


class ProductoRepository {

    private fun getEntityManager(): EntityManager {
        return HibernateUtils.getEntityManager()
    }

    fun insertProducto(producto: Producto):Boolean {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        var done = false
        try {
            transaction.begin()
            em.persist(producto)
            transaction.commit()
            done = true
        } catch (e: Exception) {
            if (transaction.isActive) {
                transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
        return done
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

    fun updateProducto(producto: Producto):Boolean {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        var done = false
        try {
            transaction.begin()
            em.merge(producto)
            transaction.commit()
            done = true
        } catch (e: Exception) {
            if (transaction.isActive) {
                transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
        return done
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

    fun deleteProducto(id: String):Boolean {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        var done = false
        try {
            val producto = getProductoById(id)
            if (producto != null) {
                transaction.begin()
                em.remove(if (em.contains(producto)) producto else em.merge(producto)) //sacado de https://stackoverflow.com/questions/17027398/java-lang-illegalargumentexception-removing-a-detached-instance-com-test-user5
                transaction.commit()
                done = true
            }
        } catch (e: Exception) {
            if (transaction.isActive) {
                transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
        return done
    }
}
