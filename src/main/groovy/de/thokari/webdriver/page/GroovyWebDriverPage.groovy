package de.thokari.webdriver.page

import org.openqa.selenium.WebElement

import de.thokari.webdriver.GroovyWebDriver

abstract class GroovyWebDriverPage {

	@Delegate(interfaces = false)
	GroovyWebDriver driver

	Map<String, Object> components = [:]

	GroovyWebDriverPage(GroovyWebDriver driver) {
		this.driver = driver
	}

	abstract void validate()

	abstract Boolean isCurrentPage()

	abstract void  addComponents()

	protected void single(Class<? extends GroovyWebDriverPageComponent> clazz) {
		components.put clazz.simpleName, clazz.getConstructor(WebElement).newInstance(element)
	}

	protected void multiple(Class<? extends GroovyWebDriverPageComponent> clazz, commonSelector) {
		List<WebElement> elements = driver.findElements(commonSelector)
		//		elements.each {
		//			println it.getAttribute("class")
		//		}
		List<GroovyWebDriverPageComponent> multipleComponents = []
		elements.each { WebElement element ->
			multipleComponents.add clazz.getConstructor(WebElement).newInstance(element)
		}
		components.put clazz.simpleName, multipleComponents
	}

	private void validateComponents() {
		components.each { key, componentOrList->
			if(componentOrList instanceof GroovyWebDriverPageComponent) {
				componentOrList.validate()
			} else {
				componentOrList.each { component ->
					component.validate()
				}
			}
		}
	}

	public void safeValidate(Closure errorHandler) {
		try {
			validate()
		} catch(Exception e) {
			errorHandler.call(e)
		}
	}
}
