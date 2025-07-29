package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A tags-input field.
 *
 * @author cet
 */
public class TagsInputComponent extends AbstractSeleniumComponent {

    private final SuggestionList suggestionList = new SuggestionList(this);

    public TagsInputComponent(SeleniumComponent parent) {
        super(parent, selectByTagName("tags-input"));
    }

    public TagsInputComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getValue() {
        return getAttribute("value");
    }

    /**
     * @param value the value to be entered
     * @param selectSuggestion the number of the element in the suggested list which should be selected
     */
    public void setValue(String value, int selectSuggestion) {
        InputComponent input = new InputComponent(this);

        input.enter(value);

        suggestionList.selectEntry(value, selectSuggestion);
    }

    public void clear() {
        try {
            List<WebElement> removeButtons = searchContext().findElements(By.className("remove-button"));

            for (WebElement removeButton : removeButtons) {
                removeButton.click();
            }
        } catch (NoSuchElementException e) {
            //Nothing to do. May be empty
        }
    }

    private static class SuggestionList extends AbstractSeleniumComponent {

        private final SeleniumComponentListFactory<HtmlComponent> itemFactory = new SeleniumComponentListFactory<>(
            this,
            selectByTagName("li"),
            HtmlComponent::new
        );

        SuggestionList(SeleniumComponent parent) {
            super(parent, selectByClassName("suggestion-list"));
        }

        public void selectEntry(String value, int selectSuggestion) {
            waitUntilVisible(10);

            SeleniumComponentList<HtmlComponent> matchingList = itemFactory.findAll();
            SeleniumComponentList<HtmlComponent> filteredList = matchingList.filter(item ->
                item.getText().contains(value)
            );

            if (filteredList.size() == 0) {
                matchingList.get(selectSuggestion).click();
            } else {
                filteredList.get(selectSuggestion).click();
            }
        }
    }
}
