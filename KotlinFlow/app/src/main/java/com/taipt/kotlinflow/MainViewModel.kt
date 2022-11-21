package com.taipt.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {

//        val flow1 = flow {
//            emit(1)
//            delay(500L)
//            emit(2)
//        }

        val flow1 = (1..5).asFlow()

        val eatingFlow = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main dish")
            delay(100L)
            emit("Dessert")
        }

        viewModelScope.launch {
//            val count = countDownFlow
//                .filter { time ->
//                    time % 2 == 0
//                }
//                .map { time ->
//                    time * time
//                }
//                .onEach { time ->
//                    println(time)
//                }
//                .count {
//                    it % 2 == 0
//                }
//            println("The count is $count")


//            val reduceResult = countDownFlow
//                .reduce { accumulator, value ->
//                .fold(100) { accumulator, value ->
//                    accumulator + value
//                }
//            println("Sum = $reduceResult")

//            flow1.flatMapConcat { value ->
//                flow {
//                    emit(value + 1)
//                    delay(500L)
//                    emit(value + 2)
//                }
//            }.collect { value ->
//                println("The current value is $value")
//            }

            eatingFlow.onEach {
                println("FLOW: $it is delivered")
            }.collectLatest {
                println("FLOW: Now eating $it")
                delay(1500L)
                println("FLOW: Finished eating $it")
            }
        }
    }
}