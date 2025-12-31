package com.example.unicred.ui.screens.university

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unicred.R
import com.example.unicred.ui.screens.PopupDropdownField
import com.example.unicred.ui.screens.PopupTextField
import com.example.unicred.ui.screens.UniversityPopup
import com.example.unicred.ui.theme.*

/* ----------------------------- ENTRY POINT ----------------------------- */

@Composable
fun StudentDirectory(
    navController: NavController
) {
    var query by remember { mutableStateOf("") }

    var showAddStudentPopup by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("Computer Science") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }


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

            item { StudentDirectoryHeader() }

            item { StudentStatsGrid() }

            item {
                StudentSearchAndFilter(
                    query = query,
                    onQueryChange = { query = it }
                )
            }

            item { AddStudentButton(onClick = { showAddStudentPopup = true }) }

            item { StudentsListSection() }
        }

        if (showAddStudentPopup) {



            UniversityPopup(
                title = "Add New Student",
                confirmText = "Add Student",
                onDismiss = { showAddStudentPopup = false },
                onConfirm = {
                    // TODO: ViewModel call later
                    showAddStudentPopup = false
                }
            ) {
                PopupTextField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = "Enter full name",
                    required = true
                )

                PopupTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter email address",
                    required = true
                )

                PopupTextField(
                    label = "Student ID",
                    value = studentId,
                    onValueChange = { studentId = it },
                    placeholder = "Enter student ID",
                    required = true
                )

                PopupDropdownField(
                    label = "Program",
                    options = listOf(
                        "Computer Science",
                        "Electronics",
                        "Mechanical",
                        "Civil"
                    ),
                    selected = program,
                    onSelect = { program = it },
                    required = true
                )

                PopupTextField(
                    label = "Phone Number",
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = "Enter phone number"
                )

                PopupTextField(
                    label = "Address",
                    value = address,
                    onValueChange = { address = it },
                    placeholder = "Enter address"
                )
            }
        }

    }
}

/* ----------------------------- HEADER ----------------------------- */

@Composable
private fun StudentDirectoryHeader() {
    Column {
        Text("Student Directory", fontSize = 18.sp, color = Color.White)
        Spacer(Modifier.height(12.dp))
        Text("Student Directory", fontSize = 22.sp, color = Color.White)
    }
}

/* ----------------------------- STATS ----------------------------- */


@Composable
private fun StudentStatsGrid() {
    val stats = listOf(
        SimpleStatItem("Total Students", "11"),
        SimpleStatItem("Active", "11"),
        SimpleStatItem("Graduated", "0"),
        SimpleStatItem("Suspended", "0")
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
            Text(stat.title, color = grey, fontSize = 13.sp)
        }
    }
}

/* ----------------------------- SEARCH ----------------------------- */

@Composable
private fun StudentSearchAndFilter(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryBlue)
                .border(0.5.dp, grey, RoundedCornerShape(12.dp))
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

                Spacer(Modifier.width(8.dp))

                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text("Search students...", color = grey)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        IconButton(
            onClick = { },
            modifier = Modifier
                .size(56.dp)
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

/* ----------------------------- ADD STUDENT ----------------------------- */

@Composable
private fun AddStudentButton(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(secondaryBlue)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.people),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(Modifier.width(8.dp))
            Text("Add New Student", color = Color.White)
        }
    }
}

/* ----------------------------- STUDENT LIST ----------------------------- */

data class Student(
    val name: String,
    val email: String,
    val id: String,
    val program: String,
    val status: String
)

@Composable
private fun StudentsListSection() {
    val students = listOf(
        Student(
            name = "Debarghaya Mitra",
            email = "debarghayamitra0@gmail.com",
            id = "STU008",
            program = "Computer Science",
            status = "Active"
        ),
        Student(
            name = "Anna Garcia",
            email = "anna.garcia@example.edu",
            id = "STU009",
            program = "Data Science",
            status = "Active"
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Students (${students.size})", fontSize = 18.sp, color = Color.White)

        students.forEach {
            StudentCard(it)
        }
    }
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
private fun StudentCard(student: Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(primaryBlue)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleAvatar(student.name)

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(student.name, color = Color.White)
                    Text(student.email, color = grey, fontSize = 12.sp)
                    Text("ID: ${student.id}", color = grey, fontSize = 12.sp)
                    Text(student.program, color = grey, fontSize = 12.sp)
                }

                StatusChip(
                    text = student.status,
                    color = green
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = grey.copy(alpha = 0.3f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StudentAction(R.drawable.view, "View Details")
                StudentAction(R.drawable.docs, "Credentials")
                StudentAction(R.drawable.email, "Contact")
                StudentAction(R.drawable.settings, "Settings")
            }
        }
    }
}

/* ----------------------------- COMPONENTS ----------------------------- */

@Composable
private fun CircleAvatar(name: String) {
    val initial = name.first().uppercase()

    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(50))
            .background(secondaryBlue),
        contentAlignment = Alignment.Center
    ) {
        Text(initial, color = Color.White, fontSize = 18.sp)
    }
}

@Composable
private fun StudentAction(
    icon: Int,
    label: String
) {
    Row(
        modifier = Modifier.clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = secondaryBlue,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(label, color = secondaryBlue, fontSize = 12.sp)
    }
}
