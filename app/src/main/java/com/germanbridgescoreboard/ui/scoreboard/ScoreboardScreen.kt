package com.germanbridgescoreboard.ui.scoreboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.R
import com.germanbridgescoreboard.ui.theme.extendedColorScheme

@Composable
fun ScoreTable(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
){
    val horizontalScroll = rememberScrollState()
    val verticalScroll = rememberScrollState()

    Box(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .horizontalScroll(horizontalScroll)
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Column{
            Row(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)){
                Text(
                    "Round",
                    modifier = Modifier
                        .width(80.dp)
                        .padding(6.dp),
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
                viewModel.players.forEach{ player ->
                    Text(
                        text = player.name,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            for(i in 0 until viewModel.rounds){
                Row{
                    Text(
                        text = (i + 1).toString(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .width(80.dp)
                            .padding(6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    for(j in viewModel.players.indices){
                        Text(
                            text = viewModel.players[j].scores[i].toString(),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(6.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            Row(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)){
                Text(
                    text = "Total",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
                viewModel.players.forEach{ player ->
                    Text(
                        text = player.total.toString(),
                        fontSize = 24.sp,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun RankingTable(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
){
    val horizontalScroll = rememberScrollState()
    val verticalScroll = rememberScrollState()
    val sortedPlayers = remember(viewModel.players){
        viewModel.players.sortedByDescending { it.total }
    }
    Box(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .horizontalScroll(horizontalScroll)
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Column{
            Row(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    "Player",
                    modifier = Modifier
                        .width(160.dp)
                        .padding(6.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
                VerticalDivider()
                Text(
                    "Total Score",
                    modifier = Modifier
                        .width(160.dp)
                        .padding(6.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
            }
            for(player in sortedPlayers){
                Row{
                    Text(
                        text = player.name,
                        modifier = Modifier
                            .width(160.dp)
                            .padding(6.dp),
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    VerticalDivider()
                    Text(
                        text = player.total.toString(),
                        modifier = Modifier
                            .width(160.dp)
                            .padding(6.dp),
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreboardScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
){
    var showScoreboard by rememberSaveable{ mutableStateOf(true) }
    if(viewModel.gameProcess == MainViewModel.GAMEPROCESS.INIT) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Spacer(Modifier.height(48.dp))
            Text(
                text = stringResource(R.string.no_game_to_show),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
    }
    else{
        Column(modifier = modifier.fillMaxSize()){
            Box(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                Button(
                    onClick = { showScoreboard = !showScoreboard },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.extendedColorScheme.purple.color,
                        contentColor = MaterialTheme.extendedColorScheme.purple.onColor
                    )
                ){
                    Text(
                        text = if(showScoreboard) stringResource(R.string.show_ranking) else stringResource(R.string.show_scoreboard),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            if(showScoreboard) ScoreTable(viewModel) else RankingTable(viewModel)
        }
    }
}