package com.carlosalcina.repository

import com.carlosalcina.utils.HibernateUtils
import com.carlosalcina.model.Usuario
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction

class UsuarioRepository {

    private fun getEntityManager(): EntityManager {
        return HibernateUtils.getEntityManager()
    }

    fun insertUsuario(usuario: Usuario) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            transaction.begin()
            em.persist(usuario)
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

    fun getAllUsuarios(): List<Usuario> {
        val em = getEntityManager()
        return try {
            em.createQuery("FROM Usuario", Usuario::class.java).resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun updateUsuario(usuario: Usuario) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            transaction.begin()
            em.merge(usuario)
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

    fun getUsuarioById(nombreUsuario: String): Usuario? {
        val em = getEntityManager()
        return try {
            em.find(Usuario::class.java, nombreUsuario)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun deleteUsuario(nombreUsuario: String) {
        val em = getEntityManager()
        val transaction: EntityTransaction = em.transaction
        try {
            val usuario = getUsuarioById(nombreUsuario)
            if (usuario != null) {
                transaction.begin()
                em.remove(if (em.contains(usuario)) usuario else em.merge(usuario)) //sacado de https://stackoverflow.com/questions/17027398/java-lang-illegalargumentexception-removing-a-detached-instance-com-test-user5
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
