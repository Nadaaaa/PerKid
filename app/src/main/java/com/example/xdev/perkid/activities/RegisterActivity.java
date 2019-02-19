package com.example.xdev.perkid.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.Utils.Utils;
import com.example.xdev.perkid.models.User;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    //Views
    TextInputLayout textInputLayout_username, textInputLayout_password, textInputLayout_confirmPassword;
    TextView textView_signIn, textView_accountType;
    Button button_register;
    RadioGroup radioGroup_accountType;

    //Vars
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        realm = Realm.getDefaultInstance();

        textInputLayout_username = findViewById(R.id.register_textInputLayout_username);
        textInputLayout_password = findViewById(R.id.register_textInputLayout_password);
        textInputLayout_confirmPassword = findViewById(R.id.register_textInputLayout_confirmPassword);
        textView_signIn = findViewById(R.id.register_text_signIn);
        button_register = findViewById(R.id.button_register);
        radioGroup_accountType = findViewById(R.id.radioGroup_accountType);
        textView_accountType = findViewById(R.id.register_text_accountType);

        onClickSignInText();
        onClickRegisterButton();
    }

    void onClickRegisterButton() {
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidData()) {
                    String username = textInputLayout_username.getEditText().getText().toString();
                    String password = textInputLayout_password.getEditText().getText().toString();
                    int selectedID = radioGroup_accountType.getCheckedRadioButtonId();
                    String accountType = "";
                    if (selectedID == R.id.radioButton_parent) {
                        accountType = "parent";
                    } else if (selectedID == R.id.radioButton_kid) {
                        accountType = "kid";
                    }

                    writeToDB(username, password, accountType);

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                }
            }
        });
    }

    boolean isValidData() {

        if ((!Utils.isEmptyInputTextLayout(textInputLayout_username, textInputLayout_password, textInputLayout_confirmPassword))) {
            if (Utils.isValidPassword(textInputLayout_password)) {
                if (Utils.isMatched(textInputLayout_password, textInputLayout_confirmPassword)) {
                    // if user didn't select account type the selected Id will be -1
                    int selectedID = radioGroup_accountType.getCheckedRadioButtonId();
                    if (selectedID == -1) {
                        Utils.setErrorTextView(textView_accountType);
                    } else {
                        return true;
                    }

                }
            }
        }
        return false;
    }


    void onClickSignInText() {
        textView_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                RegisterActivity.this.finish();
            }
        });
    }

    void writeToDB(final String username, final String password, final String accountType) {
        //Add user to database.
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setUsername(username);
                user.setPassword(password);
                user.setAccountType(accountType);
                user.setLogged(true);
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

}
