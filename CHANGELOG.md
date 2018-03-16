# selenium-components-0.0 (alpha)

## selenium-components-0.0.8

### Enhancements

* Components for Clarity
* Utilities for simplifying strings and computing the Levenshtein Distance.
* The SeleniumComponent now offers a LOG variable. Debug log messages were added
  at various locations.

## selenium-components-0.0.7

### Enhancements

* Default WebDriverFactories for Chrome, Firefox, Edge and BrowserStack.
* assertThatSoon accepts supplier methods with exceptions.
* Better code line hints on exceptions.

## selenium-components-0.0.6

### Breaking Changes

* Moved isClickable, click to ClickableSelenumComponent interface.
* Moved isEditable, clear, sendKeys, isEnabled to EditableSelenumComponent interface.
* Moved isSelected to SelectableSeleniumComonent interface.

### Enhancements

* Moved isVisible to own VisibleSeleniumComponent interface.
* Added an AnimatedSeleniumComponent with an animation check.

## selenium-components-0.0.5

### Breaking Changes

* Clarified usage of isVisible by adding it to SeleniumComponent.
* Removed isReady and isVisible from SeleniumUtils for consistency.

### Enhancements

* SelectFirst and SelectLast for WebElementSelector

## selenium-components-0.0.4

### Breaking Changes 

* SeleniumComponent provides a searchContext() method instead of implementing the interface.
* Renamed SeleniumContext to SeleniumEnvironment, otherwise it's too confusing with the SearchContext.

### Enhancements

* Added ListComponent 
* Better debugging support (use -Dselenium-components.debug)
* More SeleniumUtils and -Asserts

### Fixes

* Fixed endless-loop in retryOnStale

## selenium-components-0.0.3

### Breaking Changes

* Moved SeleniumActions to SeleniumUtils
* Moved some SeleniumUtils to Utils
* Removed component classes named after HTML elements

### Fixes

* Fix for WebElementSelector.selectByIndex(..)

### Enhancements

* WebElementSelector.selectByColumn(..) added
* Better toString descriptions for components and selectors

## selenium-components-0.0.2

### Breaking Changes

* Added some documentation
* Some classnames changed to simplify understanding of the API.
* SeleniumComponent does not provide elements() anymore, to simplify the API.

### Enhancements

* WebElementSelector got a selectByIndex(int)
* new predefined components

## selenium-components-0.0.1

Initial release.
