package de.thokari.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

class ImprovedSearchContext {

	private SearchContext context

	public ImprovedSearchContext(SearchContext context) {
		this.context = context
	}

	public By css(String selector) {
		By.cssSelector selector
	}

	public WebElement findElement(By locator) {
		new GroovyWebElement(context.findElement(locator))
	}

	public List<WebElement> findElements(By locator) {
		context.findElements(locator).collect { new GroovyWebElement(it) }
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
