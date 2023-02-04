package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val landmark = viewModel.preferredLandmark.collectAsState()
    val unitOfMeasurement = viewModel.unitOfMeasurement.collectAsState()

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Settings",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            "PREFERRED LANDMARKS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        CheckboxSetting(
            label = "All",
            checked = (landmark.value == "all"),
            onClick = { viewModel.landmarkChange("all") })
        CheckboxSetting(
            label = "Natural",
            checked = (landmark.value == "natural"),
            onClick = { viewModel.landmarkChange("natural") })
        CheckboxSetting(
            label = "Historic",
            checked = (landmark.value == "historic"),
            onClick = { viewModel.landmarkChange("historic") })

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            "UNIT OF MEASUREMENT",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        CheckboxSetting(
            label = "Metric",
            checked = (unitOfMeasurement.value == "metric"),
            onClick = { viewModel.measurementChange("metric") })
        CheckboxSetting(
            label = "Imperial",
            checked = (unitOfMeasurement.value == "imperial"),
            onClick = { viewModel.measurementChange("imperial") })

        Spacer(modifier = Modifier.size(48.dp))
        LogoutButton(navController, viewModel)
    }
}

@Composable
fun CheckboxSetting(label: String, checked: Boolean, onClick: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label)
        Checkbox(
            checked = checked,
            onCheckedChange = onClick,
            colors = CheckboxDefaults.colors(checkedColor = Color.Blue, uncheckedColor = Color.LightGray)
        )
    }
}

@Composable
fun LogoutButton(navController: NavController, viewModel: SettingsViewModel) {
    Button(onClick = {
        viewModel.handleLogout {
            navController.navigate("login") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }, shape = RoundedCornerShape(50.dp), modifier = Modifier.fillMaxWidth()) {
        Text(text = "Logout")
    }
}