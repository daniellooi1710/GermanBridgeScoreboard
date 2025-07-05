package com.germanbridgescoreboard.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.germanbridgescoreboard.Destination
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.ui.bidoutcome.BidsOutcomesScreen
import com.germanbridgescoreboard.ui.gameinit.GameInitScreen
import com.germanbridgescoreboard.ui.home.HomeScreen
import com.germanbridgescoreboard.ui.scoreboard.ScoreboardScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
){
    NavHost(navController, startDestination = startDestination.route){
        composable(Destination.HOME.route){
            HomeScreen(viewModel = viewModel, navController = navController, snackbarHostState = snackbarHostState, modifier = modifier)
        }
        composable(Destination.BIDS.route){
            BidsOutcomesScreen(viewModel = viewModel, snackbarHostState = snackbarHostState, modifier = modifier)
        }
        composable(Destination.SCOREBOARD.route){
            ScoreboardScreen(viewModel = viewModel, modifier = modifier)
        }
        composable("init/{numPlayers}"){ backStackEntry ->
            val count = backStackEntry.arguments?.getString("numPlayers")?.toIntOrNull() ?: 2
            GameInitScreen(viewModel = viewModel, numPlayers = count, navController = navController, snackbarHostState = snackbarHostState, modifier = modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBarMain(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    val bottomNavItems = listOf(
        Destination.HOME,
        Destination.BIDS,
        Destination.SCOREBOARD
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val snackbarHostState = remember { SnackbarHostState() }

    val title = when (currentRoute) {
        Destination.HOME.route -> "German Bridge Score Counter"
        Destination.BIDS.route -> "Bids & Outcomes"
        Destination.SCOREBOARD.route -> "Scoreboard"
        else -> "German Bridge Score Counter"
    }

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentWindowInsets = WindowInsets.systemBars.union(WindowInsets.ime),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                bottomNavItems.forEach{ destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route){
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                restoreState = true
                            }},
                        label = { Text(text = destination.label) },
                        icon = { Icon(imageVector = destination.icon, contentDescription = null) }
                    )
                }
            }
        }
    ){
        contentPadding ->
        AppNavHost(
            navController,
            startDestination,
            viewModel,
            snackbarHostState,
            Modifier.padding(contentPadding))
    }
}