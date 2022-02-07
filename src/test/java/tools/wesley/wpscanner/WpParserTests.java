package tools.wesley.wpscanner;

import org.junit.jupiter.api.Test;
import tools.wesley.wpscanner.wp.WpParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WpParserTests {
    private WpParser wpParser = new WpParser();

    @Test
    void installationDirectoryHttp() {
        var installationDir = wpParser.getInstallationDirectory("http://localhost/wp-content/plugin/custom.js");

        assertEquals("http://localhost/", installationDir.get());
    }

    @Test
    void installationDirectoryHttps() {
        var installationDir = wpParser.getInstallationDirectory("https://localhost/wp-content/plugins/abc/style.css");

        assertEquals("https://localhost/", installationDir.get());
    }

    @Test
    void themeDirectoryHttp() {
        var installationDir = wpParser.getThemeDirectory("http://localhost/wp-content/themes/test/style.css");

        assertEquals("test", installationDir.get());
    }

    @Test
    void themeDirectoryHttps() {
        var installationDir = wpParser.getThemeDirectory("https://localhost/wp-content/themes/abc/style.css");

        assertEquals("abc", installationDir.get());
    }

    @Test
    void themeDirectoryDeep() {
        var installationDir = wpParser.getThemeDirectory("https://localhost/wp-content/themes/def/styling/main.css");

        assertEquals("def", installationDir.get());
    }

}
