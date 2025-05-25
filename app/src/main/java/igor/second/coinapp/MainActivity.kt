package igor.second.coinapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import igor.second.coinapp.databases.DataStoreManager
import igor.second.coinapp.databases.MyNavController
import igor.second.coinapp.databases.NotificationWorker
import igor.second.coinapp.databases.PurchaseHelper
import igor.second.coinapp.ui.theme.CoinAppTheme

const val AD_ID = "ca-app-pub-9456842051222073/5368009888"

class MainActivity : ComponentActivity() {

    var rewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val dataStoreManager = DataStoreManager(this)

        val purchaseHelper = PurchaseHelper(this)
        purchaseHelper.billingSetup()

        MobileAds.initialize(this)
        loadAd()

        setContent {
            CoinAppTheme {

                val counter = remember { mutableIntStateOf(1) }
                val plusForCounter = remember { mutableIntStateOf(1) }
                val currency = remember { mutableIntStateOf(0) }

                LaunchedEffect(key1 = true) {
                    dataStoreManager.getSettings().collect { settings ->
                        counter.intValue = settings.counter
                        plusForCounter.intValue = settings.plusForCounter
                        currency.intValue = settings.currency
                    }
                }
                Column (modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {
                        rewardedAd?.show(this@MainActivity){ item ->
                            Toast.makeText(this@MainActivity, "${item.amount}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(text = "adssad")
                    }
                }
            }
        }
    }

    private fun oneTimeWorkManager(){
        val workManager = WorkManager.getInstance(applicationContext)
        val oneTimeWorkRequest = OneTimeWorkRequest
            .Builder(NotificationWorker::class.java)
            .build()
        workManager.enqueue(oneTimeWorkRequest)
    }

    private fun loadAd(){
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, AD_ID, adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                }

                override fun onAdLoaded(add: RewardedAd) {
                    super.onAdLoaded(add)
                    rewardedAd = add
                    rewardedAd?.fullScreenContentCallback = adListener()
                }
            }
        )
    }
    private fun adListener() = object : FullScreenContentCallback(){
        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            rewardedAd = null
            loadAd()
        }

        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            rewardedAd = null
            loadAd()
        }
    }
}

/*@Composable
fun ads(rewardedAd: RewardedAd?){

    val context = LocalContext.current

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            rewardedAd?.show(this@MainActivity){ item ->
                Toast.makeText(this@MainActivity, "${item.amount}",
                    Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "adssad")
        }
    }
}*/

sealed class Screens(val route: String) {
    data object StartActivity: Screens("StartActivity")
    data object ShopActivity: Screens("ShopActivity")
    data object RefreshActivity: Screens("RefreshActivity")
}