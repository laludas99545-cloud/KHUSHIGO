                        package com.example.khushigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF00796B),
                    secondary = Color(0xFFF57C00),
                    background = Color(0xFFF8F9FA)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KhushigoMainRoot()
                }
            }
        }
    }
}

@Composable
fun KhushigoMainRoot() {
    var currentScreen by remember { mutableStateOf("splash") }
    var selectedRole by remember { mutableStateOf("") }
    var isAdminLoggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(currentScreen) {
        if (currentScreen == "splash") {
            delay(2500)
            currentScreen = "role_gateway"
        }
    }

    when (currentScreen) {
        "splash" -> SplashScreen(onLogoTapped = { currentScreen = "admin_login" })
        "role_gateway" -> RoleGatewayScreen(
            onRoleSelected = { role ->
                selectedRole = role
                currentScreen = "workspace"
            },
            onSecretAdminTrigger = { currentScreen = "admin_login" }
        )
        "admin_login" -> AdminLoginScreen(
            onLoginSuccess = {
                isAdminLoggedIn = true
                currentScreen = "admin_dashboard"
            },
            onBack = { currentScreen = "role_gateway" }
        )
        "admin_dashboard" -> AdminDashboardScreen(onLogout = { currentScreen = "role_gateway" })
        "workspace" -> WorkspaceScreen(role = selectedRole, onBack = { currentScreen = "role_gateway" })
    }
}

@Composable
fun SplashScreen(onLogoTapped: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00796B))
            .clickable { onLogoTapped() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalShipping,
                    contentDescription = "Logo",
                    tint = Color(0xFFF57C00),
                    modifier = Modifier.size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "KHUSHIGO", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "Your Daily Super App", color = Color(0xFFB2DFDB), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "A Product of LUKEN", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun RoleGatewayScreen(onRoleSelected: (String) -> Unit, onSecretAdminTrigger: () -> Unit) {
    var termsAccepted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "KHUSHIGO", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00796B))
        Text(text = "Powered by LUKEN", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.clickable { onSecretAdminTrigger() })
        Spacer(modifier = Modifier.height(40.dp))

        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = termsAccepted, onCheckedChange = { termsAccepted = it })
                Text(text = "I accept Partner Terms & Conditions and Safety Policies.", fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        RoleButton(title = "Continue as Customer", enabled = termsAccepted, onClick = { onRoleSelected("Customer") })
        Spacer(modifier = Modifier.height(12.dp))
        RoleButton(title = "Login as Rider", enabled = termsAccepted, onClick = { onRoleSelected("Rider") })
        Spacer(modifier = Modifier.height(12.dp))
        RoleButton(title = "Login as Merchant", enabled = termsAccepted, onClick = { onRoleSelected("Merchant") })
    }
}

@Composable
fun RoleButton(title: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun WorkspaceScreen(role: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$role Portal - KHUSHIGO") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00796B), titleContentColor = Color.White, navigationIconContentColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            when (role) {
                "Customer" -> CustomerPortalView()
                "Rider" -> RiderPortalView()
                "Merchant" -> MerchantPortalView()
            }
        }
    }
}

@Composable
fun CustomerPortalView() {
    Column {
        Text("Explore Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CategoryChip("Food 🍲")
            CategoryChip("Grocery 🛒")
            CategoryChip("Meat & Fish 🐟")
            CategoryChip("Bike Taxi 🏍️")
        }
    }
}

@Composable
fun CategoryChip(title: String) {
    Surface(shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp) {
        Text(text = title, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun RiderPortalView() {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Rider Status: Online 🟢", fontWeight = FontWeight.Bold, color = Color(0xFF00796B))
            Text("Wallet Balance: ₹650 (Min ₹500 required)", fontSize = 12.sp)
        }
    }
}

@Composable
fun MerchantPortalView() {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Merchant Dashboard", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Prep Time Cap: Max 20 mins (Fast Food)", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun AdminLoginScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var pin by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Secure Admin Gateway", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00796B))
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = pin,
            onValueChange = { if (it.length <= 6) pin = it },
            label = { Text("Enter 6-Digit Admin PIN") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { if (pin.length == 6) onLoginSuccess() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))) {
            Text("Unlock Dashboard")
        }
        TextButton(onClick = onBack) { Text("Back to Gateway") }
    }
}

@Composable
fun AdminDashboardScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Control Center") },
                actions = { IconButton(onClick = onLogout) { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00796B), titleContentColor = Color.White)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("System Analytics & Oversight", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}
