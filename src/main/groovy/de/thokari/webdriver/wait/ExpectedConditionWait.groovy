package de.thokari.webdriver.wait

import org.openqa.selenium.TimeoutException
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import de.thokari.webdriver.GroovyWebDriver

class ExpectedConditionWait {

	@Delegate
	private GroovyWebDriver driver
	private Long remainingSeconds
	private Closure errorHandler

	public ExpectedConditionWait(GroovyWebDriver driver, Long seconds) {
		this.driver = driver
		this.remainingSeconds = seconds
	}

	def until(Closure condition, Closure errorHandler = null) {
		this.errorHandler = errorHandler
		condition.resolveStrategy = Closure.DELEGATE_FIRST
		condition.delegate = this
		condition.call()
	}

	private def doWait(ExpectedCondition condition) {
		def start = now()
		def wait = new WebDriverWait(driver, remainingSeconds)
		def result
		try {
			result = wait.until condition
		} catch (TimeoutException toe) {
			if(errorHandler) {
				errorHandler.call toe
			}
		}
		def duration = ( now() - start ) / 1000
		remainingSeconds -= duration as Long
		result
	}

	def methodMissing(String name, args) {
		ExpectedCondition condition = ExpectedConditions.invokeMethod name, args
		doWait condition
	}

	def now = { System.currentTimeMillis() }
}
