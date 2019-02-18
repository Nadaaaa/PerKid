package com.example.xdev.perkid.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.Utils.Utils;
import com.example.xdev.perkid.models.CurrentUser;
import com.example.xdev.perkid.models.User;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    //Vars
    Realm realm;
    String username, password;

    //Views
    TextInputLayout textInputLayout_username, textInputLayout_password;
    Button button_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        realm = Realm.getDefaultInstance();

        // To Add Database.
        writeToDB();

        textInputLayout_username = findViewById(R.id.textInputLayout_username);
        textInputLayout_password = findViewById(R.id.textInputLayout_password);
        button_signIn = findViewById(R.id.button_signIn);


        if (isLogged()) {
            login();
        }

        onClickSignInButton();

        //To Delete All Database.
       /* realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/


    }

    void login() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
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


    void writeToDB() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User user = bgRealm.createObject(User.class);
                user.setUsername("xdev");
                user.setPassword("123456");
                user.setLogged(false);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                //Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                //Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    boolean isValidData() {

        if ((!Utils.isEmptyInputTextLayout(textInputLayout_username, textInputLayout_password))) {
            return true;
        }
        return false;
    }


    boolean isLogged() {
        User user = realm.where(User.class)
                .equalTo("username", "xdev")
                .equalTo("password", "123456")
                .equalTo("isLogged", true)
                .findFirst();

        if (user != null) {
            return true;
        } /*else {
            //Toast.makeText(this, "You Don't Have an Account", Toast.LENGTH_LONG).show();
        }*/
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
