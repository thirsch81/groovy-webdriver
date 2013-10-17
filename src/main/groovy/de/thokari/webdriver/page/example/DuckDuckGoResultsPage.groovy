package de.thokari.webdriver.page.example

import groovy.transform.InheritConstructors
import de.thokari.webdriver.page.GroovyWebDriverPage

@InheritConstructors
class DuckDuckGoResultsPage extends GroovyWebDriverPage {

	@Override
	public void validate() {
		findElement ".results_links"
	}

	@Override
	public Boolean isCurrentPage() {
		isElementPresent "#header_logo"
	}

	public String getZeroClickLinkText() {
		text "#zero_click_heading"
	}
}
