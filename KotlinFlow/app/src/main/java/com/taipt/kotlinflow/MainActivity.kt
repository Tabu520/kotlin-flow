package com.taipt.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taipt.kotlinflow.ui.theme.KotlinFlowTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.stateFlow.collectLatest { number ->
//                binding.tvCounter.text = number.toString()
//                }
//            }
//        }
        collectLatestLifecycleFlow(viewModel.stateFlow) { number ->
//            binding.tvCounter.text = number.toString()
        }

        setContent {
            KotlinFlowTheme {
                val viewModel = viewModel<MainViewModel>()
                val time = viewModel.countDownFlow.collectAsState(initial = 10)
                val count = viewModel.stateFlow.collectAsState(0)

                LaunchedEffect(key1 = true) {
                    viewModel.sharedFlow.collect { number ->

                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
//                    Text(
//                        text = time.value.toString(),
//                        fontSize = 32.sp,
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                    )
                    Button(onClick = { 
                        viewModel.incrementCounter()
                    }) {
                        Text(text = "Counter: ${count.value}")
                    }
                }
            }
        }
    }
}

fun <T> ComponentActivity.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> ComponentActivity.collectLifecycleFlow(flow: Flow<T>, collect: FlowCollector<T>) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}
