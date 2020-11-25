package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

/**
 * Some tests for {@link Utils}.
 *
 * @author ham
 */
public class UtilsTest
{

    @Test
    public void simplify()
    {
        assertThat(Utils.simplify("Fo1+-\n\tä"), is("fo1ä"));
    }

    @Test
    public void levenshteinDistanceTest()
    {
        assertThat(Utils.levenshteinDistance("Hannah", "Hannah"), is(0.0));
        assertThat(Utils.levenshteinDistance("Hannah", "Hanah"), closeTo(0.15, 0.01));
        assertThat(Utils.levenshteinDistance("Hannah", "Johannah"), closeTo(0.333, 0.01));
        assertThat(Utils.levenshteinDistance("Hannah", "Tobi"), greaterThan(0.8));
    }

    @Test
    public void simplifiedLevenshteinDistanceTest()
    {
        assertThat(Utils
            .simplifiedLevenshteinDistance( //
                "\tThe quick\nbrown fox\njumps over the lazy dog!", "the quick brown fox jumps over the lazy dog"),
            is(0.0));

        assertThat(Utils
            .simplifiedLevenshteinDistance( //
                "\tThe quick\nbrown fox!", "the quick brown fox jumps over the lazy dog"),
            greaterThan(0.5));
    }

}
