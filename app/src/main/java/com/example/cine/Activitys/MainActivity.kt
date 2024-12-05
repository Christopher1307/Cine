package com.example.cine.Activitys

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import coil.compose.AsyncImage
import com.example.cine.R
import com.example.cine.api.AuthInterceptor
import com.example.cine.api.Movie
import com.example.cine.api.MovieApiService
import com.example.cine.api.MovieResponse
import com.example.cine.ui.theme.CineTheme
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("dark_theme", false)
        setTheme(isDarkTheme)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieApiService = retrofit.create(MovieApiService::class.java)

        // Estado para almacenar las películas
        val movies = mutableStateOf<List<Movie>>(emptyList())

        // Llamada a la API
        movieApiService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movies.value = response.body()?.results ?: emptyList()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API", "Error: ${t.message}", t)
                Toast.makeText(this@MainActivity, "Failed to load movies", Toast.LENGTH_SHORT).show()
            }
        })

        // Configuración de la pantalla principal
        setContent {
            CineTheme {
                MainScreen(movies = movies.value)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_light_theme -> {
                setTheme(false)
                Toast.makeText(this, "Light Theme selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_dark_theme -> {
                setTheme(true)
                Toast.makeText(this, "Dark Theme selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_light_theme -> setTheme(false)
            R.id.nav_dark_theme -> setTheme(true)
            R.id.nav_exit -> finish() // Salir de la aplicación
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setTheme(isDark: Boolean) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        sharedPreferences.edit().putBoolean("dark_theme", isDark).apply()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(movies: List<Movie>) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    val openAboutDialog = remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("App Info", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text("User Preferences") },
                    selected = false,
                    onClick = {
                        openDialog.value = true
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("About") },
                    selected = false,
                    onClick = {
                        openAboutDialog.value = true
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Exit") },
                    selected = false,
                    onClick = { /* Handle exit */ }
                )
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Cine") },
                    actions = {
                        IconButton(onClick = { /* Handle profile click */ }) {
                            Icon(Icons.Default.Person, contentDescription = "Profile")
                        }
                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show drawer") },
                    icon = { Icon(Icons.Filled.Menu, contentDescription = null) },
                    onClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }
                )
            }
        ) { contentPadding ->
            MovieList(movies = movies, modifier = Modifier.padding(contentPadding))
        }
    }

    // Diálogo de selección de tema
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text("User Preferences") },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Diálogo de información
    if (openAboutDialog.value) {
        AlertDialog(
            onDismissRequest = { openAboutDialog.value = false },
            title = { Text("About") },
            text = { Text("App Name: Cine\nVersion: 1.0\nDevelopers: Your Name") },
            confirmButton = {
                TextButton(onClick = { openAboutDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("movie", movie)
                }
                context.startActivity(intent)
            }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f)
        )
    }
}