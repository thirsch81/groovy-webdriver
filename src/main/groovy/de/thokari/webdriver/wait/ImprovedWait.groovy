package de.thokari.webdriver.wait

import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions

import de.thokari.webdriver.GroovyWebDriver

class ImprovedWait {

	private GroovyWebDriver driver

	public ImprovedWait(GroovyWebDriver driver) {
		this.driver = driver
	}

	/**
	 * Generic wait method. Needs a string describing what is expected,<br/>
	 * e.g. "title to contain some text".<br/>
	 * Only single statements (i.e. the last one in the closure) are evaluated.
	 * 
	 * @param seconds (optional) max timeout, defaults to 10
	 * @param expectation
	 * @param condition as a closure containing a single statement
	 *
	 */
	public void waitFor(Long seconds = 10, String expectation, Closure condition, Closure errorHandler = null) {
		new CustomConditionWait(driver, seconds, expectation).until condition, errorHandler
	}

	/**
	 * Wait using standard Selenium ExpectedConditions.<br/>
	 * You can provide multiple statements, which will be evaluated in order.
	 * 
	 * @param seconds (optional) max timeout, defaults to 10
	 * @param condition a closure containing calls to ExpectedConditions
	 * @return whatever the (last) condition returns, e.g. a WebElement
	 * @see org.openqa.selenium.support.ui.ExpectedConditions
	 * 
	 */
	public def waitFor(Long seconds = 10, Closure condition, Closure errorHandler = null) {
		new ExpectedConditionWait(driver, seconds).until condition, errorHandler
	}

	/**
	 * Evaluates an ExpectedCondition.
	 * 
	 * @param expectation a closure containing a call to ExpectedConditions
	 * @return whatever the condition returns, e.g. Boolean or WebElement
	 */
	def expect(Closure expectation) {
		expectation.delegate = new ExpectedConditions()
		ExpectedCondition expectedCondition = expectation.call()
		expectedCondition.apply(driver)
	}
}
