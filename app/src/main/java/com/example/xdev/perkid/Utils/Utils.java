package com.example.xdev.perkid.Utils;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.xdev.perkid.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isEmptyInputTextLayout(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            if (TextUtils.isEmpty(textInputLayout.getEditText().getText().toString())) {
                textInputLayout.setError(" Mandatory Field ");
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setErrorTextAppearance(R.style.TextInputLayoutError);
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPassword(EditText password) {
        if (!password.getText().toString().isEmpty()) {
            String passwordPattern = "((?=.*[A-Za-z]).(?=.*[A-Za-z0-9@#$%_])(?=\\S+$).{3,})";
            Pattern pattern = Pattern.compile(passwordPattern);
            Matcher matcher = pattern.matcher(password.getText().toString());
            if (matcher.matches()) {
                return true;
            }
            password.requestFocus();
            password.setError("password must contain at least 3 digits, one character or more ");
            return false;
        }
        return true;
    }
}
