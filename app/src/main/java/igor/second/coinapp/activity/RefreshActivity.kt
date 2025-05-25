package igor.second.coinapp.activity

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PlatformImeOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import igor.second.coinapp.databases.DataStoreManager
import igor.second.coinapp.databases.SettingData
import igor.second.coinapp.databases.roomdb.MainViewModel
import igor.second.coinapp.databases.roomdb.RoomData
import kotlinx.coroutines.launch

@Composable
fun RefreshActivity(navController: NavController,
                    dataStoreManager: DataStoreManager,
                    counter: MutableState<Int>,
                    plusForCounter: MutableState<Int>,
                    currency: MutableState<Int>){

    val context = LocalContext.current
    var count by remember { mutableIntStateOf(0) }
    val coroutine = rememberCoroutineScope()
    val course by remember { mutableIntStateOf(20000) }

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoomActivity(
                    dataStoreManager = dataStoreManager,
                    currency = currency,
                    counter = counter,
                    plusForCounter = plusForCounter
                )
            }
        }
        Card (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)) {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Description")
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.size(width = 150.dp, height = 60.dp),
                        value = count.toString(),
                        onValueChange = { count = it.toInt() },
                        label = { Text("Enter your counter") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                    )
                    Text(text = " = $course")
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(onClick = {
                    if (counter.value > count * course && count > 0) {
                        counter.value -= count * course
                        currency.value += count
                        coroutine.launch {
                            dataStoreManager.saveSettings(
                                SettingData(
                                    counter = counter.value,
                                    currency = currency.value,
                                    plusForCounter = plusForCounter.value
                                )
                            )
                        }
                        count = 0
                        Toast.makeText(context, "Operation is good", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Operation is not good pidr", Toast.LENGTH_LONG)
                            .show()
                    }
                }) {
                    Text(text = "Obmenyat")
                }
            }
        }
    }
}

@Composable
fun ListItem(item: RoomData,
             onClick: (RoomData) -> Unit,
             onClickDelete: (RoomData) -> Unit){

    val context = LocalContext.current

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick(item)
                Toast
                    .makeText(
                        context, "Counter ${item.unique} copy on buffer",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(text = "be-")
            Text(
                text = item.unique.toString(),
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomActivity(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
    dataStoreManager: DataStoreManager,
    counter: MutableState<Int>,
    plusForCounter: MutableState<Int>,
    currency: MutableState<Int>
    ){

    val itemList = mainViewModel.itemList.collectAsState(initial = emptyList())

    val state = rememberLazyListState()
    val course by remember { mutableIntStateOf(12) }
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            if (currency.value > course){
                currency.value -= course
                mainViewModel.newUnique.intValue = mathOperationForUnique()
                mainViewModel.insertItem()
                coroutine.launch {
                    dataStoreManager.saveSettings(
                        SettingData(
                            counter = counter.value,
                            currency = currency.value,
                            plusForCounter = plusForCounter.value
                        )
                    )
                }
            }
            else{
                Toast.makeText(context, "LOX", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "GENERATE")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .border(width = 1.dp, color = Color.Blue),
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(itemList.value) { item ->
                ListItem(item,
                    {
                        mainViewModel.roomData = it
                        mainViewModel.newUnique.intValue = it.unique
                    },
                    {
                        mainViewModel.deleteItem(it)
                    }
                )
            }
        }
    }
}

fun mathOperationForUnique(): Int {
    var a = 1

    return a
}