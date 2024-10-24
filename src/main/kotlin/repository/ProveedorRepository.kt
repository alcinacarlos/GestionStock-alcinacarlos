package com.carlosalcina.repository

import com.carlosalcina.ejercicioinstitutos.utils.HibernateUtils
import com.carlosalcina.model.Proveedor
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction

class ProveedorRepository {

    companion object{
        const val PERSITENCENAME = "gestionEmpresa"
    }

    private fun getEntityManager(): EntityManager {
        return HibernateUtils.getEntityManager(PERSITENCENAME)
    }

    fun insertProveedor(proveedor: Proveedor) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            transaction.begin()
            em.persist(proveedor)
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

    fun getAllProveedores(): List<Proveedor> {
        val em = getEntityManager()
        return try {
            em.createQuery("FROM Proveedor", Proveedor::class.java).resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun updateProveedor(proveedor: Proveedor) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            transaction.begin()
            em.merge(proveedor)
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

    fun getProveedorById(id: Long): Proveedor? {
        val em = getEntityManager()
        return try {
            em.find(Proveedor::class.java, id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun deleteProveedor(id: Long) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            val proveedor = getProveedorById(id)
            if (proveedor != null) {
                transaction.begin()
                em.remove(proveedor)
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