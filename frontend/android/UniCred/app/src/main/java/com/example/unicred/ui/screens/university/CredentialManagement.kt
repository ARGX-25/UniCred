package com.example.unicred.ui.screens.university

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
import com.example.unicred.ui.theme.red
import com.example.unicred.ui.theme.secondaryBlue

@Composable
fun CredentialManagement(
    navController: NavController
) {
    var query by remember { mutableStateOf("") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    Scaffold(
        bottomBar = {
            UniversityNavBar(
                navController = navController,
                currentRoute = currentRoute
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

            item { CredentialManagementHeader() }

            item { CredentialStatsGrid() }

            item {
                CredentialSearchAndFilter(
                    query = query,
                    onQueryChange = { query = it }
                )
            }

            item {
                IssueCredentialButton()
            }

            item {
                PendingVerificationsSection()
            }
        }
    }
}

@Composable
private fun CredentialManagementHeader() {
    Column {
        Text("Credential Management", fontSize = 18.sp, color = Color.White)
        Spacer(Modifier.height(12.dp))
        Text("Credential Management", fontSize = 22.sp, color = Color.White)
    }
}
data class SimpleStatItem(
    val title: String,
    val value: String,

)
@Composable
private fun CredentialStatsGrid() {
    val stats = listOf(
        SimpleStatItem("Total Issued", "32"),
        SimpleStatItem("Verified", "27"),
        SimpleStatItem("Pending", "5"),
        SimpleStatItem("Revoked", "0")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(200.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stats) { stat ->
            StatCard(stat)
        }
    }
}

@Composable
private fun StatCard(stat: SimpleStatItem) {
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
        Text("Search Students and Credentials", fontSize = 18.sp, color = Color.White)
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
                            "Search by name, student ID, or program…",
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

@Composable
private fun CredentialSearchAndFilter(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            SearchCredentialsSection(
                query = query,
                onQueryChange = onQueryChange,
                onSearchClick = {}
            )
        }

        IconButton(
            onClick = { /* open filters */ },
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryBlue)
        ) {
            Icon(
                painter = painterResource(R.drawable.filter),
                contentDescription = "Filter",
                tint = secondaryBlue
            )
        }
    }
}



@Composable
private fun IssueCredentialButton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { /* open issue dialog */ },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(secondaryBlue)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(Modifier.width(8.dp))
            Text("Issue New Credential", color = Color.White)
        }
    }
}

data class CredentialItem(
    val title: String,
    val hash: String,
    val issuedAt: String,
    val type: String,
    val status: String,
    val statusColor: Color
)


enum class CredentialActionType {
    VERIFY,
    VIEW,
    DOWNLOAD,
    SHARE,
    REVOKE
}
@Composable
private fun StatusChip(
    text: String,
    color: Color
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(color)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
fun CredentialCard(
    credential: CredentialItem,
    actions: List<CredentialActionType>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(primaryBlue)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(credential.title, color = Color.White)

                StatusChip(
                    text = credential.status,
                    color = credential.statusColor
                )
            }

            Text(credential.hash, color = grey, fontSize = 12.sp)
            Text(credential.issuedAt, color = grey, fontSize = 12.sp)
            Text("Type: ${credential.type}", color = grey, fontSize = 12.sp)

            HorizontalDivider(
                color = grey.copy(alpha = 0.3f),
                thickness = 1.dp
            )

            // Actions row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                actions.forEach { action ->
                    CredentialActionButton(action)
                }
            }
        }
    }
}



@Composable
private fun CredentialActionButton(
    action: CredentialActionType,
    onClick: () -> Unit = {}
) {
    val (icon, label, tint) = when (action) {
        CredentialActionType.VERIFY ->
            Triple(R.drawable.total_verified, "Verify", green)

        CredentialActionType.VIEW ->
            Triple(R.drawable.view, "View Details", secondaryBlue)

        CredentialActionType.DOWNLOAD ->
            Triple(R.drawable.download, "Download", secondaryBlue)

        CredentialActionType.SHARE ->
            Triple(R.drawable.share, "Share", secondaryBlue)

        CredentialActionType.REVOKE ->
            Triple(R.drawable.close, "Revoke", red)
    }

    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(label, color = tint, fontSize = 14.sp)
    }
}

@Composable
private fun PendingVerificationsSection() {
    val pending = listOf(
        CredentialItem(
            title = "Machine Learning Diploma",
            hash = "cmev1zy000dy5e2y2clr9r3",
            issuedAt = "Issued: 2025-07-31",
            type = "Diploma",
            status = "Pending",
            statusColor = orange
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Pending Verifications", fontSize = 18.sp, color = Color.White)

        pending.forEach {
            CredentialCard(
                credential = it,
                actions = listOf(
                    CredentialActionType.VERIFY,
                    CredentialActionType.VIEW,
                    CredentialActionType.DOWNLOAD
                )
            )
        }
    }
}

@Composable
private fun AllCredentialsSection() {
    val all = listOf(
        CredentialItem(
            title = "Cognitive Behavioral Therapy Certificate",
            hash = "cmev1zy000wy5iedqel7zgu",
            issuedAt = "Issued: 2025-07-11",
            type = "Certificate",
            status = "Verified",
            statusColor = green
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("All Credentials", fontSize = 18.sp, color = Color.White)

        all.forEach {
            CredentialCard(
                credential = it,
                actions = listOf(
                    CredentialActionType.VIEW,
                    CredentialActionType.DOWNLOAD,
                    CredentialActionType.SHARE,
                    CredentialActionType.REVOKE
                )
            )
        }
    }
}




