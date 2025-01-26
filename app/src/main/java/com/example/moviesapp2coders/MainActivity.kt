package com.example.moviesapp2coders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.example.moviesapp2coders.presentation.components.BottomBar
import com.example.moviesapp2coders.presentation.screens.NavGraphs
import com.example.moviesapp2coders.ui.theme.MoviesApp2CodersTheme
import com.example.moviesapp2coders.util.LocalCurrentNavController
import com.example.moviesapp2coders.util.LocalSnackBarHostState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesApp2CodersTheme {
                //UI navigation container setup with use of ram costas library based on compose navigation
                //Bottom bar and navigation setup
                //Local provider navController
                val navController = rememberNavController()
                val snackBarHostState = remember {
                    SnackbarHostState()
                }
                val bottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    skipHalfExpanded = true
                )
                val bottomSheetNavigator = remember {
                    BottomSheetNavigator(sheetState = bottomSheetState)
                }
                navController.navigatorProvider += bottomSheetNavigator
                CompositionLocalProvider(
                    LocalCurrentNavController provides navController,
                    LocalSnackBarHostState provides snackBarHostState
                ) {
                    MoviesApp2CodersTheme {
                        ModalBottomSheetLayout(
                            bottomSheetNavigator = bottomSheetNavigator,
                            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                        ) {
                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                snackbarHost = { SnackbarHost(snackBarHostState) },
                                bottomBar = {
                                    BottomBar(controller = navController)
                                }
                            ) { innerPadding ->
                                DestinationsNavHost(
                                    modifier = Modifier.padding(innerPadding),
                                    navGraph = NavGraphs.root,
                                    navController = navController,
                                    engine = rememberAnimatedNavHostEngine()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
