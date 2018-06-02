package com.qa.constant;

public enum ChromeDriverPath {
    WINDOWS("C:/Development/web_driver/chromedriver.exe"),
    LINUX("~/web_driver/chromedriver");

    public final String value;
    ChromeDriverPath(final String value) {
        this.value = value;
    }
}
