package com.example.moviesapp2coders

import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutineTestExtension::class)
open class AppBaseTest : CoroutineTest {

    override lateinit var testCoroutineProvider: CoroutineProvider
    override lateinit var testScope: TestScope

    val mockContext = mockk<Context>(relaxed = true)
}
