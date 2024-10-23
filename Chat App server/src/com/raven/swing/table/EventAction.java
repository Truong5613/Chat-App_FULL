package com.raven.swing.table;

import model.Model_User_Account;

public interface EventAction {

    public void delete(Model_User_Account user);

    public void update(Model_User_Account user);
}
