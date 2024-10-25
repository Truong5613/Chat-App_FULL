package com.raven.model;

import javax.swing.Icon;

public class ModelCard {
    private String title;
    private Object values;  // Changed to Object
    private int percentage;
    private Icon icon;
    private String uptime;

    public ModelCard(String title, Object values, int percentage, Icon icon) { // Updated parameter type
        this.title = title;
        this.values = values;
        this.percentage = percentage;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getValues() {  // Updated return type
        return values;
    }

    public void setValues(Object values) {  // Updated parameter type
        this.values = values;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
