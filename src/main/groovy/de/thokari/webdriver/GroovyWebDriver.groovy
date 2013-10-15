package de.thokari.webdriver

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.JavascriptExecutor

import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.WebElement
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.concurrent.TimeUnit

public class GroovyWebDriver {

	@Delegate
	WebDriver driver

	ExpectedConditions condition = new ExpectedConditions()

	Long implicitWaitMilliseconds

	private Boolean implicitWaitInitialized = false

	public void setImplicitWait(Long milliseconds) {
		driver.manage().timeouts().implicitlyWait milliseconds, TimeUnit.MILLISECONDS
		implicitWaitInitialized = true
	}

	public void unsetImplicitWait() {
		implicitWaitMilliseconds = 0
		driver.manage().timeouts().implicitlyWait implicitWaitMilliseconds, TimeUnit.MILLISECONDS
		implicitWaitInitialized = false
	}

	public void open(String url) {
		driver.get url
	}

	public By css(String selector) {
		By.cssSelector selector
	}

	public WebElement find(String selector) {
		find By.cssSelector(selector)
	}

	public WebElement find(By locator) {
		if(!implicitWaitInitialized && implicitWaitMilliseconds) {
			setImplicitWait implicitWait
		}
		driver.findElement locator
	}

	public Boolean waitFor(Long seconds = 5, String message, Closure clos) {
		def wait = new WebDriverWait(driver, seconds)
		clos.delegate = this
		try {
			wait.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							return clos.call() as Boolean
						}
					})
		} catch(Exception e) {
			def newException = new TimeoutException("Timed out after $seconds seconds waiting for $message")
			throw newException
		}
	}
	
	public def waitFor(Long seconds = 5, Closure clos) {
		clos.delegate = condition
		def wait = new WebDriverWait(driver, seconds)
		wait.until clos.call()
	}

	public void sleep(milliseconds) {
		Thread.sleep milliseconds as Long
	}

	public void sleep(from, to) {
		def time = from + new Random().nextInt(to)
		Thread.sleep time as Long
	}

	public def exec(String command) {
		((JavascriptExecutor) driver).executeScript command
	}

	public void click(locator) {
		find(locator).click()
	}

	public void type(locator, String text) {
		find(locator).sendKeys(text)
	}

	public String text(locator) {
		find(locator).getText()
	}
}