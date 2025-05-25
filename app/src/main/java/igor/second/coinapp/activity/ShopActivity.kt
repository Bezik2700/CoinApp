package igor.second.coinapp.activity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import igor.second.coinapp.R
import igor.second.coinapp.databases.PurchaseHelper

@Composable
fun ShopActivity(navController: NavController,
                 purchaseHelper: PurchaseHelper
){

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)){

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Divider(color = Color.Red)
            Spacer(modifier = Modifier.padding(8.dp))
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                ShoppingCard(
                    description = R.string.firstDescription,
                    price = R.string.firstPrice,
                    img = R.drawable.ic_launcher_background,
                    onClick = {purchaseHelper.makePurchase()})
                Spacer(modifier = Modifier.padding(8.dp))
                ShoppingCard(
                    description = R.string.secondDescription,
                    price = R.string.secondPrice,
                    img = R.drawable.ic_launcher_background,
                    onClick = {})
                Spacer(modifier = Modifier.padding(8.dp))
                ShoppingCard(
                    description = R.string.thirdDescription,
                    price = R.string.thirdPrice,
                    img = R.drawable.ic_launcher_background,
                    onClick = {})
            }
            Spacer(modifier = Modifier.padding(32.dp))
            Divider(color = Color.Red)
            Spacer(modifier = Modifier.padding(8.dp))
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                ShoppingCard(
                    description = R.string.fourthDescription,
                    price = R.string.fourthPrice,
                    img = R.drawable.ic_launcher_background,
                    onClick = {})
                Spacer(modifier = Modifier.padding(8.dp))
                ShoppingCard(
                    description = R.string.fifthDescription,
                    price = R.string.fifthPrice,
                    img = R.drawable.ic_launcher_background,
                    onClick = {})
                Spacer(modifier = Modifier.padding(8.dp))
                ShoppingCard(
                    description = R.string.sixthDescription,
                    price = R.string.sixthPrice,
                    img = R.drawable.ic_launcher_background,
                    onClick = {})
            }
        }
    }
}

/*@Composable
fun TestPurchases(purchaseHelper: PurchaseHelper){

    val buyEnabled by purchaseHelper.buyEnabled.collectAsState(false)
    val consumeEnabled by purchaseHelper.consumeEnabled.collectAsState(false)
    val productName by purchaseHelper.productName.collectAsState("")
    val statusText by purchaseHelper.statusText.collectAsState("")

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(productName, Modifier.padding(20.dp), fontSize = 30.sp)
        Text(statusText)
        Row(Modifier.padding(20.dp)) {
            Button(onClick = { purchaseHelper.makePurchase() }, Modifier.padding(20.dp),
                enabled = buyEnabled
            ) {
                Text("Purchase")
            }
            Button(
                onClick = { purchaseHelper.consumePurchase() },
                Modifier.padding(20.dp),
                enabled = consumeEnabled
            ) {
                Text("Consume")
            }
        }
    }
}*/

@Composable
fun ShoppingCard(
    @StringRes description: Int,
    @StringRes price: Int,
    @DrawableRes img: Int,
    onClick: () -> Unit
){
    Card (modifier = Modifier
        .size(width = 110.dp, height = 200.dp)
        .clickable { onClick.invoke() }) {
        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {
            Image(
                modifier = Modifier,
                painter = painterResource(img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds)
            Column {
                Text(
                    text = stringResource(price),
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic
                )
                HorizontalDivider(thickness = 1.dp, color = Color.Red)
                Text(
                    text = stringResource(description),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal
                )
            }
        }
    }
}
