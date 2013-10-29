package de.thokari.webdriver.page

import java.lang.reflect.Constructor

import de.thokari.webdriver.GroovyWebDriver

class PageHandler {

	private GroovyWebDriver driver

	public PageHandler(GroovyWebDriver driver) {
		this.driver = driver
	}

	Boolean on(Class<? extends GroovyWebDriverPage> pageClass, Closure actions = null) {
		GroovyWebDriverPage page = createPage(pageClass)
		Boolean onPage = page.isCurrentPage()
		if(actions) {
			actions.delegate = page
			actions.call()
		}
		onPage
	}

	Boolean notOn(Class<? extends GroovyWebDriverPage> pageClass, Closure actions = null) {
		GroovyWebDriverPage page = createPage(pageClass)
		Boolean notOnPage = !page.isCurrentPage()
		if(actions && notOnPage) {
			actions.call()
		}
		notOnPage
	}

	void onValidate(Class<? extends GroovyWebDriverPage> pageClass, Closure actions = null) {
		GroovyWebDriverPage page = createPage(pageClass)
		if(page.isCurrentPage()) {
			page.validate()
			if(actions) {
				actions.delegate = page
				actions.resolveStrategy = Closure.DELEGATE_FIRST
				actions.call()
			}
		} else {
			throw new WrongPageException("Not on ${pageClass.simpleName}")
		}
	}

	private GroovyWebDriverPage createPage(Class<? extends GroovyWebDriverPage> pageClass) {
		Constructor constructor = pageClass.getConstructor(GroovyWebDriver)
		GroovyWebDriverPage page = constructor.newInstance(driver)
		page.addComponents()
		page
	}
}
