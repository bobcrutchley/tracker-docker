package com.qa.constant;

public enum ChromeDriverPath {
    WINDOWS("C:/Development/web_driver/chromedriver.exe"),
    LINUX("/home/jenkins/chromedriver");

    public final String value;
    ChromeDriverPath(final String value) {
        this.value = value;
    }
}
