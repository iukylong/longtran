package com.kait.longlongtran.storyvoz.LoginFirebase.ViewMVP;

/**
 * Created by LongLongTran on 2/6/2018.
 */

public interface ILoginView {
     void onLoginSuccess(Boolean result,int code);

    void onSetProgressBar(int visibility);
}
