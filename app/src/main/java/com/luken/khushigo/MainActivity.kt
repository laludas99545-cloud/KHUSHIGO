      package com.luken.khushigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFFD4AF37),
                    secondary = Color(0xFF1E232A)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF4F5F7)
                ) {
                    KhushigoApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhushigoApp() {
    var selectedRole by remember { mutableStateOf("Customer") }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.`1789997871800`),
                            contentDescription = "KHUSHIGO Logo",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Column {
                            Text(
                                text = "KHUSHIGO",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFB8860B)
                            )
                            Text(
                                text = "By LUKEN • Food & Delivery Portal",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color(0xFF1E232A))
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
            // Role Switcher Tabs (Customer, Rider, Merchant)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Customer", "Rider", "Merchant").forEach { role ->
                        val isSelected = selectedRole == role
                        Button(
                            onClick = { selectedRole = role },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFF1E232A) else Color.Transparent,
                                contentColor = if (isSelected) Color(0xFFD4AF37) else Color.DarkGray
                            ),
                            elevation = null,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(role, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            when (selectedRole) {
                "Customer" -> CustomerPortalView(searchText) { searchText = it }
                "Rider" -> RiderPortalView()
                "Merchant" -> MerchantPortalView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerPortalView(searchText: String, onSearchChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        placeholder = { Text("Search dishes, restaurants...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )

    Card(
        modifier = Modifier.fillMaxWidth().height(120.dp).padding(bottom = 20.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E232A))
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Golden Offer 🌟", color = Color(0xFFD4AF37), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("50% OFF On First Order", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Use Code: KHUSHI50", color = Color.LightGray, fontSize = 11.sp)
            }
            Icon(Icons.Default.Fastfood, contentDescription = null, tint = Color(0xFFD4AF37), modifier = Modifier.size(48.dp))
        }
    }

    Text("Explore Categories", fontWeight = FontWeight.Bold, fontSize = 17.sp, modifier = Modifier.padding(bottom = 12.dp))
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }

    Text("Popular Restaurants", fontWeight = FontWeight.Bold, fontSize = 17.sp, modifier = Modifier.padding(bottom = 12.dp))
    RestaurantItem("Royal Biryani Palace", "4.8 ★", "20-25 mins", "Biryani, North Indian")
    Spacer(modifier = Modifier.height(10.dp))
    RestaurantItem("Pizza Express & Grill", "4.5 ★", "25-30 mins", "Pizzas, Italian, Fast Food")
}

@Composable
fun RiderPortalView() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TwoWheeler, contentDescription = null, tint = Color(0xFFB8860B), modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Rider Delivery Dashboard", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Status: Active & Online 🟢", fontSize = 12.sp, color = Color.Gray)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
            Text("Assigned Orders (1 Active)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Order #KH-9842", fontWeight = FontWeight.Bold)
                    Text("Pickup: Royal Biryani Palace", fontSize = 12.sp, color = Color.Gray)
                    Text("Drop: MG Road, Block B", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E232A)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Accept Delivery", color = Color(0xFFD4AF37))
                    }
                }
            }
        }
    }
}

@Composable
fun MerchantPortalView() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Storefront, contentDescription = null, tint = Color(0xFFB8860B), modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Merchant Restaurant Control", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Store: Open for orders 🟢", fontSize = 12.sp, color = Color.Gray)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Today Orders: 14", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Revenue: ₹4,520", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E232A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Manage Menu & Prices", color = Color(0xFFD4AF37))
            }
        }
    }
}

@Composable
fun RestaurantItem(name: String, rating: String, time: String, tags: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(tags, fontSize = 12.sp, color = Color.Gray)
                Text("Fast Delivery • Free Shipping", fontSize = 11.sp, color = Color(0xFFB8860B))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(rating, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 13.sp)
                Text(time, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}
                         
