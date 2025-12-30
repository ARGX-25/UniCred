package com.example.unicred.ui.screens.student

import android.graphics.Color.red
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unicred.ui.theme.bgBlue
import com.example.unicred.ui.theme.primaryBlue
import com.example.unicred.R
import com.example.unicred.ui.theme.blue
import com.example.unicred.ui.theme.green
import com.example.unicred.ui.theme.grey
import com.example.unicred.ui.theme.orange
import com.example.unicred.ui.theme.red
import com.example.unicred.ui.theme.secondaryBlue


@Composable
fun StudentDashboard(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = { StudentNavBar(
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
                RecentCredentialsSection()
            }

            item {
                QuickActionsSection()
            }
        }
    }
}

@Composable
private fun DashboardHeader() {
    Column {
        Text("Dashboard", fontSize = 18.sp, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Welcome back, Student One!", fontSize = 22.sp, color = Color.White)
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
            title = "Total Credentials",
            value = "7",
            iconRes = R.drawable.docs,
            iconTint = blue
        ),
        StatItem(
            title = "Verified",
            value = "6",
            iconRes = R.drawable.verified,
            iconTint = green
        ),
        StatItem(
            title = "Pending",
            value = "1",
            iconRes = R.drawable.clock,
            iconTint = orange
        ),
        StatItem(
            title = "Expired",
            value = "0",
            iconRes = R.drawable.pending,
            iconTint = red
        )
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

data class Credential(
    val title: String,
    val issuer: String,
    val date: String,
    val status: String,
    val statusColor: Color
)

@Composable
private fun RecentCredentialsSection() {
    val credentials = listOf(
        Credential("Bachelor of Computer Science", "Tech University", "Issued: 31/8/2024","Verified",green),
        Credential("Data Analysis Certificate", "Data Institute", "Issued: 23/3/2025","Verified",green),
        Credential("Machine Learning Diploma", "AI Academy", "12/8/2025","Pending",orange)
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Recent Credentials", fontSize = 18.sp, color = Color.White)

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
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Column(modifier = Modifier.padding(8.dp)) {
                Text(credential.title, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    credential.issuer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = grey
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    credential.date,
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

    }
}


@Composable
private fun QuickActionsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Quick Actions", fontSize = 18.sp, color = Color.White)

        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // TODO: open add credential dialog
                },
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    tint = secondaryBlue,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Add Credential", color = secondaryBlue)
            }

            OutlinedButton(
                onClick = {
                    // TODO: share credentials as PDF
                },
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.share),
                    tint = secondaryBlue,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Share Profile", color = secondaryBlue)
            }
        }
    }
}

@Composable
fun StudentNavBar(
    navController: NavController,
    currentRoute: String
) {
    NavigationBar(
        containerColor = primaryBlue
    ) {
        NavigationBarItem(
            selected = currentRoute == "student_dashboard",
            onClick = { navController.navigate("student_dashboard") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home),
                    contentDescription = null,
                    tint = if (currentRoute == "student_dashboard") secondaryBlue else grey
                )
            },
            label = { Text("Dashboard", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "student_credentials",
            onClick = { /* navigate later */ },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.docs),
                    contentDescription = null,
                    tint = if (currentRoute == "student_credentials") secondaryBlue else grey
                )
            },
            label = { Text("My Credentials", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "student_profile",
            onClick = { navController.navigate("student_profile") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = null,
                    tint = if (currentRoute == "student_profile") secondaryBlue else grey
                )
            },
            label = { Text("Profile", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}


