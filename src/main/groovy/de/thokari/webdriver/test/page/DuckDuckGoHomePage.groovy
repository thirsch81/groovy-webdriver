package de.thokari.webdriver.test.page

import groovy.transform.InheritConstructors
import de.thokari.webdriver.GroovyWebDriver
import de.thokari.webdriver.page.GroovyWebDriverPage
import de.thokari.webdriver.page.GroovyWebDriverPageComponent

@InheritConstructors
class DuckDuckGoHomePage extends GroovyWebDriverPage {

	@Override
	public Boolean isCurrentPage() {
		title.equals("Search DuckDuckGo")
	}

	@Override
	public void validate() {
		waitFor 0, { titleIs "Search DuckDuckGo" }
		waitFor 0, { elementToBeClickable css("#search_button_homepage") }
	}

	@Override
	public void addComponents() {
		// TODO Auto-generated method stub
	}
	
	void enterSearch(String text) {
		type "#search_form_input_homepage", text
	}
	
	void clickSearchButton() {
		click "#search_button_homepage"
	}
}
