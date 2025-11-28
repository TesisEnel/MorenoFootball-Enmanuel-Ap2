package edu.ucne.morenofootball.data.productos

import edu.ucne.morenofootball.data.productos.remote.ProductoRemoteDataSource
import javax.inject.Inject

class ProductoRepositoryImpl @Inject constructor(
    private val remote: ProductoRemoteDataSource
) {
    // TODO("Not implemented yet.")
}