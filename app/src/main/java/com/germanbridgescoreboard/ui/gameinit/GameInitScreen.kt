package com.germanbridgescoreboard.ui.gameinit

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.germanbridgescoreboard.Destination
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun PlayerNameInput(
    num: Int,
    text: String,
    onTextChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?, // null if last field
    isLast: Boolean
){
    val keyboardController = LocalSoftwareKeyboardController.current

    Row {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.width(120.dp).height(54.dp)
        ){
            Text(
                text = "Player $num",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        TextField(
            label = { Text("Name") },
            value = text,
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (isLast) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    nextFocusRequester?.requestFocus()
                },
                onDone = {
                    keyboardController?.hide()
                }
            ),
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
    }
}

@Composable
fun GameInitScreen(
    viewModel: MainViewModel,
    numPlayers: Int,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier) {
    val localNames = rememberSaveable (
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList()}
        )
    ) {
        mutableStateListOf<String>().apply {
            repeat(numPlayers) { add("") }
        }
    }

    LaunchedEffect(numPlayers) {
        if (localNames.size < numPlayers) {
            repeat(numPlayers - localNames.size) {
                localNames.add("")
            }
        } else if (localNames.size > numPlayers) {
            repeat(localNames.size - numPlayers) {
                localNames.removeAt(localNames.lastIndex)
            }
        }
    }

    val focusRequesters = remember(numPlayers) {
        List(numPlayers) { FocusRequester() }
    }

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn (
        modifier = modifier
    ){
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Input Player Names",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterVertically)
                )

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {
                        val trimmed = localNames.map { it.trim() }

                        keyboardController?.hide()

                        when{
                            trimmed.any{ it.isEmpty() } -> {
                                coroutineScope.launch{
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        message = "Please fill in all player names",
                                        actionLabel = "OK",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            trimmed.toSet().size != trimmed.size -> {
                                coroutineScope.launch{
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        message = "Names must be unique.",
                                        actionLabel = "OK",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            else -> {
                                Snapshot.withMutableSnapshot{
                                    viewModel.initGame(trimmed)
                                }
                                navController.navigate(Destination.HOME.route){
                                    launchSingleTop = true
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(end = 12.dp)
                    ){
                    Text(text = "Start")
                }
            }
        }
        items(numPlayers){ i ->
            PlayerNameInput(
                num = i + 1,
                text = localNames[i],
                onTextChange = {localNames[i] = it},
                focusRequester = focusRequesters[i],
                nextFocusRequester = focusRequesters.getOrNull(i + 1),
                isLast = i == numPlayers - 1
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun GameInitScreenPreview() {
    val viewModel = MainViewModel()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    AppTheme{
        GameInitScreen(viewModel, 10, navController, snackbarHostState, Modifier)
    }
}