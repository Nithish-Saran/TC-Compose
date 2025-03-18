package com.apps.tc.tccompose2025

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps.tc.tccompose2025.dialog.BabyNameDialog
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.setStatusBarColor(R.color.status_bar)
        setContent {
            ComposeTamilCalendar2025Theme {
                val navController = rememberNavController()
                val currentDestination by navController.currentBackStackEntryAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ){
                    VirtualPooja()
                    /*NavHost(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = ""
                    ) {

                    }*/
                }
            }
        }
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
    ComposeTamilCalendar2025Theme {
        Greeting("Android")
    }
}