package com.mrmansur.demodeclarative.activity.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mrmansur.demodeclarative.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    show_id : String,
    show_name : String,
    show_type : String
){
    val viewModel = hiltViewModel<DetailsViewModel>()
    viewModel.apiTVShowDetails(show_id.toInt())

    DetailsScreenContent(viewModel, show_id, show_name, show_type)
}

@Composable
fun DetailsScreenContent(
    viewModel: DetailsViewModel,
    showId: String,
    showName: String,
    showType: String
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val tvShowDetails by viewModel.tvShowDetails.observeAsState()

    Scaffold(
        backgroundColor = Color.Black,
    ) {
        if (isLoading){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }else{
            
        }
    }

}
