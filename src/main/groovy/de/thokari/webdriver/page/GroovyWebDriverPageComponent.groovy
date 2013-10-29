package de.thokari.webdriver.page

import org.openqa.selenium.WebElement

abstract class GroovyWebDriverPageComponent {

	@Delegate(interfaces = false)
	WebElement rootElement

	GroovyWebDriverPageComponent(WebElement element) {
		this.rootElement = element
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
