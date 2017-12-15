# Selenium Components

**Selenium Components** is small and easy-to-use framework, on top of the Java implementation of [Selenium](http://www.seleniumhq.org/), to access web elements more easily.

It encourages you to describe web pages as component tree. This component tree simplifies access to the underlying elements.

The framework was extracted from a proprietary project and is currently still in progress.

## Basic Concept

When writing tests with Selenium the basic workflow is to call a web page, wait until it's loaded and check the elements of the page. If the page contains scripts, it get's a bit more complicated: call a web page, wait until it's loaded, do something, wait until the script has completed and check the elements of the page. If the page is a single-page-application with a lot of async calls, things get awfully complicated. Your tests then consist of do-wait-check passages, that are hard to write, even harder to read and a pain to maintain. 

**Selenium Components** tries to simplify this. It let's you define the complete layout of the page as component tree (before the page is even loaded). Afterwards you can use the tree to implement your tests. 

Let's look at this (simplified) example:

	<html>
		<body>
		    <div id="input">
		        <input name="text" value="Hello World!" />
		        <button onclick="document.getElementById('result').innerHTML=document.getElementsByName('text')[0].value">Test</button>
		    </div>
		    <div id="result"></div>
		</body>
	</html> 

If you want to test this awfully complicated page, start to describe the page as component tree (you can do it by using final variables, it does not matter if the elements are not loaded, yet):

	private final HtmlPage page = new HtmlPage("https://localhost:8080");
	private final HtmlComponent inputDiv = new HtmlComponent(page, WebElementSelector.selectById("result"));  
	private final ButtonComponent button = new ButtonComponent(inputDiv);  
	private final InputComponent inputField = new InputComponent(inputDiv, WebElementSelector.selectByName("text"));  
	private final HtmlComponent resultDiv = new HtmlComponent(page, WebElementSelector.selectById("result"));  

When defining a component you have to pass the parent as first parameter (e.g. the `testLink` has the `inputDiv` as parent and the `inputDiv` has the `page` as parent). The `WebElementSelector` just searches within element of the parent component.
 
Let's write a test! Selenium Components is optimized for [Hamcrest](http://hamcrest.org/) matchers:

	public void test() {
		page.open();
		assertThat(inputField.getValue(), is("Hello World!"));
	}

The `getValue()` method has a timeout (like most other methods). By default, it waits one second for the input field to become available. 

Another test with some interaction:

	public void testUpdate() {
		page.open();
		inputField.enter("Hello Selenium!")
		button.click();
		SeleniumAssert.assertThatSoon(inputDiv::getText, is("Hello Selenium!"));
	}

You surely have recognized the `SeleniumAssert.assetThatSoon` call. If you are using a slow browser, it may take some time until the `div` gets updated. If you call `inputDiv.getText()` immediately, it may still return an empty string. The `assertThatSoon` method has a timeout, by default one second, and it keeps testing the assertion to succeed within the timeout.

Modern web pages are usually built by using components. Imagine, that the `inputDiv` in this example is a component. It's just a logical step, that the `inputDiv` is a component in your test, too.

	public class InputDiv extends AbstractSeleniumComponent {
		private final ButtonComponent button = new ButtonComponent(this);  
		private final InputComponent inputField = new InputComponent(this, WebElementSelector.selectByName("text"));  
		
		public void submit(String text) {
			inputField.enter(text);
			button.click();
		}
	} 

You can use `this` as parent for the sub-components. Now you can reuse your component in multiple test classes.
 
As you can see, the strength of **Selenium Components** is to define a whole web page by defining some final fields in your test.

## Basic Details

**Selenium Components** is a fairly simple framework: everything focuses on the `SeleniumComponent` interface, it's derivates and some static utilities.

Usually you define a `SeleniumComponent` by providing a parent and a `WebElementSelector`. The `WebElementSelector` is used to find Selenium's `WebElement`. 

To access a properties of a component, use the `SeleniumUtils` class. Additinally there a couple of standard components, like the `HtmlComponent`, the `LinkComponent`, the `InputComponent` and so on, that provide methods for their intended purpose.

The `SeleniumUtils` class provides some handy methods for interactions with a timeout, like `waitUntil`, `keepTrying` and `callWithTimeout`. 

For testing use the `SeleniumAsserts` class. It provides some handy assertions including the fabulous `assertThatSoon` call with a timeout. 

Quite often, you need to work with some kind of lists. For this, you can either use the `ListComponent` or just the `SeleniumComponentListFactory`. Both classes need a child selector and a factory for the child components. You can then access all the items with a `SeleniumComponentList`.


 
