package com.pemmob.smartspend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pemmob.smartspend.ui.navigation.NavGraph
import com.pemmob.smartspend.ui.theme.SmartSpendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartSpendTheme {
                NavGraph()
            }
        }
    }
}
