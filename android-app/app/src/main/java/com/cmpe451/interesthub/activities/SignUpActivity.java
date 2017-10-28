package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button b2=(Button) findViewById(R.id.button);
        Button b3=(Button) findViewById(R.id.button2);
        Button b4=(Button) findViewById(R.id.button3);

        final EditText e = (EditText) findViewById(R.id.editText3);
        final EditText e2 = (EditText) findViewById(R.id.editText4);
        final EditText e3 = (EditText) findViewById(R.id.editText7);
        final EditText e4 = (EditText) findViewById(R.id.editText10);

        final TextView t2 = (TextView) findViewById(R.id.textView2);



        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(view.getContext(), UserActivity.class);
                startActivity(intent);

            }
        });
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


}
