package de.thokari.webdriver.page

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import de.thokari.webdriver.GroovyWebDriver
import de.thokari.webdriver.GroovyWebElement

abstract class GroovyWebDriverPage {

	@Delegate
	private GroovyWebDriver driver

	public List<WebElement> findElements(By locator) {
		driver.improvedSearchContext.findElements(locator)
	}
	public Set<String> getWindowHandles() {
		driver.windowHandles
	}

	Map<String, Object> components = [:]

	GroovyWebDriverPage(GroovyWebDriver driver) {
		this.driver = driver
	}

	abstract void validate()

	abstract Boolean isCurrentPage()

	abstract void addComponents()

	protected void single(Class<? extends GroovyWebDriverPageComponent> clazz) {
		components.put clazz.simpleName, clazz.getConstructor(GroovyWebDriver, WebElement).newInstance(driver, element)
	}

	protected void multiple(Class<? extends GroovyWebDriverPageComponent> clazz, commonSelector) {
		List<WebElement> elements = findElements(commonSelector)
		List<GroovyWebDriverPageComponent> multipleComponents = []
		elements.each { element ->
			def constructor = clazz.getConstructor(GroovyWebDriver, GroovyWebElement)
			// weird this is no GroovyWebDriver...
			assert !(driver instanceof GroovyWebDriver)
			multipleComponents.add constructor.newInstance(new GroovyWebDriver(driver), element)
		}
		components.put clazz.simpleName, multipleComponents
	}

	private void validateComponents() {
		components.each { key, componentOrList->
			if(componentOrList instanceof GroovyWebDriverPageComponent) {
				componentOrList.validate()
			} else {
				componentOrList.each { component ->
					component.validate()
				}
			}
		}
	}

	public void safeValidate(Closure errorHandler) {
		try {
			validate()
		} catch(Exception e) {
			errorHandler.call(e)
		}
	}
}
