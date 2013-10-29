package de.thokari.webdriver.test.component

import java.awt.TexturePaintContext.Byte;

import de.thokari.webdriver.page.GroovyWebDriverPageComponent
import groovy.transform.InheritConstructors

import org.openqa.selenium.By

@InheritConstructors
class DuckDuckGoResultListItem extends GroovyWebDriverPageComponent {

	@Override
	public void validate() {
	}

	@Override
	public void loadedWhen() {
	}

	public String getLinkText() {
		By locator = By.tagName("a")
		findElement(locator).text
	}
}
