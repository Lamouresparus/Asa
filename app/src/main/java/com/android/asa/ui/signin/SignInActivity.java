package com.android.asa.ui.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.android.asa.R;
import com.android.asa.utils.ViewUtil;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView tvResetPassword = findViewById(R.id.textViewResetPassword);
        TextView textViewSignUp = findViewById(R.id.textViewSignUp);

        makeTextSpannable(tvResetPassword, textViewSignUp);

    }


    /**
     *  using a spannable text to change color for a portion of text view
     * @param tv1 is is text view
     * @param tv2 is another text view
     */
    private void makeTextSpannable(TextView tv1, TextView tv2) {

        ViewUtil
                .spannableText(tv1,
                        getResources().getString(R.string.text_forgot_password_reset),
                        17,
                        22,
                        ContextCompat.getColor(this, R.color.colorAccent));


        ViewUtil.spannableText(tv2,
                tv2.getText().toString(),
                20,
                27,
                ContextCompat.getColor(this, R.color.colorAccent));
    }
}
