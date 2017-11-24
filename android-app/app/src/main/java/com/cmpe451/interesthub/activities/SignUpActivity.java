package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.models.Interest;
import com.cmpe451.interesthub.models.Profile;
import com.cmpe451.interesthub.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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

        final EditText firstName = (EditText) findViewById(R.id.FirstName);
        final EditText secondName = (EditText) findViewById(R.id.SecondName);
        final EditText email = (EditText) findViewById(R.id.Email);
        final EditText password = (EditText) findViewById(R.id.Password);
        final EditText password2 = (EditText) findViewById(R.id.Password2);
        final EditText username = (EditText) findViewById(R.id.UserName);

        final EditText about = (EditText) findViewById(R.id.about);
        final EditText contact = (EditText) findViewById(R.id.contact);


        final InterestHub hub = (InterestHub) getApplication();

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                if(!password.getText().toString().equals(password2.getText().toString())){
                    Toast toast = Toast.makeText(getBaseContext(),"Passwords does not match",Toast.LENGTH_LONG);
                    toast.show();
                }
                if(!firstName.getText().toString().equals(null) && !email.getText().toString().equals(null) && !password.getText().toString().equals(null)){
                    User user = new User();
                    user.setEmail(email.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());

                    Profile p = new Profile();
                    p.setName(firstName.getText().toString());
                    p.setAbout(about.getText().toString());
                    p.setContacts(contact.getText().toString());
                    List<Interest> interestList = new ArrayList<Interest>();
                    //TODO set interest list
                    p.setInterests(interestList);
                    p.setLastname(secondName.getText().toString());
                    user.setProfile(p);
                    Gson gson = new Gson();
                    user.setId(null);
                    String json = gson.toJson(user);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    Log.d("JSON",json);
                    hub.getApiService().addUser(requestBody).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
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
