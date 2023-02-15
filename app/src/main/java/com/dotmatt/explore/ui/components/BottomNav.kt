package com.dotmatt.explore.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNav(navController: NavController, currentRoute: String?) {
    Surface(elevation = 8.dp) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            NavItem(Icons.Rounded.Home,"Home", (currentRoute == "home")) {
                navController.navigate("home")
            }
            NavItem(Icons.Rounded.LocationOn,"Map", (currentRoute == "map")) {
                navController.navigate("map")
            }
            NavItem(Icons.Rounded.Settings,"Settings", (currentRoute == "settings")) {
                navController.navigate("settings")
            }
        }
    }
}

@Composable
fun NavItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xffe5e5ea) else Color.Transparent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick
            )
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(50))
    ) {
        Icon(icon, label, tint = Color.DarkGray)
        AnimatedVisibility(visible = isSelected) {
            Text(label, color = Color.DarkGray, modifier = Modifier.padding(start = 8.dp))
        }
    }
}