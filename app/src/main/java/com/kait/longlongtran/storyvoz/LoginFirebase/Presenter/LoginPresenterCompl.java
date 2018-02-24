package com.kait.longlongtran.storyvoz.LoginFirebase.Presenter;

import android.os.Handler;
import android.os.Looper;

import com.kait.longlongtran.storyvoz.LoginFirebase.Model.IUser;
import com.kait.longlongtran.storyvoz.LoginFirebase.Model.UserModel;
import com.kait.longlongtran.storyvoz.LoginFirebase.ViewMVP.ILoginView;

/**
 * Created by LongLongTran on 2/6/2018.
 */

public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser iUser;
    Handler handler;

    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void doLogin(String name, String password) {
        Boolean isLoginSuccess = true;
        final int code = iUser.checkValidation(name, password);
        if (code != 0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginSuccess(result, code);
            }
        }, 1000);
    }

    @Override
    public void setProgressBar(int visibility) {
        iLoginView.onSetProgressBar(visibility);
    }

    private void initUser() {
        iUser = new UserModel("", "");
    }
}
