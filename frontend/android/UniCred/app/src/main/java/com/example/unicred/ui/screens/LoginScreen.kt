package com.example.unicred.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unicred.ui.theme.bgBlue
import com.example.unicred.ui.theme.blue
import com.example.unicred.ui.theme.green
import com.example.unicred.ui.theme.grey
import com.example.unicred.ui.theme.primaryBlue

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Student") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBlue)
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "UniCred",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Student Credential Platform",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = primaryBlue,
                unfocusedContainerColor = primaryBlue,
                focusedBorderColor = primaryBlue,
                unfocusedBorderColor = primaryBlue,
                focusedLabelColor = grey,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),

        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = primaryBlue,
                unfocusedContainerColor = primaryBlue,
                focusedBorderColor = primaryBlue,
                unfocusedBorderColor = primaryBlue,
                focusedLabelColor = grey,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Access Type:",
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { selectedRole = "Student" },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRole == "Student") blue else primaryBlue
                ),
                border = if (selectedRole == "Student") null else BorderStroke(0.5.dp, grey)
            ) {
                Text("Student",
                    fontSize = 10.sp,
                    color = if(selectedRole == "Student") Color.White else grey
                )
            }

            Button(
                onClick = { selectedRole = "Recruiter" },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRole == "Recruiter") blue else primaryBlue
                ),
                border = if (selectedRole == "Recruiter") null else BorderStroke(0.5.dp, grey)
            ) {
                Text("Recruiter",
                    fontSize = 10.sp,
                    color = if(selectedRole == "Recruiter") Color.White else grey
                    )
            }

            Button(
                onClick = { selectedRole = "University" },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRole == "University") blue else bgBlue
                ),
                border = if (selectedRole == "University") null else BorderStroke(0.5.dp, grey)
            ) {
                Text("University",
                    fontSize = 10.sp,
                    color = if(selectedRole == "University") Color.White else grey
                    )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // authentication will live here later
                //This is Important We are not handling any navigation logic here.
                // that is 'outsourced' to the UniCredRoot function.
                /* we are passing the selectedRole state variable to the onLoginSuccess parameter
                    which when received by the login composable root takes us to our desired interface.
                 */
                onLoginSuccess(selectedRole)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = blue)
        ) {
            Text("Login", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { /* create account */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, green)
        ) {
            Text("Create Account", color = green)
        }
    }
}


