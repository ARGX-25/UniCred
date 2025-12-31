package com.example.unicred.ui.screens.recruiter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unicred.R
import com.example.unicred.ui.theme.bgBlue
import com.example.unicred.ui.theme.blue
import com.example.unicred.ui.theme.green
import com.example.unicred.ui.theme.grey
import com.example.unicred.ui.theme.orange
import com.example.unicred.ui.theme.primaryBlue
import com.example.unicred.ui.theme.purple
import com.example.unicred.ui.theme.secondaryBlue

@Composable
fun RecruiterDashboard(
    navController : NavController
) {

    var query by remember { mutableStateOf("") }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = { RecruiterNavBar(
            navController = navController,
            currentRoute = currentRoute ?: ""
        ) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(bgBlue)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                DashboardHeader()
            }

            item {
                StatsGrid()
            }

            item {
                SearchCredentialsSection(
                    query = query,
                    onQueryChange = { query = it },
                    onSearchClick = { /* trigger search using query */ }
                )
            }

            item {
                RecentCredentialsSection()
            }
        }
    }
}

@Composable
private fun DashboardHeader(

) {

    Column {
        Text("Dashboard", fontSize = 18.sp, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Welcome back, Recruiter One!", fontSize = 22.sp, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Here's your credential overview",
            style = MaterialTheme.typography.bodyMedium,
            color = grey
        )
    }
}

data class StatItem(
    val title: String,
    val value: String,
    val iconRes: Int,
    val iconTint: Color
)

@Composable
private fun StatsGrid() {
    val stats = listOf(
        StatItem(
            title = "Total Verification",
            value = "7",
            iconRes = R.drawable.total_verified,
            iconTint = green
        ),
        StatItem(
            title = "Verified Today",
            value = "6",
            iconRes = R.drawable.verified,
            iconTint = blue
        ),
        StatItem(
            title = "Pending",
            value = "1",
            iconRes = R.drawable.clock,
            iconTint = orange
        ),
        StatItem(
            title = "Candidates",
            value = "1",
            iconRes = R.drawable.people,
            iconTint = purple
        ),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(260.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stats) { stat ->
            StatCard(stat)
        }
    }
}

@Composable
private fun StatCard(stat: StatItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(primaryBlue)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = stat.iconRes),
                contentDescription = stat.title,
                tint = stat.iconTint,
                modifier = Modifier.size(28.dp)
            )
            Text(stat.value, fontSize = 22.sp, color = Color.White)
            Text(stat.title, style = MaterialTheme.typography.bodySmall, color = grey)
        }
    }
}

@Composable
private fun SearchCredentialsSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text("Search Candidates", fontSize = 18.sp, color = Color.White)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryBlue)
                .border(border = BorderStroke(0.5.dp,grey), shape = RoundedCornerShape(12.dp))
            , contentAlignment = Alignment.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 6.dp, end = 28.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    tint = grey
                )
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text(
                            "Search Candidates...",
                            color = grey,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp,vertical = 4.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                IconButton(
                    onClick = { /* trigger search */ },
                    modifier = Modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(secondaryBlue)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_up),
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp).rotate(90f)
                    )
                }

            }

        }
    }

}

data class Credential(
    val title: String,
    val email: String,
    val totalverified: String,
    val status: String,
    val statusColor: Color
)

@Composable
private fun RecentCredentialsSection() {
    val credentials = listOf(
        Credential(
            title = "Student One",
            email = "studentone@techuniversity.edu",
            totalverified = "5/7 credentials verified",
            status = "95%",
            statusColor = green
        ),
        Credential(
            title = "Aarav Mehta",
            email = "aarav.mehta@datainstitute.org",
            totalverified = "2/4 credentials verified",
            status = "88%",
            statusColor = green
        ),
        Credential(
            title = "Neha Sharma",
            email = "neha.sharma@aiacademy.io",
            totalverified = "3/10 credentials verified",
            status = "75%",
            statusColor = orange
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Top Candidates", fontSize = 18.sp, color = Color.White)

        credentials.take(3).forEach {
            CredentialCard(it)
        }
    }
}

@Composable
private fun CredentialCard(credential: Credential) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(primaryBlue)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(credential.title, color = Color.White)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        credential.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = grey
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        credential.totalverified,
                        style = MaterialTheme.typography.labelMedium,
                        color = grey
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.height(34.dp).width(64.dp).padding(top = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(credential.statusColor)
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = credential.status,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 18.dp),
                color = grey,
                thickness = 1.dp
            )


            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                ActionItem(
                    icon = R.drawable.view,
                    label = "View Details",
                    modifier = Modifier.weight(1f)

                )

                ActionItem(
                    icon = R.drawable.download,
                    label = "Download",
                    modifier = Modifier.weight(1f)
                )

                ActionItem(
                    icon = R.drawable.share,
                    label = "Share",
                    modifier = Modifier.weight(1f)
                )
            }
        }


    }
}

@Composable
private fun ActionItem(
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = secondaryBlue,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = secondaryBlue
        )
    }
}



@Composable
fun RecruiterNavBar(
    navController: NavController,
    currentRoute: String
) {
    NavigationBar(
        containerColor = primaryBlue
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("recruiter_dashboard")},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home),
                    contentDescription = null,
                    tint = if (currentRoute == "recruiter_dashboard") secondaryBlue else grey
                )},
            label = { Text("Dashboard", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate("credential_verification") },
            icon = {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.total_verified),
                        contentDescription = null,
                        tint = if (currentRoute == "credential_verification") secondaryBlue else grey
                    )
                    Text(
                        text = "Credentials\nVerification",
                        color = Color.White,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 13.sp
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )

            )

        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate("recruiter_settings") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = null,
                    tint = if (currentRoute == "recruiter_settings") secondaryBlue else grey
                )},
            label = { Text("Settings", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}