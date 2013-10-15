package de.thokari.webdriver.test;

import de.thokari.webdriver.GroovyWebDriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import org.junit.Test
import org.junit.Before
import org.junit.After

public class GroovyWebDriverTest {

	WebDriver driver

	@Before
	public void setUp() {
		driver = new GroovyWebDriver(driver: new HtmlUnitDriver(true))
	}

	@After
	public void tearDown() {
		driver.quit()
	}

	@Test
	public void testDuckDuckGoSearchWikipedia() {
		(driver as GroovyWebDriver).with {

			open "https://duckduckgo.com"

			waitFor 2, { titleContains "DuckDuckGo" }

			"Wikipedia".each { letter -> type letter, "#search_form_input_homepage" }

			waitFor { elementToBeClickable css("#search_button_homepage") }.click()

			waitFor 2, {
				textToBePresentInElement css("#zero_click_heading"), "Wikipedia"
			}
		}
	}
}