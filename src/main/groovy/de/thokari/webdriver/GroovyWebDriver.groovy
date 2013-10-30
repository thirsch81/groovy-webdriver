package de.thokari.webdriver

import java.util.List;
import java.util.concurrent.TimeUnit

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import de.thokari.webdriver.page.PageHandler
import de.thokari.webdriver.wait.ImprovedWait

public class GroovyWebDriver {

	@Delegate
	WebDriver driver
	
	@Delegate
	private ImprovedSearchContext improvedSearchContext = new ImprovedSearchContext(this)

	@Delegate
	private ImprovedWait improvedWait = new ImprovedWait(this)

	@Delegate
	private PageHandler pageHandler = new PageHandler(this)

	private Long implicitWaitMilliseconds

	public GroovyWebDriver(WebDriver driver) {
		this.driver = driver
	}

	def methodMissing(String name, args) {
		driver.invokeMethod(name, args)
	}

	public void setImplicitWait(Long milliseconds) {
		implicitWaitMilliseconds = milliseconds
		driver.manage().timeouts().implicitlyWait implicitWaitMilliseconds, TimeUnit.MILLISECONDS
	}

	public void unsetImplicitWait() {
		implicitWaitMilliseconds = 0
		driver.manage().timeouts().implicitlyWait implicitWaitMilliseconds, TimeUnit.MILLISECONDS
	}

	public void open(String url) {
		driver.get url
	}

	public def execJS(String command) {
		((JavascriptExecutor) driver).executeScript command
	}

	public void sleep(milliseconds) {
		Thread.sleep milliseconds as Long
	}

	public void sleep(from, to) {
		def time = from + new Random().nextInt(to)
		Thread.sleep time as Long
	}
}
