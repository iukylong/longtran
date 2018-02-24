package com.kait.longlongtran.storyvoz.LoginFirebase.Model;

/**
 * Created by LongLongTran on 2/6/2018.
 */

public interface IUser {
    String getName();

    String getPassword();

    int checkValidation(String name, String password);
}
