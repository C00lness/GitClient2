package com.example.gitclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.NavRoutes
import com.example.gitclient.screen.RepositoryDetailsScreen

import com.example.gitclient.screen.SearchResultScreen
import com.example.gitclient.ui.theme.GitClientTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GitClientTheme {
            val navController = rememberNavController()
            NavHost(navController, startDestination = NavRoutes.Search.route) {
                composable(NavRoutes.Search.route) {
                    SearchResultScreen(viewModel) {
                            viewModel.getContent(viewModel.url)
                            navController.navigate(NavRoutes.RepositoryDetails.route)
                    }
                }
                composable(NavRoutes.RepositoryDetails.route) { stackEntry ->
                    RepositoryDetailsScreen(viewModel)
                    {
                        val url: String = viewModel.url.substringBeforeLast("/")
                        if (url.indexOf("contents") == -1)
                            navController.popBackStack()
                        else {
                            viewModel.url = url
                            viewModel.getContent(viewModel.url)
                        }
                    }
                }
            }
        }
        }
    }
}




