package edu.ucne.morenofootball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.data.entities.usuarios.UsuarioRepositoryImpl
import edu.ucne.morenofootball.data.entities.usuarios.local.UsuarioDao
import edu.ucne.morenofootball.data.entities.usuarios.remote.UsuarioRemoteDataSource
import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    
    @Provides
    @Singleton
    fun providesUsuarioRepository(remote: UsuarioRemoteDataSource, local: UsuarioDao): UsuarioRepository =
        UsuarioRepositoryImpl(remote, local)
}