package edu.ucne.morenofootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.morenofootball.ui.presentation.navigation.MorenoFootBallNavHost
import edu.ucne.morenofootball.ui.theme.MorenoFootballTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MorenoFootballTheme {
                MorenoFootBallNavHost()
            }
        }
    }
}