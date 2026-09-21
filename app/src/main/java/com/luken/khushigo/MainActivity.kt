   package com.luken.khushigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFFFF6F00),
                    secondary = Color(0xFF673AB7)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF8F9FA)
                ) {
                    KhushigoHomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhushigoHomeScreen() {
    var selectedRole by remember { mutableStateOf("Customer") }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "KHUSHIGO 🍕",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6F00)
                        )
                        Text(
                            text = "Powered by LUKEN • Delivering Happiness",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Role Selector (Customer / Rider / Merchant)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Customer", "Rider", "Merchant").forEach { role ->
                        Button(
                            onClick = { selectedRole = role },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedRole == role) Color(0xFF673AB7) else Color.Transparent,
                                contentColor = if (selectedRole == role) Color.White else Color.Black
                            ),
                            elevation = null,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(role, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            if (selectedRole == "Customer") {
                // Search Bar
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Search pizza, burger, biryani...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                // Offer Banner
                Card(
                    modifier = Modifier.fillMaxWidth().height(110.dp).padding(bottom = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF6F00))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("50% OFF On First Order", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Use Code: KHUSHI50", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                        }
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                }

                // Food Categories
                Text("Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
                val categories = listOf("Pizza 🍕", "Burger 🍔", "Biryani 🍲", "Drinks 🥤", "Dessert 🍰")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    items(categories) { item ->
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Popular Restaurants List
                Text("Popular Near You", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
                RestaurantCard(name = "Royal Biryani House", rating = "4.5 ★", time = "25 mins")
                Spacer(modifier = Modifier.height(10.dp))
                RestaurantCard(name = "Pizza Express", rating = "4.2 ★", time = "30 mins")

            } else {
                // Portal View for Rider / Merchant
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = if (selectedRole == "Rider") Icons.Default.Place else Icons.Default.Home,
                            contentDescription = null,
                            tint = Color(0xFFFF6F00),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Welcome to $selectedRole Portal", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            if (selectedRole == "Rider") "View assigned delivery orders and route details."
                            else "Manage restaurant menu, orders, and sales dashboard.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(name: String, rating: String, time: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Fast Delivery • Free Shipping", fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(rating, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                Text(time, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
             
