package com.raven.swing.table;

import model.Model_User_Account;

public class ModelAction {

    private Model_User_Account user; // Change to hold Model_User_Account
    private EventAction event; // Keep the event type

    public Model_User_Account getUser() {
        return user; // Getter for the user
    }

    public void setUser(Model_User_Account user) {
        this.user = user; // Setter for the user
    }

    public EventAction getEvent() {
        return event; // Getter for the event
    }

    public void setEvent(EventAction event) {
        this.event = event; // Setter for the event
    }

    // Constructor that takes a Model_User_Account and EventAction
    public ModelAction(Model_User_Account user, EventAction event) {
        this.user = user; // Set the user
        this.event = event; // Set the event
    }

    // Default constructor
    public ModelAction() {
    }
}
