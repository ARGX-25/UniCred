package com.example.unicred.ui.screens.recruiter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unicred.R
import com.example.unicred.ui.theme.*

/* -------------------- ROOT -------------------- */

@Composable
fun CredentialVerification(
    navController: NavController
) {
    var query by remember { mutableStateOf("") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    Scaffold(
        bottomBar = {
            RecruiterNavBar(
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

            item { VerificationHeader() }

            item {
                CredentialSearchSection(
                    query = query,
                    onQueryChange = { query = it },
                    onVerifyClick = { /* trigger verification */ }
                )
            }

            item { RecentVerificationsSection() }
        }
    }
}

/* -------------------- HEADER -------------------- */

@Composable
private fun VerificationHeader() {
    Column {
        Text("Credential Verification", fontSize = 18.sp, color = Color.White)
        Spacer(Modifier.height(12.dp))
        Text("Credential Verification", fontSize = 22.sp, color = Color.White)
        Spacer(Modifier.height(4.dp))
        Text(
            "Verify the authenticity of student credentials",
            color = grey
        )
    }
}

/* -------------------- SEARCH -------------------- */

@Composable
private fun CredentialSearchSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onVerifyClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text("Search Credentials", fontSize = 18.sp, color = Color.White)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryBlue)
                .border(BorderStroke(0.5.dp, grey), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
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
                            "Search by name, student ID, or credential…",
                            color = grey
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clickable(onClick = onVerifyClick),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(secondaryBlue)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.verified),
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text("Verify Credential", color = Color.White)
            }
        }
    }
}

/* -------------------- DATA -------------------- */

data class VerificationItem(
    val title: String,
    val student: String,
    val date: String,
    val status: String,
    val statusColor: Color
)

/* -------------------- RECENT LIST -------------------- */

@Composable
private fun RecentVerificationsSection() {
    val recent = listOf(
        VerificationItem(
            "Bachelor of Computer Science",
            "John Doe • STU001",
            "Verified: 2024-01-15",
            "Verified",
            green
        ),
        VerificationItem(
            "Web Development Certificate",
            "Jane Smith • STU002",
            "Verified: 2024-01-14",
            "Verified",
            green
        ),
        VerificationItem(
            "Data Science Diploma",
            "Mike Johnson • STU003",
            "Pending",
            "Pending",
            orange
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text("Recent Verifications", fontSize = 18.sp, color = Color.White)

        recent.forEach {
            VerificationCard(it)
        }
    }
}

/* -------------------- CARD -------------------- */

@Composable
private fun VerificationCard(item: VerificationItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(primaryBlue)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item.title, color = Color.White)

                StatusChip(
                    text = item.status,
                    color = item.statusColor
                )
            }

            Text(item.student, color = grey, fontSize = 12.sp)
            Text(item.date, color = grey, fontSize = 12.sp)
        }
    }
}

/* -------------------- STATUS CHIP -------------------- */

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
