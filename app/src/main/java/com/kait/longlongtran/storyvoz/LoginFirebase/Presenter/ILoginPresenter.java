package com.kait.longlongtran.storyvoz.LoginFirebase.Presenter;

/**
 * Created by LongLongTran on 2/6/2018.
 */

public interface ILoginPresenter {
    void doLogin(String name, String password);

    void setProgressBar(int visibility);
}
