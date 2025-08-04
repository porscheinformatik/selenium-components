package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import java.util.List;

public interface WithClarityControlHelpers extends SeleniumComponent {
    default SeleniumComponentList<HtmlComponent> getHelperComponents() {
        return SeleniumComponentListFactory.of(
            this,
            WebElementSelector.selectByTagName("clr-control-helper"),
            HtmlComponent::new
        ).findAll();
    }

    default List<String> getHelperTexts() {
        return getHelperComponents().stream().map(HtmlComponent::getText).toList();
    }
}
