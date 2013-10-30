package de.thokari.webdriver.page

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import de.thokari.webdriver.GroovyWebDriver
import de.thokari.webdriver.GroovyWebElement


abstract class GroovyWebDriverPageComponent {

	@Delegate(interfaces = false)
	GroovyWebElement rootElement

	@Delegate
	private GroovyWebDriver driver

	GroovyWebDriverPageComponent(GroovyWebDriver driver, GroovyWebElement element) {
		this.driver= driver
		this.rootElement = element
	}

	public List<WebElement> findElements(By locator) {
		println "22test22"
		driver.improvedSearchContext.findElements(locator).collect { new GroovyWebElement(it) }
	}

	public Set<String> getWindowHandles() {
		driver.windowHandles
	}

	abstract void validate()

	abstract void loadedWhen()

	public void safeValidate(Closure errorHandler) {
		try {
			validate()
		} catch(Exception e) {
			errorHandler.call(e)
		}
	}
}
