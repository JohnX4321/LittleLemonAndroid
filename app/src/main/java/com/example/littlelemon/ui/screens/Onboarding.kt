package com.example.littlelemon.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.models.UserData
import com.example.littlelemon.ui.events.OnboardingEvent
import com.example.littlelemon.utils.Home
import com.example.littlelemon.vm.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState(initial = UserData())

    Column {
        Column(modifier = Modifier.weight(1f)) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 20.dp, top = 20.dp)
                .align(CenterHorizontally))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp)
                    .background(colorResource(id = R.color.littlelemon_green))
            ) {
                Text(
                    text = stringResource(id = R.string.hero_text),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Text(text = "Personal Info",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 10.dp, top = 60.dp, bottom = 60.dp)
                .align(Alignment.Start))
            OutlinedTextField(value = state.value.firstname, onValueChange = {viewModel.onEvent(OnboardingEvent.OnFirstNameChanged(it))},
            label = { Text(text = "First Name")},
            placeholder = { Text(text = "First Name")},
            shape = RoundedCornerShape(25),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(CenterHorizontally)
            )
            OutlinedTextField(
                value = state.value.lastName,
                onValueChange = { viewModel.onEvent(OnboardingEvent.OnLastNameChanged(it)) },
                label = { Text(text = "Last Name")},
                placeholder = { Text(text = "Last Name")},
                shape = RoundedCornerShape(25),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(CenterHorizontally)
                )
            OutlinedTextField(value = state.value.email, onValueChange = {viewModel.onEvent(OnboardingEvent.OnEmailChanged(it))},
            label = { Text(text = "Email")},
                placeholder = { Text(text = "Email")},
                shape = RoundedCornerShape(25),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(CenterHorizontally)
                )
        }
        Button(onClick = {
            if (!viewModel.isInputDataValid()) {
                Toast.makeText(context,"Registration unsuccessful. Please enter valid data",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Registration successful!",Toast.LENGTH_SHORT).show()
                viewModel.onEvent(OnboardingEvent.OnSabeUserData)
                navController.navigate(Home.route)
            }
        },
        border = BorderStroke(0.5.dp, Color.Red),
            shape = RoundedCornerShape(25),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ) {
            Text(text = "Register", color = Color.Black)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {

}