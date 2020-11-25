package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.OutputType;

/**
 * The type of screenshot to take
 *
 * @author HAM
 */
public enum ScreenshotOutputType
{

    BASE64(OutputType.BASE64),

    FILE(SeleniumGlobals.PERSISTENT_FILE);

    private final OutputType<?> outputType;

    ScreenshotOutputType(OutputType<?> outputType)
    {
        this.outputType = outputType;
    }

    public OutputType<?> getOutputType()
    {
        return outputType;
    }

}
