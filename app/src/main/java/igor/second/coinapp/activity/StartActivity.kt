package igor.second.coinapp.activity

import android.app.Activity
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.android.gms.ads.rewarded.RewardedAd
import igor.second.coinapp.R
import igor.second.coinapp.databases.DataStoreManager
import igor.second.coinapp.databases.SettingData
import kotlinx.coroutines.launch

@Composable
fun StartActivity(
    navController: NavController,
    dataStoreManager: DataStoreManager,
    counter: MutableState<Int>,
    plusForCounter: MutableState<Int>,
    currency: MutableState<Int>,
    rewardedAd: RewardedAd?
){

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    var showDialogInfo by remember { mutableStateOf(false) }

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)){

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalAlignment = Alignment.Start) {
            IconButton(
                colors = IconButtonColors(
                    contentColor = Color.Red,
                    containerColor = Color.Gray,
                    disabledContentColor = Color.Blue,
                    disabledContainerColor = Color.Green
                ),
                onClick = {showDialogInfo = true}) {
                Icon(
                    Icons.Rounded.Info,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
            if (showDialogInfo){
                Dialog(onDismissRequest = { showDialogInfo = false }) {
                    Card (modifier = Modifier.size(350.dp)) {
                        Column (
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "description")
                            Button(onClick = { showDialogInfo = false }) {
                                Text(text = "Apply")
                            }
                        }
                    }
                }
            }
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalAlignment = Alignment.End) {
            NewButton(
                onClick = {

                },
                imaRes = R.drawable.x22
            )
            NewButton(
                onClick = {
                    rewardedAd?.show(context as Activity){ item ->
                        Toast.makeText(context,
                            "${item.amount}",
                            Toast.LENGTH_SHORT)
                            .show()

                        counter.value += 100

                        coroutine.launch {
                            dataStoreManager.saveSettings(
                                SettingData(
                                    counter = counter.value,
                                    plusForCounter = plusForCounter.value,
                                    currency = currency.value
                                )
                            )
                        }
                    }
                },
                imaRes = R.drawable.hundred
            )
        }
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                modifier = Modifier.clickable {
                    counter.value += plusForCounter.value
                    coroutine.launch {
                        dataStoreManager.saveSettings(
                            SettingData(
                                counter = counter.value,
                                plusForCounter = plusForCounter.value,
                                currency = currency.value
                            )
                        )
                    }
                },
                painter = painterResource(R.drawable.hame),
                contentDescription = null
            )
            Text(text = "${counter.value}",
                modifier = Modifier.padding(80.dp),
                fontSize = 24.sp)
        }

    }
}

@Composable
fun NewButton(onClick: () -> Unit,
              @DrawableRes imaRes: Int){
    Column (modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(imaRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clickable {
                onClick.invoke()
            }
        )
    }
}