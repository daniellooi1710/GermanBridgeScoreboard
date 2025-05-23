package com.germanbridgescoreboard.ui.bidoutcome

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.ui.theme.AppTheme
import com.germanbridgescoreboard.ui.theme.extendedColorScheme
import kotlinx.coroutines.launch

// Custom Number Picker
@Composable
fun CustomNumberPicker(
    range: IntRange,
    value: Int,
    enabled: Boolean = true,
    onValueChange: (Int) -> Unit
){
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(value) {
        val initialIndex = range.indexOf(value)
        listState.scrollToItem(initialIndex)
    }

    LaunchedEffect((listState.isScrollInProgress)) {
        if(!listState.isScrollInProgress){
            val center = listState.firstVisibleItemIndex +if (listState.firstVisibleItemScrollOffset > 24) 1 else 0

            val clampedIndex = center.coerceIn(0, range.last)
            onValueChange(range.elementAt(clampedIndex))
            coroutineScope.launch{
                listState.animateScrollToItem(clampedIndex)
            }
        }
    }

    Box(
        modifier = Modifier
            .height(44.dp)
            .width(60.dp)
            .alpha(if(enabled) 1f else 0.5f),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = enabled,
            modifier = Modifier
                .padding(top = 10.dp)
                .height(40.dp)
                .fillMaxWidth(),
        ){
            items(range.count()){ value ->
                Text(
                    text = value.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 2.dp)
                .fillMaxWidth()

        )

        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp)
                .fillMaxWidth()

        )
    }
}

// Holder for one row of player bids and wins
@Composable
fun PlayerBidsOutcomes(
    viewModel: MainViewModel,
    name: String,
    localBid: Int,
    localWin: Int,
    onBidChange: (Int) -> Unit,
    onWinChange: (Int) -> Unit
){
    val isBidding = viewModel.gameProcess == MainViewModel.GAMEPROCESS.BIDDING
    val isPlaying = viewModel.gameProcess == MainViewModel.GAMEPROCESS.PLAYING
    Row(
        modifier = Modifier.fillMaxWidth().height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(
            modifier = Modifier.width(32.dp)
        )

        Box(
            modifier = Modifier.weight(1f).height(44.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        CustomNumberPicker(
            value = localBid,
            range = 0..viewModel.currentRound,
            enabled = isBidding,
            onValueChange = onBidChange,
        )

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        CustomNumberPicker(
            value = localWin,
            range = 0..viewModel.currentRound,
            enabled = isPlaying,
            onValueChange = onWinChange,

        )
        Spacer(
            modifier = Modifier.width(32.dp)
        )
    }
}

@Composable
fun BidsOutcomesScreen(
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val gameProcess = viewModel.gameProcess
    val currentRound = viewModel.currentRound

    val localBids = rememberSaveable(
        saver = listSaver(
            save = { it.toList() }, // Convert SnapshotStateList to plain List<String> for saving
            restore = { it.toMutableStateList() } // Restore back to SnapshotStateList
        )
    ){
        mutableStateListOf<Int>().apply{
            viewModel.players.forEach{player ->
                add(player.bids.getOrNull(currentRound) ?: 0)
            }
        }
    }

    val localWins = rememberSaveable(
        saver = listSaver(
            save = { it.toList() }, // Convert SnapshotStateList to plain List<String> for saving
            restore = { it.toMutableStateList() } // Restore back to SnapshotStateList
        )
    ){
        mutableStateListOf<Int>().apply {
            viewModel.players.forEach { player ->
                add(player.wins.getOrNull(currentRound) ?: 0)
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
    ){
        stickyHeader {
            // Title Box
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(vertical = 8.dp)
            ) {
                // Left text - round and state
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ){
                    Text(
                        text = "Round $currentRound",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = when (gameProcess) {
                            MainViewModel.GAMEPROCESS.BIDDING -> {
                                "Bidding"
                            }
                            MainViewModel.GAMEPROCESS.PLAYING -> {
                                "Playing"
                            }
                            MainViewModel.GAMEPROCESS.ENDED -> {
                                "Game Ended"
                            }
                            else -> {
                                "Game Not Started"
                            }
                        },
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                // Right buttons
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    if(gameProcess == MainViewModel.GAMEPROCESS.BIDDING){
                        Button(onClick = {
                            if(localBids.sum() == viewModel.currentRound){
                                coroutineScope.launch{
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        message = "Total number of bids cannot be equal to the current round number.",
                                        actionLabel = "OK",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            else{
                                viewModel.saveBids(localBids)
                                viewModel.playing()
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.extendedColorScheme.orange.color,
                                contentColor = MaterialTheme.extendedColorScheme.orange.onColor
                            )) {
                            Text(text = "Next")
                        }
                    }
                    else if(gameProcess == MainViewModel.GAMEPROCESS.PLAYING){
                        Button(onClick = {
                            viewModel.bidding()
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.extendedColorScheme.blue.color,
                                contentColor = MaterialTheme.extendedColorScheme.blue.onColor
                            )) {
                            Text(text = "Back")
                        }
                        Button(onClick = {
                            if(localWins.sum() != viewModel.currentRound){
                                coroutineScope.launch{
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        message = "Total number of wins must be equal to the current round number.",
                                        actionLabel = "OK",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            else{
                                viewModel.saveWins(localWins)
                                viewModel.calcRoundScores()
                                if(currentRound == viewModel.rounds){
                                    viewModel.endGame()
                                }
                                else{
                                    viewModel.nextRound()
                                }
                                localBids.replaceAll { 0 }
                                localWins.replaceAll { 0 }
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.extendedColorScheme.green.color,
                                contentColor = MaterialTheme.extendedColorScheme.green.onColor
                            )) {
                            Text(text = "Next")
                        }
                    }
                }
            }

            // Table Header
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 8.dp, bottom = 8.dp)){
                Spacer(
                    modifier = Modifier.width(32.dp)
                )
                Text(
                    text = "Player",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                Text(
                    text = "Bids",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(60.dp)
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                Text(
                    text = "Wins",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(60.dp)
                )
                Spacer(
                    modifier = Modifier.width(32.dp)
                )
            }
        }
        items(viewModel.players.size){ i ->
            PlayerBidsOutcomes(
                viewModel,
                viewModel.players[i].name,
                localBids[i],
                localWins[i],
                onBidChange = { localBids[i] = it },
                onWinChange = { localWins[i] = it })
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun BidsOutcomesScreenPreview() {
    val viewModel = MainViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    AppTheme{
        BidsOutcomesScreen(viewModel, snackbarHostState)
    }
}