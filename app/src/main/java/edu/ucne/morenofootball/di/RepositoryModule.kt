package edu.ucne.morenofootball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.data.carritos.CarritoRepositoryImpl
import edu.ucne.morenofootball.data.carritos.remote.CarritoRemoteDataSource
import edu.ucne.morenofootball.data.productos.ProductoRepositoryImpl
import edu.ucne.morenofootball.data.productos.remote.ProductoRemoteDataSource
import edu.ucne.morenofootball.data.tarjetas.TarjetaRepositorImpl
import edu.ucne.morenofootball.data.tarjetas.remote.TarjetaRemoteDataSource
import edu.ucne.morenofootball.data.usuarios.UsuarioRepositoryImpl
import edu.ucne.morenofootball.data.usuarios.local.UsuarioDao
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource
import edu.ucne.morenofootball.domain.carritos.CarritoRepository
import edu.ucne.morenofootball.domain.productos.ProductoRepository
import edu.ucne.morenofootball.domain.tarjetas.TarjetaRepository
import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    
    @Provides
    @Singleton
    fun providesUsuarioRepository(remote: UsuarioRemoteDataSource, local: UsuarioDao): UsuarioRepository =
        UsuarioRepositoryImpl(remote, local)

    @Provides
    @Singleton
    fun providesProductoRepository(remote: ProductoRemoteDataSource): ProductoRepository =
        ProductoRepositoryImpl(remote)

    @Provides
    @Singleton
    fun provudesTarjetaRepository(remote: TarjetaRemoteDataSource): TarjetaRepository =
        TarjetaRepositorImpl(remote)

    @Provides
    @Singleton
    fun provudesCarritoRepository(remote: CarritoRemoteDataSource): CarritoRepository =
        CarritoRepositoryImpl(remote)
}