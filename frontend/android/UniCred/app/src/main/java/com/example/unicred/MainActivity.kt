package com.example.unicred

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unicred.ui.screens.LoginScreen
import com.example.unicred.ui.screens.recruiter.RecruiterDashboard
import com.example.unicred.ui.screens.student.StudentDashboard
import com.example.unicred.ui.screens.university.UniversityDashboard
import com.example.unicred.ui.theme.UniCredTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UniCredTheme {
                UniCredRoot()
            }
        }
    }
}

/*
This composable root makes sure that the navigation happens one way after selection is made between
Student,Recruiter and University.
*/
@Composable
private fun UniCredRoot() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            // Based on what state variable we received in the onLoginSuccess variable we will be take accordingly to selected screens.
            LoginScreen { role ->
                val destination = when (role) {
                    "Student" -> "student_dashboard"
                    "Recruiter" -> "recruiter_dashboard"
                    "University" -> "university_dashboard"
                    else -> "login"
                }
                //This line makes sure that there is no going back after the user has logged in.
                navController.navigate(destination) {
                    popUpTo("login") { inclusive = true }
                }
            }
        }


        composable("student_dashboard") {
            StudentDashboard()
        }

        composable("university_dashboard") {
            UniversityDashboard()
        }

        composable("recruiter_dashboard") {
            RecruiterDashboard()
        }
    }
}
