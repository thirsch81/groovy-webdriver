package de.thokari.webdriver.test

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver

import de.thokari.webdriver.GroovyWebDriver
import de.thokari.webdriver.test.page.DuckDuckGoHomePage
import de.thokari.webdriver.test.page.DuckDuckGoResultsPage

public class GroovyWebDriverTest {

	@Delegate
	GroovyWebDriver driver

	@Before
	public void setUp() {
		driver = new GroovyWebDriver(new FirefoxDriver())
	}

	@After
	public void tearDown() {
		driver.quit()
	}

	@Test
	public void testDuckDuckGoSearchWikipedia() {

		open "https://duckduckgo.com"

		waitFor 2, { titleContains "DuckDuckGo" }

		waitFor 2, "title to contain DuckDuckGo", { title.contains "DuckDuckGo" }

		waitFor 2, "search button to be enabled", {
			findElement("#search_button_homepage").isEnabled()
		}

		"Wikipedia".each { letter -> type "#search_form_input_homepage", letter }

		waitFor {
			textToBePresentInElementValue css("#search_form_input_homepage"), "Wikipedia"
			elementToBeClickable css("#search_button_homepage")
		}.click()

		waitFor 2, {
			textToBePresentInElement css("#zero_click_heading"), "Wikipedia"
		}
	}

	@Test
	public void testDuckDuckGoWithPageObjectValidation() {

		open "https://duckduckgo.com"

		assertTrue expect { titleIs "Search DuckDuckGo" }
		assertTrue expect { presenceOfElementLocated css("#search_button_homepage") } instanceof WebElement

		onValidate DuckDuckGoHomePage, {

			enterSearch "Wikipedia"
			clickSearchButton()
		}

		assertTrue notOn(DuckDuckGoHomePage)

		onValidate DuckDuckGoResultsPage


		//		, {
		//
		//			DuckDuckGoResultListItem { list ->
		//				list.each {
		//					println it.linkText
		//				}
		//			}
		//println get(DuckDuckGoResultListItem)[0].linkText

		//println it.components.DuckDuckGoResultListItem[1].linkText
		//}
	}
}