package com.example.kbm15.loto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class InputActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);




        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNewItem = (EditText) findViewById(R.id.email);
                String itemText = etNewItem.getText().toString();
                etNewItem.setText("");


                if (itemText.length() != 5){
                    Context context = getApplicationContext();
                    CharSequence text = "Numero incorrecto";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("some_key",itemText);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }

            }
        });


    }



}

