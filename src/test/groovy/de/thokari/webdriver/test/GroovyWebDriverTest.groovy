package de.thokari.webdriver.test;

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import de.thokari.webdriver.GroovyWebDriver
import de.thokari.webdriver.page.example.DuckDuckGoHomePage
import de.thokari.webdriver.page.example.DuckDuckGoResultsPage;

import static org.junit.Assert.*

public class GroovyWebDriverTest {

	@Delegate
	GroovyWebDriver driver

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

		open "https://duckduckgo.com"

		waitFor 2, { titleContains "DuckDuckGo" }

		"Wikipedia".each { letter -> type "#search_form_input_homepage", letter }

		waitFor { elementToBeClickable css("#search_button_homepage") }.click()

		waitFor 2, {
			textToBePresentInElement css("#zero_click_heading"), "Wikipedia"
		}
	}

	@Test
	public void testDuckDuckGoWithPageObjectValidation() {

		open "https://duckduckgo.com"

		expect {
			titleIs "Search DuckDuckGos"
		}
		
		onValidate DuckDuckGoHomePage, {
			
			enterSearch "Wikipedia"
			clickSearch()
		}
		
		assertTrue notOn(DuckDuckGoHomePage)
		
		onValidate DuckDuckGoResultsPage, {
			
		}
	
		
	}
}