package io.github.alexkamanin.scarlet.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule : TestRule {

	private val testDispatcher = UnconfinedTestDispatcher()

	override fun apply(base: Statement, description: Description): Statement =
		object : Statement() {

			override fun evaluate() {
				Dispatchers.setMain(testDispatcher)

				try {
					base.evaluate()
				} finally {
					Dispatchers.resetMain()
				}
			}
		}
}