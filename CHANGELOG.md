# selenium-components-0.0 (alpha)

## selenium-components-0.0.5

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
