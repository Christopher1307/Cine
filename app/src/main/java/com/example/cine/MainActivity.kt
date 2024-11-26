package com.example.cine


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.cine.ui.theme.CineTheme
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.navigation.NavigationView

class MainActivity : ComponentActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.main)
        navigationView = findViewById(R.id.navigation_view)

        // Handle menu selections
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Action when "Home" is selected
                    showToast(getString(R.string.op_home_toast))
                }
                R.id.nav_profile -> {
                    // Action when "Profile" is selected
                    showToast(getString(R.string.op_profile_toast))
                }
                R.id.nav_settings -> {
                    // Action when "Settings" is selected
                    showToast(getString(R.string.op_settings_toast))
                }
            }
            // Close the drawer after selecting an option
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CineTheme {
        Greeting("Android")
    }
}