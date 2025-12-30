package com.example.unicred.ui.screens.university

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unicred.R
import com.example.unicred.ui.screens.student.SettingItem
import com.example.unicred.ui.theme.bgBlue
import com.example.unicred.ui.theme.grey
import com.example.unicred.ui.theme.primaryBlue
import com.example.unicred.ui.theme.secondaryBlue

@Composable
fun UniversitySettings(
    navController: NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = { UniversityNavBar(
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
            item{
                SettingsHeader()
            }

            item{
                SettingsSection(
                    title = "General Settings",
                    items = listOf(
                        SettingItem.Toggle("Push Notifications","Receive Notifications about important updates",R.drawable.bell),
                        SettingItem.Toggle("Email Alerts","Get Email Notifications about critical events",R.drawable.email),
                        SettingItem.Toggle("Dark Mode","Use Dark Theme Throughout the App",R.drawable.moon),
                        SettingItem.Toggle("Auto Verification", "Automatically verify credentials whenever possible",R.drawable.total_verified)
                    )
                )
            }

            item {
                SettingsSection(
                    title = "University Management",
                    items = listOf(
                        SettingItem.Arrow(
                            title = "University Profile",
                            subtitle = "Manage university information and details",
                            icon = R.drawable.university
                        ),
                        SettingItem.Arrow(
                            title = "Academic Settings",
                            subtitle = "Configure academic policies and requirements",
                            icon = R.drawable.university
                        ),
                        SettingItem.Arrow(
                            title = "Faculty Management",
                            subtitle = "Manage faculty members and advisors",
                            icon = R.drawable.people
                        ),
                        SettingItem.Arrow(
                            title = "Reports & Analytics",
                            subtitle = "View institutional reports and statistics",
                            icon = R.drawable.graph
                        )
                    )
                )
            }

            item {
                SettingsSection(
                    title = "Account & Security",
                    items = listOf(
                        SettingItem.Arrow(
                            title = "Edit Profile",
                            subtitle = "Update your personal information",
                            icon = R.drawable.profile
                        ),
                        SettingItem.Arrow(
                            title = "Change Password",
                            subtitle = "Update your account password",
                            icon = R.drawable.lock
                        ),
                        SettingItem.Arrow(
                            title = "Privacy Settings",
                            subtitle = "Manage data sharing and privacy preferences",
                            icon = R.drawable.shield
                        ),
                        SettingItem.Arrow(
                            title = "Logout",
                            subtitle = "Sign out of your account",
                            icon = R.drawable.logout,

                        )
                    )
                )
            }


        }
    }
}
@Composable
private fun SettingsHeader(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text("Settings", fontSize = 28.sp, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Manage your Univeristy Preferences", fontSize = 18.sp, color = grey)
    }
}

@Composable
private fun SettingsSection(
    title: String,
    items: List<SettingItem>
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, color = Color.White, fontSize = 16.sp)

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(primaryBlue)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    when (item) {
                        is SettingItem.Arrow -> ArrowItem(item)
                        is SettingItem.Toggle -> ToggleItem(item)
                    }

                    if (index != items.lastIndex) {
                        HorizontalDivider(
                            color = Color.White.copy(alpha = 0.08f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArrowItem(item: SettingItem.Arrow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(item.icon),
            contentDescription = null,
            tint = secondaryBlue,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(item.title, color = Color.White)
            Text(item.subtitle, color = grey, fontSize = 12.sp)
        }

        Icon(
            painterResource(R.drawable.arrow_next),
            contentDescription = null,
            tint = grey
        )
    }
}

@Composable
private fun ToggleItem(item: SettingItem.Toggle) {
    var checked by remember { mutableStateOf(item.enabled) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(item.icon),
            contentDescription = null,
            tint = secondaryBlue,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(item.title, color = Color.White)
            Text(item.subtitle, color = grey, fontSize = 12.sp)
        }

        Switch(
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }
}
