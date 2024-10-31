package com.carlosalcina.service

import com.carlosalcina.model.Usuario
import com.carlosalcina.repository.UsuarioRepository

class LoginManager(private val usuarioRepository: UsuarioRepository) {

    fun login(userInput: String, passInput: String): Usuario? {
        val usuario = usuarioRepository.getUsuarioById(userInput)
        return if (usuario != null && usuario.password == passInput) {
            usuario
        } else {
            null
        }
    }

}