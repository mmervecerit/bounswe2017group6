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
import com.cmpe451.interesthub.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button b2=(Button) findViewById(R.id.button);
        Button b3=(Button) findViewById(R.id.button2);
        Button b4=(Button) findViewById(R.id.button3);

        final EditText e = (EditText) findViewById(R.id.FirstName);
        final EditText e2 = (EditText) findViewById(R.id.SecondName);
        final EditText e3 = (EditText) findViewById(R.id.Email);
        final EditText e4 = (EditText) findViewById(R.id.Password);

        final TextView t2 = (TextView) findViewById(R.id.textView2);

        final InterestHub hub = (InterestHub) getApplication();

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(!e.getText().toString().equals(null) && !e3.getText().toString().equals(null) && !e4.getText().toString().equals(null)){

                    hub.getApiService().addUser(e.getText().toString(),e3.getText().toString(),e4.getText().toString()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("Signup ", "Signup response succesfull");
                            Intent intent= new Intent(view.getContext(), LoginActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }



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
