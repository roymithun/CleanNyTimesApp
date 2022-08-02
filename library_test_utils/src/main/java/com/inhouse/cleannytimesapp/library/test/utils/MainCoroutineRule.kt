@file:OptIn(ExperimentalCoroutinesApi::class)

package com.inhouse.cleannytimesapp.library.test.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Add this JUnit 5 extension to your test class using
 * @JvmField
 * @RegisterExtension
 * val coroutinesTestExtension = CoroutinesTestExtension()
 */

/*
* A JUnit Test Extension that swaps the coroutine dispatcher one which executes each task synchronously.
* You can use this rule for your host side tests that use Kotlin Coroutines.
*/
class MainCoroutineRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

/**
 * Creates a new [CoroutineScope] with the rule's testDispatcher
 */
fun MainCoroutineRule.CoroutineScope(): CoroutineScope = TestScope(testDispatcher)
