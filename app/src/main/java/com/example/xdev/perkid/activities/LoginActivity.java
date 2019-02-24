package com.example.xdev.perkid.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.utils.Utils;
import com.example.xdev.perkid.models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    //Vars
    Realm realm;
    String username, password;

    //Views
    TextInputLayout textInputLayout_username, textInputLayout_password;
    Button button_signIn;
    TextView textView_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        realm = Realm.getDefaultInstance();

        textInputLayout_username = findViewById(R.id.login_textInputLayout_username);
        textInputLayout_password = findViewById(R.id.login_textInputLayout_password);
        button_signIn = findViewById(R.id.button_signIn);
        textView_register = findViewById(R.id.login_text_register);

        if (isLogged()) {
            login();
        }

        onClickSignInButton();
        onClickTextRegister();
    }

    boolean isLogged() {
        User user = realm.where(User.class)
                .equalTo("isLogged", true)
                .findFirst();

        if (user != null) {
            return true;
        }
        return false;
    }

    void login() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    void onClickSignInButton() {

        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidData()) {
                    username = textInputLayout_username.getEditText().getText().toString();
                    password = textInputLayout_password.getEditText().getText().toString();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            User user = realm.where(User.class)
                                    .equalTo("username", username)
                                    .equalTo("password", password)
                                    .equalTo("isLogged", false)
                                    .findFirst();
                            if (user != null) {
                                user.setLogged(true);
                                login();
                            } else {
                                ConstraintLayout constraintLayout = findViewById(R.id.layout);
                                Snackbar.make(constraintLayout, "Enter the right username or password", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            // Transaction was a success.
                            //Toast.makeText(LoginActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            // Transaction failed and was automatically canceled.
                            //Toast.makeText(LoginActivity.this, "Enter the right username or password", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });


    }

    boolean isValidData() {

        if ((!Utils.isEmptyInputTextLayout(textInputLayout_username, textInputLayout_password))) {

            if (Utils.isValidPassword(textInputLayout_password)) {
                return true;
            }
        }
        return false;
    }

    void onClickTextRegister() {
        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });
    }

    void getAllUsers() {
        RealmResults<User> users = realm.where(User.class).findAll();
    }

    void clearDatabase() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //realm.close();
    }
}
