package es.queryinformatica.clean_architecture

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class DispatcherTestRule : TestRule {

    @ExperimentalCoroutinesApi
    val testDispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    override fun apply(base: Statement, description: Description?): Statement {
        try {
            Dispatchers.setMain(testDispatcher)
            base.evaluate()
        } catch (e: Exception) {
        } finally {
            Dispatchers.resetMain()
            //testDispatcher.cleanupTestCoroutines()
        }
        return base
    }

}