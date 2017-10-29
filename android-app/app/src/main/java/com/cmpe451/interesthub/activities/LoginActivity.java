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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("INFO","login activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button b=(Button) findViewById(R.id.button8);
        final EditText e = (EditText) findViewById(R.id.editText8);
        final EditText e2 = (EditText) findViewById(R.id.editText9);
        final TextView t = (TextView) findViewById(R.id.textView6);
        InterestHub hb = (InterestHub)getApplication();
        hb.getSessionController();
        final InterestHub hub = (InterestHub) getApplication();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                hub.getApiService().getUsers().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        Log.d("Login","aldık");

                        final List<User> groupList =  response.body();
                        for (int i = 0 ; i< groupList.size() ; i++  ){
                            if(groupList.get(i).getUsername().toString().equals(e.getText().toString())){
                                hub.getSessionController().setUser((User) response.body().get(i));
                                Intent intent= new Intent(view.getContext(), UserActivity.class);
                                startActivity(intent);
                            }
                        }
                        Log.d("error", "hocam error");
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });







                /*if(e.getText().toString().equals("Eric")){
                    if(e2.getText().toString().equals("1234")){
                        hub.getApiService().getUsers().enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Log.d("Login ", "Login response succesfull");

                                hub.getSessionController().setUser(response.body());
                                final List<String> groupList = response.body().getGroupListResponse();
                                for(int i = 0 ;i< groupList.size();i++){
                                    final int finalI = i;
                                    hub.getApiService().getSpesificGroup(groupList.get(i)).enqueue(new Callback<Group>() {
                                        @Override
                                        public void onResponse(Call<Group> call, Response<Group> response) {
                                            hub.getSessionController().getUser().addGroupList( response.body());
                                            if(finalI == groupList.size()-1){

                                                Log.d("SESSION ", "intent acılıyooor");
                                                Intent intent= new Intent(view.getContext(), UserActivity.class);
                                                startActivity(intent);

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Group> call, Throwable t) {

                                        }
                                    });

                                }



                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });




                    }
                }
*/
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(view.getContext(), SignUpActivity.class);
                startActivity(intent);


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
