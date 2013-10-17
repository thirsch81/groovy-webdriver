package de.thokari.webdriver.page.example

import groovy.transform.InheritConstructors
import de.thokari.webdriver.GroovyWebDriver
import de.thokari.webdriver.page.GroovyWebDriverPage

@InheritConstructors
class DuckDuckGoHomePage extends GroovyWebDriverPage {

	@Override
	public Boolean isCurrentPage() {
		title == "Search DuckDuckGo"
	}

	@Override
	public void validate() {
		waitFor 0, { titleIs "Search DuckDuckGo" }
		waitFor 0, { elementToBeClickable css("#search_button_homepage") }
	}

	void enterSearch(String text) {
		type "#search_form_input_homepage", text
	}

	void clickSearch() {
		click "#search_button_homepage"
	}
}
