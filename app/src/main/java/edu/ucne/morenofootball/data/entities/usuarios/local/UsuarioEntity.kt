package edu.ucne.morenofootball.data.entities.usuarios.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val usuarioId: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val fechaRegistro: String = LocalDateTime.now().toString()
)