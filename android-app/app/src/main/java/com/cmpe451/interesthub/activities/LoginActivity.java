package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("INFO","login activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button b=(Button) findViewById(R.id.button);
        final EditText e = (EditText) findViewById(R.id.editText);
        final EditText e2 = (EditText) findViewById(R.id.editText2);
        final TextView t = (TextView) findViewById(R.id.textView);
        InterestHub hb = (InterestHub)getApplication();
        hb.getSessionController();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(e.getText().toString().equals("baris")){
                    if(e2.getText().toString().equals("1234")){
                        Intent intent= new Intent(view.getContext(), UserActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
        t.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent= new Intent(view.getContext(), SignUpActivity.class);
                startActivity(intent);

                return true;
            }


        });
 /*       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
