package de.thokari.webdriver

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
	private ImprovedWait improvedWait = new ImprovedWait(this)

	@Delegate
	private PageHandler pageHandler = new PageHandler(this)

	private Long implicitWaitMilliseconds

	public GroovyWebDriver(WebDriver driver) {
		this.driver = driver
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

	public By css(String selector) {
		By.cssSelector selector
	}

	public WebElement findElement(String selector) {
		findElement css(selector)
	}

	public List<WebElement> findElements(String selector) {
		findElements css(selector)
	}

	public Boolean isElementPresent(locator) {
		findElements(locator) as Boolean
	}

	public Boolean isElementAbsent(locator) {
		!isElementPresent(locator)
	}

	public Boolean isElementVisible(locator) {
		if(isElementPresent(locator)) {
			return findElement(locator).displayed
		} else {
			return false
		}
	}

	public void sleep(milliseconds) {
		Thread.sleep milliseconds as Long
	}

	public void sleep(from, to) {
		def time = from + new Random().nextInt(to)
		Thread.sleep time as Long
	}

	public def execJS(String command) {
		((JavascriptExecutor) driver).executeScript command
	}

	public void click(locator) {
		findElement(locator).click()
	}

	public void type(locator, String text) {
		findElement(locator).sendKeys(text)
	}

	public String text(locator) {
		findElement(locator).getText()
	}
}
