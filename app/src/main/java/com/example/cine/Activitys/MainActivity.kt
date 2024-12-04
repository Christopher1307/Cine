package com.example.cine.Activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cine.api.AuthInterceptor
import com.example.cine.api.Movie
import com.example.cine.api.MovieApiService
import com.example.cine.api.MovieResponse
import com.example.cine.ui.theme.CineTheme
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    movies.value = (response.body()?.results ?: emptyList()) as List<Movie>
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
            MovieItem(movie = movie) {
                // Acción al hacer clic en un elemento
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: (Movie) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(movie) }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(movie.title)
            Text(movie.overview)
        }
    }
}
