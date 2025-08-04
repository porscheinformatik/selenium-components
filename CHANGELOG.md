# selenium-components-0 (alpha)

## selenium-components-0.13.2

- Deprecated an open method to avoid ambiguous method calls.
- Add helpers to Clarity form components

## selenium-components-0.13.1

- Added some more selectors to select containers.
- Added a selectByLabelContains to select containers

## selenium-components-0.13.0

- Introduce Prettier for formatting code (company standard) and reformat all code.
- Add some utility methods to Button/Checkbox/InputComponents.

## selenium-components-0.12.6

- Improve the fix for selecting/deselecting Clarity checkboxes without label

## selenium-components-0.12.5

- Fix selecting/deselecting Clarity checkboxes without label

## selenium-components-0.12.4

- Quality of Life enhancements for Clarity Components

## selenium-components-0.12.3

- Add getOptionLabels to Comboboxes

## selenium-components-0.12.2

- Add byFormControlName methode to ClaritySelectContainerComponent

## selenium-components-0.12.1

- Add selectByLabel\* methode to WebElementSelectors
- Add byText and byLabel factory methods to some components

## selenium-components-0.12.0

- Stabelize Clarity comboboxes. It's a minor breaking change, because I could not resist to move the abstract
  classes to their own file.
- Added selectByText and selectByClassNameAndText to the WebElementSelectors

## selenium-components-0.11.3

- Add code for interacting with local storage

## selenium-components-0.11.2

- Fixed an issue with DataGrids of Clartiy 17.2.1

## selenium-components-0.11.1

- Introduced ClarityPasswordContainerComponent

## selenium-components-0.11.0

- The attribute `selenium-key` will be removed in favor of the official `data-testid` attribute. All methods
  involving `selenium-key` will be deprecated.

## selenium-components-0.10.5

- Fix an issue with comboboxes without selected option.

## selenium-components-0.10.4

- Fix an invalid call in waitUntilInvisible.

## selenium-components-0.10.3

- Add assert...Soon to all waitFor... methods (that do not trigger the "no-assertion" warning).

## selenium-components-0.10.2

- Add method to clear combobox and mark it dirty.

## selenium-components-0.10.1

- Additional waitFor\* methods
- scrollIntoView for ActiveSeleniumComponents

## selenium-components-0.10.0

- Update to Java 17
- Update dependencies

## selenium-components-0.9.0

- Optimize ClarityWizardComponent by checking result after next(), previous() and finish()

## selenium-components-0.9.0

- Restore SeleniumMatchers.isNotVisible
- Removed a lot of deprecated code

## selenium-components-0.8.1

- Fix selectByIndex (it did not respect the parent component)

## selenium-components-0.8.0

- Upgraded Selenium to 4.8.3

## selenium-components-0.7.14

- Add a Selenium Matcher for contaisning text
- Add an escapeXPath utility for escaping stings used in XPath arguments

## selenium-components-0.7.13

- Fix an issue with selecting radio buttons.

## selenium-components-0.7.12

- Made the open(url) method of the page deprecated, since it implies that the caller should known the URL of the page

## selenium-components-0.7.11

- Fix buttons in wizards (based on types, now)

## selenium-components-0.7.10

- Add helper methods to some components

## selenium-components-0.7.9

- Make it possible to globally disable the logger in order to avoid logging of passwords.
- Add IFrameComponent

## selenium-components-0.7.8

- Add components for Clarity comboboxes.

## selenium-components-0.7.7

- Add ClarityInputContainer.
- Improve ClarityFormControlContainer.

## selenium-components-0.7.6

- Fix the selectByIndex methods to work as described.

## selenium-components-0.7.5

- Fix the selectByIndex methods to work as described and intended.

## selenium-components-0.7.4

- Failing to take screenshot does not break error reporting
- SeleniumEnvironment.parseDate accepts empty string and returns null in this case.

## selenium-components-0.7.3

- Ignore error when switching back to closed browser.

## selenium-components-0.7.2

- Add switchToOtherWindow to Environment.

## selenium-components-0.7.1

- Fix getName method of Clarity checkbox.

## selenium-components-0.7.0

- Upgrade to Selenium 4

## selenium-components-0.6.8

- Fast access for select options, that "contain" a value
- Container for Clarity selects

## selenium-components-0.6.7

- Fast access for radio components by index

## selenium-components-0.6.6

- Added SeleniumUtils.findParentByType(..) that returns the parent of the specific type
- Fixed the XPath selectors for attributes
- Reenabled keepTrying with keepTrying (used by assertThatSoon)
- Fixed a bug when switching windows
- Basic Clarity icon

## selenium-components-0.6.5

- Better error handling if component is not clickable
- Separate AbstractSeleniumPage into a page and the "standalone" page. The standalone page has the same functionality as
  the page until now.

## selenium-components-0.6.4

- Fix the SeleniumUtils.containsClassName method which had false positives

## selenium-components-0.6.3

- Reenable input validation (https://bugs.chromium.org/p/chromium/issues/detail?id=1205107 has been fixed)

## selenium-components-0.6.2

- Disable input validation (it fails because of https://bugs.chromium.org/p/chromium/issues/detail?id=1205107)

## selenium-components-0.6.1

- Fixed a possible NPE with Utils.simplify()

## selenium-components-0.6.0

- Fixed bug with the visibility check of the SeleniumUtils. Some componets overrive the isVisible methode (e.g. pages or
  tabs), but the isVisible method in the SeleniumUtils did not use this method causing the visibility check to be
  invalid.
- API simplifications:
    - Combined Selectable-, Editable- and ClickableSeleniumComponent into ActivteSeleniumComponent.
    - The SeleniumComponent is now a Visible one by default
    - The SeleniumConditions are deprecated because the are not used and can be perfectly replaced by assertThatSoon
      with matchers.
    - Some Assert methods have been deprecated where Matchers exist. It should reduce the number of assert method
      variants.
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

- Moved isClickable, click to ClickableSeleniumComponent interface.
- Moved isEditable, clear, sendKeys, isEnabled to EditableSeleniumComponent interface.
- Moved isSelected to SelectableSeleniumComponent interface.

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
