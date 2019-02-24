package com.example.xdev.perkid.utils;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.TextView;

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

    public static boolean isValidPassword(TextInputLayout password) {
        if (!password.getEditText().getText().toString().isEmpty()) {
            String passwordPattern = "((?=.*[A-Za-z]).(?=.*[A-Za-z0-9@#$%_])(?=\\S+$).{3,})";
            Pattern pattern = Pattern.compile(passwordPattern);
            Matcher matcher = pattern.matcher(password.getEditText().getText().toString());
            if (matcher.matches()) {
                return true;
            }
            password.setErrorEnabled(true);
            password.setErrorTextAppearance(R.style.TextInputLayoutError);
            password.setError("password must contain at least 3 digits, one character or more ");
            return false;
        }
        return true;
    }

    public static boolean isMatched(TextInputLayout textInputLayout1, TextInputLayout textInputLayout2) {
        if (!textInputLayout2.getEditText().getText().toString().equals(textInputLayout1.getEditText().getText().toString())) {
            textInputLayout2.setError(" Password Doesn't Match ");
            textInputLayout2.setErrorEnabled(true);
            textInputLayout2.setErrorTextAppearance(R.style.TextInputLayoutError);
            return false;
        }
        return true;
    }

    public static void setErrorTextView(TextView textView) {
        textView.setError(" You Must Choose Account Type ");
    }
}
