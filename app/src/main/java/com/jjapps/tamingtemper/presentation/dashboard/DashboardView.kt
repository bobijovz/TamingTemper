package com.jjapps.tamingtemper.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.jjapps.tamingtemper.R
import com.jjapps.tamingtemper.data.local.model.LevelData


@Composable
fun DashboardView() {
    val viewModel : DashboardViewmodel = hiltViewModel()
    val state by viewModel.dashboardState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getLevelList()

    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        /*** Header Section **/
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.journey_icon),
                contentDescription = null,
                modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.width(8.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = "Taming temper", style = MaterialTheme.typography.labelLarge)
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    LinearProgressIndicator(progress = { 0.3f }, modifier = Modifier.width(80.dp), color = Color(0xFF6442EF))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "3 %", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6442EF))
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row (
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.fire_icon), contentDescription = null, modifier = Modifier.size(32.dp))
                Text(text = "0", style = MaterialTheme.typography.bodySmall, color = Color(0xFF6442EF))
                Spacer(modifier = Modifier.width(8.dp))
                CircularIconButton(iconRes = R.drawable.user_icon, onClick = { })
            }

        }
        /*** Weekdays tabrow Section **/
        WeekdayRow()

        /*** Contents Section **/
        LevelList(state)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LevelList(data : List<LevelData.Level?>) {
    val screenWidthPx = LocalConfiguration.current.screenWidthDp
    val columnWidthPx = screenWidthPx / 2

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(data) { item ->
            Box(
                modifier = Modifier
                    .size(100.dp) // Adjust as needed
            ) {
                Image(
                    painter = painterResource(id = R.drawable.level_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "LEVEL ${item?.level}",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(Color(0xFF212121))
                        .padding(5.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = item?.title ?: "",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = item?.description ?: "",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(5.dp))

            /*** Activities section **/
            //TODO implement coil to handle activity icon
            // fix activity title
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                item?.activities?.forEach { activity ->
                    Box(
                        modifier = Modifier
                            .width(columnWidthPx.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(8.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = activity?.title ?: "")
                            }
                        }
                    }
                }
            }

        }
    }

}


@Composable
fun WeekdayRow() {
    val tabItems = listOf("MON","TUE","WED","THU","FRI","SAT","SUN")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex) {
        tabItems.forEachIndexed { index, day ->
            Tab (
                modifier = Modifier.padding(0.dp),
                selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                },
                text = {
                    Text(text = day, modifier = Modifier.padding(horizontal = 0.dp), style = MaterialTheme.typography.labelSmall)
                },
                icon = {
                    Image(painter = if(index == selectedTabIndex) painterResource(id = R.drawable.ellipse_filled)
                        else painterResource(id = R.drawable.ellipse_hollow),
                        contentDescription = null)
                }
            )
        }
        when (selectedTabIndex){
            0 -> {}
            1 -> {}
            2 -> {}
            3 -> {}
            4 -> {}
            5 -> {}
        }
    }
}

@Composable
fun CircularIconButton(iconRes: Int, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .shadow(elevation = 3.dp, shape = CircleShape)
            .background(androidx.compose.ui.graphics.Color.White, shape = CircleShape)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
