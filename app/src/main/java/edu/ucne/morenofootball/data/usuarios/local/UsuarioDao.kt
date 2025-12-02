package edu.ucne.morenofootball.data.usuarios.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UsuarioDao {
    @Upsert
    suspend fun save(user: UsuarioEntity)

    @Query("delete from usuarios")
    suspend fun delete()

    @Query("select * from usuarios limit 1")
    suspend fun getUser(): UsuarioEntity?
}