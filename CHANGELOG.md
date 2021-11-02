
# selenium-components-0 (alpha)

## selenium-components-0.6.5

- Better error handling if component is not clickable
- Separate AbstractSeleniumPage into a page and the "standalone" page. The standalone page has the same functionality as the page until now.

## selenium-components-0.6.4

- Fix the SeleniumUtils.containsClassName method which had false positives

## selenium-components-0.6.3

- Reenable input validation (https://bugs.chromium.org/p/chromium/issues/detail?id=1205107 has been fixed)

## selenium-components-0.6.2

- Disable input validation (it fails because of https://bugs.chromium.org/p/chromium/issues/detail?id=1205107)

## selenium-components-0.6.1

- Fixed a possible NPE with Utils.simplify()

## selenium-components-0.6.0

- Fixed bug with the visibility check of the SeleniumUtils. Some componets overrive the isVisible methode (e.g. pages or tabs), but the isVisible method in the SeleniumUtils did not use this method causing the visibility check to be invalid. 
- API simplifications:
  - Combined Selectable-, Editable- and ClickableSeleniumComponent into ActivteSeleniumComponent. 
  - The SeleniumComponent is now a Visible one by default
  - The SeleniumConditions are deprecated because the are not used and can be perfectly replaced by assertThatSoon with matchers.
  - Some Assert methods have been deprecated where Matchers exist. It should reduce the number of assert method variants.
  - Removed some utility methods, because they just link to the appropriate method in the component.

## selenium-components-0.5.6

- Prevent stale exception with SelectComponent
- Additional SeleniumMatchers for editable, selectable, enabled, disabled
- SeleniumUtils.retryOnFail with runnables in addition to keepTrying

## selenium-components-0.5.5

- More robust SeleniumComponentListFactory if parent element is unavailable
- KeepTrying handles AssertionErrors correctly (Throwables)
- Removed CRs from Base64 screensshots to improve copybility
- Optimized assertThatSoon once more to improve error reporting
- Fixed description of VisibilityMatcher
- Deprecated some obsolete and problematic methods

## selenium-components-0.5.4

- Some more window handling options.
- Fix switching back to original window.
- Preventing exception when screenshotting closed windows.

## selenium-components-0.5.3

- Optimize description of failed click
- Use JPG for error image strings to avoid truncation
- Better robustness with keepTrying/retryOnState

## selenium-components-0.5.2

- Optimize SelectComponent and AngularSelectComponent

## selenium-components-0.5.1

- getAlertTexts method for ClarityAlertComponent
- Optimize assertThatSoon/Later

## selenium-components-0.5.0

- Shorter, more precise exception message in assertThatSoon/Later.
- Upgraded plugins, compiling against Java 11, now.

## selenium-components-0.4.4

- Slowly increase the delay in keepTrying-calls to reduce resource usage.
- Fix visiblity detection of Clarity modals without title.
- Fix and optimize selectByIndex
- Add class utilities to SeleniumUtils
- Basic ClarityTabsComponent

## selenium-components-0.4.3

- Add `contains` to StringPredicate
- Add `clearLocalStorage` to SeleniumEnvironment
- Fix `selectByLabel` and `selectByValue` of various SelectComponents
- ClickableSeleniumComponent should respect the short timeout
- Add `select` to SeleniumComponentListFactory to speed up selection if component can be selected

## selenium-components-0.4.2

- Utility methods in SeleniumEnvironment for handling windows and frames.
- Useful StringPredicate for simplifying work with predicates based on strings.

## selenium-components-0.4.1

- Deprecate Angular specified code in standard components
- Add a specified AngularSelectComponent repecting the ":" in the value field.

## selenium-components-0.4.0

- The SeleniumEnvironment is reponsible for screenshots, now.
- Migrate to Junit5

## selenium-components-0.3.4

- Some WebElementSelectors now support multiple parameters as or-concatination

## selenium-components-0.3.3

- Make value comparator configurable

## selenium-components-0.3.2

- PageTitle should be public

## selenium-components-0.3.1

- Remove abtract from ClarityWizardPage
- Added a findParent method to the Selenium Utils
- Added more selector options to ImageComponent

## selenium-components-0.3.0

- Adapt Components for Clarity 3

## selenium-components-0.2.0

- Fix usage of java 11 API

## selenium-components-0.1.1

- Read locale from html lang attribute

## selenium-components-0.1.0

- Upgraded all dependencies
- Some WebElementSelectors now support multiple parameters as or-concatination.

## selenium-components-0.0.34

- Fix for newest clarity version

## selenium-components-0.0.33

- Increase default timeout from 2 to 3 seconds

## selenium-components-0.0.32

- Removed callWithTimeout from SeleniumUtils.keepTrying

## selenium-components-0.0.31

- Adapted Clarity radio component

## selenium-components-0.0.30

- Make EditableSeleniumComponent more stable

## selenium-components-0.0.29

- Make ClickableSeleniumComponent more stable

## selenium-components-0.0.28

- Fix wrong implementatin for SelectComponent

## selenium-components-0.0.27

- Optimize assertion handling

## selenium-components-0.0.26

- Speeding up keepTrying
- Fixed some warnings

## selenium-components-0.0.25

- Make date handling Java 8 compatible

## selenium-components-0.0.24

- Add date handling to SeleniumEnvironment

## selenium-components-0.0.23

- Catch AssertionErrors in keepTrying
- Improve exception reporting

## selenium-components-0.0.22

- Add getName and getId to Checkbox components

## selenium-components-0.0.21

- Add second constructor to ClarityWizardComponent

## selenium-components-0.0.20

- Adapt clarity components for V2.0

## selenium-components-0.0.19

- Add some getters to the ClarityWizardComponent
- Fix build for OpenJDK 11

## selenium-components-0.0.18

- Update clarity components to version 1.0

## selenium-components-0.0.17

- Retry on stale element error

## selenium-components-0.0.16

- Improve the SelectComponent
- Fix bug on exact comparison

## selenium-components-0.0.15

- Optimizes loading of elements

## selenium-components-0.0.14

- Adds some more components for clarity

## selenium-components-0.0.13

- Fixes Problem with ClarityAlertComponent in devmode

## selenium-components-0.0.12

- Fixes some problems with clarity in production mode

## selenium-components-0.0.11

- Did some cleanup
- Adds HeadingComponent
- Adds PreComponent
- Adds constructor for selenium-key to ButtonComponent
- Fixes invalid system property handling
- Adds isClickable Matcher

## selenium-components-0.0.10

### Breaking Changes

- Call-Line logging moved to ThreadUtils.
- Using SeleniumLogger as Logger instead of SLF directly.

## selenium-components-0.0.9

### Enhancements

- Components for Clarity
- The SeleniumComponent now offers a LOG variable.
- Utilities for simplifying strings and computing the Levenshtein Distance.
- The SeleniumComponent now offers a LOG variable. Debug log messages were added
  at various locations.
- The InputComponent now checks the input on enter.
- keepTrying() now does not stack itself anymore.

## selenium-components-0.0.8

### Breaking Changes

- Remaning assertThatSoon methods
- HtmlComponent is final

### Enhancements

- Additional Components and Assertions
- Improves API for SeleniumListComponent
- Clarity checkbox

## selenium-components-0.0.7

### Enhancements

- Default WebDriverFactories for Chrome, Firefox, Edge and BrowserStack.
- assertThatSoon accepts supplier methods with exceptions.
- Better code line hints on exceptions.

## selenium-components-0.0.6

### Breaking Changes

- Moved isClickable, click to ClickableSelenumComponent interface.
- Moved isEditable, clear, sendKeys, isEnabled to EditableSelenumComponent interface.
- Moved isSelected to SelectableSeleniumComonent interface.

### Enhancements

- Moved isVisible to own VisibleSeleniumComponent interface.
- Added an AnimatedSeleniumComponent with an animation check.

## selenium-components-0.0.5

### Breaking Changes

- Clarified usage of isVisible by adding it to SeleniumComponent.
- Removed isReady and isVisible from SeleniumUtils for consistency.

### Enhancements

- SelectFirst and SelectLast for WebElementSelector

## selenium-components-0.0.4

### Breaking Changes

- SeleniumComponent provides a searchContext() method instead of implementing the interface.
- Renamed SeleniumContext to SeleniumEnvironment, otherwise it's too confusing with the SearchContext.

### Enhancements

- Added ListComponent
- Better debugging support (use -Dselenium-components.debug)
- More SeleniumUtils and -Asserts

### Fixes

- Fixed endless-loop in retryOnStale

## selenium-components-0.0.3

### Breaking Changes

- Moved SeleniumActions to SeleniumUtils
- Moved some SeleniumUtils to Utils
- Removed component classes named after HTML elements

### Fixes

- Fix for WebElementSelector.selectByIndex(..)

### Enhancements

- WebElementSelector.selectByColumn(..) added
- Better toString descriptions for components and selectors

## selenium-components-0.0.2

### Breaking Changes

- Added some documentation
- Some classnames changed to simplify understanding of the API.
- SeleniumComponent does not provide elements() anymore, to simplify the API.

### Enhancements

- WebElementSelector got a selectByIndex(int)
- new predefined components

## selenium-components-0.0.1

Initial release.
