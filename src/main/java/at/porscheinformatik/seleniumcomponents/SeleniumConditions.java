/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.Arrays;
import java.util.function.Supplier;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Daniel Furtlehner
 */
public final class SeleniumConditions
{

    private SeleniumConditions()
    {
    }

    public static Supplier<Boolean> clickable(ClickableSeleniumComponent component)
    {
        return new Supplier<Boolean>()
        {
            @Override
            public Boolean get()
            {
                return component.isClickable();
            }

            @Override
            public String toString()
            {
                return String.format("clickable[%s]", component);
            }
        };
    }

    public static Supplier<Boolean> visible(SeleniumComponent component)
    {
        return new Supplier<Boolean>()
        {
            @Override
            public Boolean get()
            {
                return component.isVisible();
            }

            @Override
            public String toString()
            {
                return String.format("visible[%s]", component);
            }
        };
    }

    /**
     * Condition that can be used to wait until a page is fully loaded.
     *
     * @param driver the driver
     * @return a condition that checks for readyState=complete
     */
    public static Supplier<Boolean> readyState(WebDriver driver)
    {
        return () -> {
            JavascriptExecutor e = ((JavascriptExecutor) driver);

            Object readyState = e.executeScript("return document.readyState");

            return readyState.equals("complete");
        };
    }

    /**
     * The condition is resolved when at least one of the conditions is fulfilled.
     *
     * @param conditions to check
     * @return the Supplier
     */
    public static Supplier<Boolean> oneOf(Supplier<?>... conditions)
    {
        return new OneOfCondition(conditions);
    }

    /**
     * The condition is resolved when all of the conditions are fulfilled.
     *
     * @param conditions to check
     * @return the Supplier
     */
    public static Supplier<Boolean> allOf(Supplier<?>... conditions)
    {
        return new AllOfCondition(conditions);
    }

    public static Supplier<Boolean> visibilityOfElement(Supplier<WebElement> elementSelector)
    {
        return new VisibleElementCondition(elementSelector);
    }

    public static Supplier<Boolean> visibilityOfElementLocatedBy(SeleniumComponent component)
    {
        return new VisibilityPageComponentSelectorCondition(component, true);
    }

    public static Supplier<Boolean> invisibilityOfElementLocatedBy(SeleniumComponent component)
    {
        return new VisibilityPageComponentSelectorCondition(component, false);
    }

    public static Supplier<Boolean> hasText(SeleniumComponent component, String text)
    {
        return new HasTextCondition(component, text);
    }

    /**
     * <pre>
     *           ___
     *         O(? ?)O
     *           (_)     - Ook!
     * </pre>
     */
    public static class HasTextCondition implements Supplier<Boolean>
    {
        private final SeleniumComponent component;
        private final String text;

        public HasTextCondition(SeleniumComponent component, String text)
        {
            super();
            this.component = component;
            this.text = text;
        }

        @Override
        public Boolean get()
        {
            try
            {
                WebElement element = component.element();

                return text.equals(element.getText());
            }
            catch (NoSuchElementException e)
            {
                return false;
            }
        }

    }

    /**
     * <pre>
     *           ___
     *         O(x x)O
     *           (_)     - Ook!
     * </pre>
     */
    public static class VisibilityPageComponentSelectorCondition implements Supplier<Boolean>
    {
        private final SeleniumComponent component;
        private final boolean shouldBeVisible;

        public VisibilityPageComponentSelectorCondition(SeleniumComponent component, boolean shouldBeVisible)
        {
            super();
            this.component = component;
            this.shouldBeVisible = shouldBeVisible;
        }

        @Override
        public Boolean get()
        {
            return SeleniumUtils.retryOnStale(() -> {
                try
                {
                    WebElement element = component.element();

                    if (shouldBeVisible)
                    {
                        return element != null && element.isDisplayed();
                    }

                    return element == null || !element.isDisplayed();
                }
                catch (NoSuchElementException e)
                {
                    return shouldBeVisible ? false : true;
                }

            });
        }

        @Override
        public String toString()
        {
            return "VisibleElementCondition [component=" + component + "]";
        }

    }

    /**
     * <pre>
     *           ___
     *         O(x x)O
     *           (_)     - Ook!
     * </pre>
     */
    public static class VisibleElementCondition implements Supplier<Boolean>
    {
        private final Supplier<WebElement> elementSelector;

        public VisibleElementCondition(Supplier<WebElement> elementSelector)
        {
            super();
            this.elementSelector = elementSelector;
        }

        @Override
        public Boolean get()
        {
            WebElement element = elementSelector.get();

            return element != null && element.isDisplayed();
        }

        @Override
        public String toString()
        {
            return "VisibleElementCondition [elementSelector=" + elementSelector + "]";
        }

    }

    /**
     * <pre>
     *           ___
     *         O(x x)O
     *           (_)     - Ook!
     * </pre>
     */
    public static class OneOfCondition implements Supplier<Boolean>
    {
        private final Supplier<?>[] conditions;

        public OneOfCondition(Supplier<?>[] conditions)
        {
            super();
            this.conditions = conditions;
        }

        @Override
        public Boolean get()
        {
            for (Supplier<?> condition : conditions)
            {
                Object value = condition.get();

                if (value == null)
                {
                    continue;
                }

                if (Boolean.class.isAssignableFrom(value.getClass()) && !((Boolean) value).booleanValue())
                {
                    return false;
                }

                return true;
            }

            return false;
        }

        @Override
        public String toString()
        {
            return "OneOfCondition [conditions=" + Arrays.toString(conditions) + "]";
        }

    }

    /**
     * <pre>
     *           ___
     *         O(x x)O
     *           (_)     - Ook!
     * </pre>
     */
    public static class AllOfCondition implements Supplier<Boolean>
    {
        private final Supplier<?>[] conditions;

        public AllOfCondition(Supplier<?>[] conditions)
        {
            super();
            this.conditions = conditions;
        }

        @Override
        public Boolean get()
        {
            for (Supplier<?> condition : conditions)
            {
                Object value = condition.get();

                if (value == null)
                {
                    return false;
                }

                if (Boolean.class.isAssignableFrom(value.getClass()) && !((Boolean) value).booleanValue())
                {
                    return false;
                }
            }

            return true;
        }

        @Override
        public String toString()
        {
            return "AllOfCondition [conditions=" + Arrays.toString(conditions) + "]";
        }
    }

}
