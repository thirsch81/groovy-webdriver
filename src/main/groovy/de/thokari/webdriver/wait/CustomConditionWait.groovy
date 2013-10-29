package de.thokari.webdriver.wait

import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import de.thokari.webdriver.GroovyWebDriver

class CustomConditionWait {

	private GroovyWebDriver driver
	private Long timeout
	private String expectation

	public CustomConditionWait(GroovyWebDriver driver, Long seconds, String expectation) {
		this.driver = driver
		this.timeout = seconds
		this.expectation = expectation
	}

	def until(Closure condition, Closure errorHandler = null) {
		condition.resolveStrategy = Closure.DELEGATE_ONLY
		condition.delegate = driver
		try {
			def wait = new WebDriverWait(driver, timeout)
			wait.until closureToBooleanCondition(condition)
		} catch(TimeoutException toe) {
			def newException = new TimeoutException("Timed out after $timeout seconds waiting for $expectation")
			if(errorHandler) {
				errorHandler.call newException
			} else {
				throw newException
			}
		}
	}

	private closureToBooleanCondition(Closure closure) {
		new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						def result = closure.call()
						closure.call() as Boolean
					}
				}
	}
}
