package com.germanbridgescoreboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.PlayCircleOutline
import androidx.compose.material.icons.twotone.TableChart
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import com.germanbridgescoreboard.ui.theme.AppTheme
import com.germanbridgescoreboard.ui.utils.AppNavHost
import com.germanbridgescoreboard.ui.utils.NavigationBarMain

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("home", "Home", Icons.TwoTone.Home, "German Bridge Score Counter"),
    BIDS("bids", "Bids", Icons.TwoTone.PlayCircleOutline, "Bids/Outcomes"),
    SCOREBOARD("scoreboard", "Scoreboard", Icons.TwoTone.TableChart, "Scoreboard"),
}

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("germanbridgescoreboard", MODE_PRIVATE)
        val db = DatabaseProvider.getDatabase(this)

        if(mainViewModel.gameProcess == MainViewModel.GAMEPROCESS.INIT) mainViewModel.restoreGameFromDb(sharedPref, db)
        setContent{
            AppTheme{
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                AppNavHost(navController = navController, startDestination = Destination.HOME, snackbarHostState = snackbarHostState, viewModel = mainViewModel)
                NavigationBarMain(mainViewModel)
            }
        }
    }

    override fun onStop(){
        super.onStop()
        val sharedPref = getSharedPreferences("germanbridgescoreboard", MODE_PRIVATE)
        val db = DatabaseProvider.getDatabase(this)
        mainViewModel.saveGameToDb(sharedPref, db)
    }

}