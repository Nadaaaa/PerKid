package com.example.xdev.perkid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.models.User;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_signOut;

    Realm realm;

    String username, accountType;

    TextView textView_username, textView_accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_username = findViewById(R.id.main_text_username);
        textView_accountType = findViewById(R.id.main_text_accountType);

        imageView_signOut = findViewById(R.id.imageView_signOut);

        realm = Realm.getDefaultInstance();

        getCurrentUser();

        onClickSignOutImage();

    }

    void getCurrentUser() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                User user = realm.where(User.class)
                        .equalTo("isLogged", true)
                        .findFirst();

                username = user.getUsername();
                accountType = user.getAccountType();

                textView_username.setText(username);
                textView_accountType.setText(accountType);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                // Toast.makeText(MainActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                //Toast.makeText(MainActivity.this, "EditError", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void onClickSignOutImage() {
        imageView_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        User user = realm.where(User.class)
                                .equalTo("isLogged", true)
                                .findFirst();
                        user.setLogged(false);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        // Transaction was a success.
                        signOut();
                        // Toast.makeText(MainActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        // Transaction failed and was automatically canceled.
                        //Toast.makeText(MainActivity.this, "EditError", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    void signOut() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        MainActivity.this.finish();
    }
}
