package de.thokari.webdriver

import org.openqa.selenium.WebElement

class GroovyWebElement {

	public GroovyWebElement(WebElement element) {
		this.element = element
	}

	@Delegate
	WebElement element

	@Delegate
	private ImprovedSearchContext improvedSearchContext = new ImprovedSearchContext(element)
}
