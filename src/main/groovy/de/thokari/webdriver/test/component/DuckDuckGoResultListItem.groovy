package de.thokari.webdriver.test.component

import groovy.transform.InheritConstructors
import de.thokari.webdriver.page.GroovyWebDriverPageComponent

@InheritConstructors
class DuckDuckGoResultListItem extends GroovyWebDriverPageComponent {

	@Override
	public void validate() {
	}

	@Override
	public void loadedWhen() {
	}

	public String getLinkText() {
		text "h2 a"
	}
}
