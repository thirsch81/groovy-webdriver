package de.thokari.webdriver.page

import de.thokari.webdriver.GroovyWebDriver

abstract class GroovyWebDriverPage {

	@Delegate(interfaces = false)
	GroovyWebDriver driver
	
	GroovyWebDriverPage(GroovyWebDriver driver) {
		this.driver = driver
	}

	abstract void validate()

	abstract Boolean isCurrentPage()

	public void safeValidate(Closure errorHandler) {
		try {
			validate()
		} catch(Exception e) {
			errorHandler.call(e)
		}
	}
}
