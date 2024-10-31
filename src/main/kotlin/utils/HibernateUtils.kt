package com.carlosalcina.utils

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object HibernateUtils {

    private const val PERSISTENCENAME = "gestionEmpresa"

    private lateinit var emf: EntityManagerFactory

    private fun getEntityManagerFactory(namePersistenceUnit: String = PERSISTENCENAME): EntityManagerFactory {
        return if(this::emf.isInitialized && emf.isOpen) {
            emf
        } else {
            Persistence.createEntityManagerFactory(namePersistenceUnit)
        }
    }

    fun getEntityManager(namePersistenceUnit: String = PERSISTENCENAME): EntityManager {
        return getEntityManagerFactory(namePersistenceUnit).createEntityManager()
    }


    fun shutdown() {
        if (emf.isOpen) {
            emf.close()
        }
    }

    fun closeEntityManager(em: EntityManager?) {
        try {
            if (em != null && em.isOpen) {
                em.close()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }



}