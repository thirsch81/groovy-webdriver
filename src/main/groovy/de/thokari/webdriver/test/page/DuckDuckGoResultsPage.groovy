package de.thokari.webdriver.test.page

import groovy.transform.InheritConstructors
import de.thokari.webdriver.page.GroovyWebDriverPage
import de.thokari.webdriver.test.component.DuckDuckGoResultListItem

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

	@Override
	public void addComponents() {
		multiple DuckDuckGoResultListItem, "div[class^='results']"
	}

	public String getZeroClickLinkText() {
		text "#zero_click_heading"
	}
}
