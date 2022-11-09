package com.jangjh123.shallwegoforawalk.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

object CoroutineScopes {
    fun io(codeBlock: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(IO).launch {
            codeBlock()
    }

    fun main(codeBlock: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Main).launch {
            codeBlock()
    }

    fun default(codeBlock: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Default).launch {
            codeBlock()
    }
}