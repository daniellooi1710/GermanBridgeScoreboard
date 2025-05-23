package com.germanbridgescoreboard.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.R
import com.germanbridgescoreboard.ui.theme.extendedColorScheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val gameProcess = viewModel.gameProcess
    val numberOfPlayers by viewModel.numPlayers
    val coroutineScope = rememberCoroutineScope()
    var openAlertDialog by remember { mutableStateOf(false)}

    var isInit = gameProcess == MainViewModel.GAMEPROCESS.INIT
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = when (gameProcess) {
                MainViewModel.GAMEPROCESS.INIT -> stringResource(R.string.init_game)
                MainViewModel.GAMEPROCESS.BIDDING -> stringResource(R.string.game_ongoing)
                MainViewModel.GAMEPROCESS.PLAYING -> stringResource(R.string.game_ongoing)
                MainViewModel.GAMEPROCESS.ENDED -> stringResource(R.string.game_ended)
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = when (gameProcess) {
                MainViewModel.GAMEPROCESS.INIT -> MaterialTheme.extendedColorScheme.yellow.onColorContainer
                MainViewModel.GAMEPROCESS.BIDDING -> MaterialTheme.extendedColorScheme.green.onColorContainer
                MainViewModel.GAMEPROCESS.PLAYING -> MaterialTheme.extendedColorScheme.green.onColorContainer
                MainViewModel.GAMEPROCESS.ENDED -> MaterialTheme.extendedColorScheme.purple.onColorContainer
            },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(when (gameProcess) {
                    MainViewModel.GAMEPROCESS.INIT -> MaterialTheme.extendedColorScheme.yellow.colorContainer
                    MainViewModel.GAMEPROCESS.BIDDING -> MaterialTheme.extendedColorScheme.green.colorContainer
                    MainViewModel.GAMEPROCESS.PLAYING -> MaterialTheme.extendedColorScheme.green.colorContainer
                    MainViewModel.GAMEPROCESS.ENDED -> MaterialTheme.extendedColorScheme.purple.colorContainer
                })
                .padding(vertical = 10.dp, horizontal = 36.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(R.string.number_of_players),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically){
            FilledTonalButton(
                onClick = {
                    if(numberOfPlayers == 2){
                        coroutineScope.launch{
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = "The minimum number of players is 2.",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    else{
                        viewModel.decreasePlayers()
                    }
                },
                shape = CircleShape,
                enabled = gameProcess == MainViewModel.GAMEPROCESS.INIT
            ){
                Text(text = "-",
                    fontSize = 36.sp
                )
            }

            Text(
                text = numberOfPlayers.toString(),
                fontSize = 120.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(180.dp)
                    .padding(horizontal = 12.dp)
            )

            FilledTonalButton(
                onClick = {
                    if(numberOfPlayers == 12){
                        coroutineScope.launch{
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = "The maximum number of players is 12.",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    else{
                        viewModel.increasePlayers()
                    }
                },
                shape = CircleShape,
                enabled = gameProcess == MainViewModel.GAMEPROCESS.INIT
            ){
                Text(
                    text = "+",
                    fontSize = 36.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("init/${numberOfPlayers}"){
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                }
            },
            modifier = Modifier
                .width(120.dp),
            enabled = isInit,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.extendedColorScheme.green.color,
                contentColor = MaterialTheme.extendedColorScheme.green.onColor
            )
        ){
            Text(text = stringResource(R.string.start_game))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                openAlertDialog = true
            },
            modifier = Modifier
                .width(120.dp),
            enabled = !isInit,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.extendedColorScheme.red.color,
                contentColor = MaterialTheme.extendedColorScheme.red.onColor
                )
        ){
            Text(text = stringResource(R.string.new_game))
        }

        if(openAlertDialog) {
            AlertDialog(
                title = {
                    Text(
                        text = "Start a new game?"
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to start a new game? This will reset all the players and the scores."
                    )
                },
                onDismissRequest = {
                    openAlertDialog = false
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.newGame()
                        openAlertDialog = false
                    }) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        openAlertDialog = false
                    }) {
                        Text(text = "No")
                    }
                }
            )
        }

    }
}