package igor.second.coinapp.databases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.rewarded.RewardedAd
import igor.second.coinapp.Screens
import igor.second.coinapp.activity.RefreshActivity
import igor.second.coinapp.activity.ShopActivity
import igor.second.coinapp.activity.StartActivity

@Composable
fun MyNavController(navController: NavHostController,
                    dataStoreManager: DataStoreManager,
                    counter: MutableState<Int>,
                    plusForCounter: MutableState<Int>,
                    currency: MutableState<Int>,
                    rewardedAd: RewardedAd?,
                    purchaseHelper: PurchaseHelper){
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screens.RefreshActivity.route) },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                shape = CircleShape,
            ) {
                Row {
                    Text(text = currency.value.toString())
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = "coin")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.navigate(Screens.RefreshActivity.route) }) {
                        Icon(
                            Icons.Rounded.Refresh,
                            contentDescription = "refresh",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate(Screens.StartActivity.route) }) {
                        Icon(
                            Icons.Rounded.Home,
                            contentDescription = "activity",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate(Screens.ShopActivity.route) }) {
                        Icon(
                            Icons.Rounded.ShoppingCart,
                            contentDescription = "shop",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box (modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screens.StartActivity.route
            ) {
                composable(route = Screens.StartActivity.route){
                    StartActivity(
                        navController = rememberNavController(),
                        dataStoreManager = dataStoreManager,
                        counter = counter,
                        plusForCounter = plusForCounter,
                        currency = currency,
                        rewardedAd = rewardedAd
                    )
                }
                composable(route = Screens.ShopActivity.route){
                    ShopActivity(
                        navController = rememberNavController(),
                        purchaseHelper = purchaseHelper
                    )
                }
                composable(route = Screens.RefreshActivity.route){
                    RefreshActivity(
                        navController = rememberNavController(),
                        dataStoreManager = dataStoreManager,
                        counter = counter,
                        plusForCounter = plusForCounter,
                        currency = currency
                    )
                }
            }
        }
    }
}

