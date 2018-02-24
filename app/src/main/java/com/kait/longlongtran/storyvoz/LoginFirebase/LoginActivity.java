package com.kait.longlongtran.storyvoz.LoginFirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kait.longlongtran.storyvoz.LoginFirebase.Presenter.ILoginPresenter;
import com.kait.longlongtran.storyvoz.LoginFirebase.Presenter.LoginPresenterCompl;
import com.kait.longlongtran.storyvoz.LoginFirebase.ViewMVP.ILoginView;
import com.kait.longlongtran.storyvoz.R;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    private EditText editUser;
    private EditText editPass;
    private Button btnLogin;
    ILoginPresenter loginPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        editUser = (EditText) findViewById(R.id.edt_UserName);
        editPass = (EditText) findViewById(R.id.edt_Password);
        btnLogin = (Button) findViewById(R.id.btn_SingIn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnLogin.setOnClickListener(this);
        loginPresenter = new LoginPresenterCompl(this);
        loginPresenter.setProgressBar(View.INVISIBLE);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_SingIn:
                loginPresenter.setProgressBar(View.INVISIBLE);
                loginPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
                break;
        }
    }

    @Override
    public void onLoginSuccess(Boolean result, int code) {
        loginPresenter.setProgressBar(View.INVISIBLE);
        if (result) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Login Fail, code = " + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
