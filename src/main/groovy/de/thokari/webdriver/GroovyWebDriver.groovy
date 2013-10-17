package de.thokari.webdriver

import java.lang.reflect.Constructor
import java.util.concurrent.TimeUnit

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import de.thokari.webdriver.page.GroovyWebDriverPage
import de.thokari.webdriver.page.WrongPageException

public class GroovyWebDriver {

	@Delegate
	WebDriver driver

	ExpectedConditions condition = new ExpectedConditions()

	Long implicitWaitMilliseconds

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

	public Boolean waitFor(Long seconds = 10, String message, Closure condition) {
		def wait = new WebDriverWait(driver, seconds)
		condition.delegate = this
		try {
			wait.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							condition.call() as Boolean
						}
					})
		} catch(Exception e) {
			def newException = new TimeoutException("Timed out after $seconds seconds waiting for $message")
			throw newException
		}
	}

	public def waitFor(Long seconds = 10, Closure condition) {
		condition.delegate = this.condition
		def wait = new WebDriverWait(driver, seconds)
		wait.until condition.call()
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

	Boolean on(Class<? extends GroovyWebDriverPage> pageClass, Closure actions = null) {
		GroovyWebDriverPage page = createPage(pageClass)
		Boolean onPage = page.isCurrentPage()
		if(actions) {
			actions.delegate = page
			actions.call()
		}
		onPage
	}

	Boolean notOn(Class<? extends GroovyWebDriverPage> pageClass, Closure actions = null) {
		GroovyWebDriverPage page = createPage(pageClass)
		Boolean notOnPage = !page.isCurrentPage()
		if(actions && notOnPage) {
			actions.delegate = page
			actions.call()
		}
		notOnPage
	}

	void onValidate(Class<? extends GroovyWebDriverPage> pageClass, Closure actions = null) {
		GroovyWebDriverPage page = createPage(pageClass)
		if(page.isCurrentPage()) {
			page.validate()
			if(actions) {
				actions.delegate = page
				actions.call()
			}
		} else {
			throw new WrongPageException("Not on ${pageClass.simpleName}")
		}
	}

	def condition(Closure expectation) {
		expectation.delegate = condition
		expectation.call().apply(driver)
	}

	def expect(Closure expectation) {
		expectation.delegate = condition
		ExpectedCondition expectedCondition = expectation.call()
		if(!(expectedCondition.apply(driver) as Boolean)) {
			throw new ExpectationFailedException("Expecting $expectedCondition")
		}
	}

	private GroovyWebDriverPage createPage(Class<? extends GroovyWebDriverPage> pageClass) {
		Constructor constructor = pageClass.getConstructor(GroovyWebDriver)
		constructor.newInstance(this)
	}
}