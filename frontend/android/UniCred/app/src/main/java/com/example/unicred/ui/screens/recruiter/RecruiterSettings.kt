package com.example.unicred.ui.screens.recruiter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unicred.R
import com.example.unicred.ui.screens.student.SettingItem
import com.example.unicred.ui.theme.*

@Composable
fun RecruiterSettings(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            RecruiterNavBar(
                navController = navController,
                currentRoute = currentRoute ?: ""
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(bgBlue)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item { RecruiterHeader() }

            item {
                SettingsSection(
                    title = "Account",
                    items = listOf(
                        SettingItem.Arrow(
                            "Edit Profile",
                            "Update your personal information",
                            R.drawable.profile
                        ),
                        SettingItem.Arrow(
                            "Change Password",
                            "Update your password",
                            R.drawable.lock
                        ),
                        SettingItem.Arrow(
                            "Privacy Settings",
                            "Manage your privacy preferences",
                            R.drawable.total_verified
                        )
                    )
                )
            }

            item {
                SettingsSection(
                    title = "Preferences",
                    items = listOf(
                        SettingItem.Toggle(
                            "Push Notifications",
                            "Receive notifications about verifications",
                            R.drawable.bell
                        ),
                        SettingItem.Toggle(
                            "Email Alerts",
                            "Receive email notifications",
                            R.drawable.email
                        ),
                        SettingItem.Toggle(
                            "Dark Mode",
                            "Toggle dark theme",
                            R.drawable.moon
                        ),
                        SettingItem.Toggle(
                            "Auto Verification",
                            "Automatically verify simple credentials",
                            R.drawable.total_verified,
                            enabled = false
                        )
                    )
                )
            }

            item {
                SettingsSection(
                    title = "Support",
                    items = listOf(
                        SettingItem.Arrow(
                            "Help & Support",
                            "Get help and contact support",
                            R.drawable.help
                        ),
                        SettingItem.Arrow(
                            "Terms of Service",
                            "Read our terms and conditions",
                            R.drawable.docs
                        ),
                        SettingItem.Arrow(
                            "About",
                            "App version and information",
                            R.drawable.info
                        )
                    )
                )
            }

            item { LogoutButton() }
        }
    }
}

@Composable
private fun RecruiterHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Recruiter One", fontSize = 22.sp, color = Color.White)
        Text("recruiter1@example.edu", color = grey)
        Text("Recruiter", color = grey)

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { },
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, secondaryBlue)
        ) {
            Icon(
                painterResource(R.drawable.edit),
                contentDescription = null,
                tint = secondaryBlue,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text("Edit Profile", color = secondaryBlue)
        }
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

@Composable
private fun LogoutButton() {
    OutlinedButton(
        onClick = { },
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Red),
        shape = RoundedCornerShape(14.dp)
    ) {
        Icon(
            painterResource(R.drawable.logout),
            modifier = Modifier.rotate(90f),
            contentDescription = null,
            tint = red
        )
        Spacer(Modifier.width(6.dp))
        Text("Logout", color = red)
    }
}
