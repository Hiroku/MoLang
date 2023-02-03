package com.bedrockk.molang;

public abstract class StringHolder {
    String originalString = null;

    public void setOriginalString(String value) {
        this.originalString = value;
    }

    public String getOriginalString() {
        return this.originalString;
    }
}
