package com.example.unicred.ui.screens.student

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unicred.R
import com.example.unicred.ui.theme.bgBlue
import com.example.unicred.ui.theme.grey
import com.example.unicred.ui.theme.primaryBlue
import com.example.unicred.ui.theme.red
import com.example.unicred.ui.theme.secondaryBlue


@Composable
fun ProfileScreen(
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
            item{
                ProfileHeader()
            }

            item{
                SettingsSection(
                    title = "Account",
                    items = listOf(
                        SettingItem.Arrow("Edit Profile", "Update your personal information", R.drawable.profile),
                        SettingItem.Arrow("Change Password", "Update your password", R.drawable.lock),
                        SettingItem.Arrow("Privacy Settings", "Manage your privacy preferences", R.drawable.total_verified)
                    )
                )
            }

            item{
                SettingsSection(
                    title = "Preferences",
                    items = listOf(
                        SettingItem.Toggle("Notifications", "Manage notification preferences", R.drawable.bell),
                        SettingItem.Toggle("Dark Mode", "Toggle dark theme", R.drawable.moon)
                    )
                )
            }

            item{
                SettingsSection(
                    title = "Support",
                    items = listOf(
                        SettingItem.Arrow("Help & Support", "Get help and contact support", R.drawable.help),
                        SettingItem.Arrow("About", "App version and information", R.drawable.info)
                    )
                )
            }

            item{
                LogoutButton()
            }

        }
    }
}

@Composable
private fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Student One", fontSize = 22.sp, color = Color.White)
        Text("student1@example.edu", color = grey)
        Text("Student", color = grey)

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

sealed class SettingItem {
    data class Arrow(
        val title: String,
        val subtitle: String,
        val icon: Int
    ) : SettingItem()

    data class Toggle(
        val title: String,
        val subtitle: String,
        val icon: Int,
        val enabled: Boolean = true
    ) : SettingItem()
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
