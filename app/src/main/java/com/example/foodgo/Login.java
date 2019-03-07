package com.example.foodgo;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgo.DatabaseModel.MyHelper;
import com.example.foodgo.Entity.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private Button btnForgot;
    private Button btnSign_up;
    private Button btnLogin;
    private EditText txtEmail ,txtPassword;
    private TextView txtTitle;
    private MyHelper myHelper;
    private LoginButton btn_FB;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSign_up = findViewById(R.id.btnSignup_login);
        btnLogin = findViewById(R.id.txtSign_in);
        txtEmail = findViewById(R.id.editText_email_sign_in);
        txtTitle = findViewById(R.id.sign_in_title);
        txtPassword = findViewById(R.id.txtPassword_login);
        myHelper = new MyHelper(this);
        btnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_up = new Intent(getApplicationContext(), Sign_up.class);
                startActivityForResult(sign_up, 100);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkedAccount = myHelper.checkAccountLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
                if(checkedAccount == true)
                {
                    Intent intent = new Intent(getApplicationContext(),LocationActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Login.this, "Your Account is incorrect", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        mCallbackManager = CallbackManager.Factory.create();

        btn_FB = (LoginButton) findViewById(R.id.btn_fb);
        btn_FB.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        btn_FB.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == 200)
            {
                User user = (User) data.getSerializableExtra("userinformation");
                txtEmail.setText(String.valueOf(user.getUsername()));
                txtTitle.setText(String.valueOf(user.getFirstname()) + " " + String.valueOf(user.getLastname()));
            }
        }
    }
}
