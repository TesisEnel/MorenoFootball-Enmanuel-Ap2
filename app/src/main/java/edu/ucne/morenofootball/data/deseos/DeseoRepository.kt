package edu.ucne.morenofootball.data.deseos

import edu.ucne.morenofootball.data.deseos.remote.DeseoRemoteDataSource
import javax.inject.Inject

class DeseoRepository @Inject constructor(
    private val remote: DeseoRemoteDataSource
) {
}