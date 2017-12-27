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
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;
import com.cmpe451.interesthub.models.Token;
import com.cmpe451.interesthub.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button b=(Button) findViewById(R.id.button8);
        final EditText e = (EditText) findViewById(R.id.editText8);
        final EditText e2 = (EditText) findViewById(R.id.editText9);
        final TextView t = (TextView) findViewById(R.id.textView6);


        //
        /*
        Content request exmaple


            hb.getApiService().getContentList().enqueue(new Callback<List<Content>>() {
                @Override
                public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                    for(Content c : response.body()){
                        ContentType ctype =c.getContentType();
                        for(Component cc:c.getComponents()){
                            Log.d(String.valueOf(c.getId()),cc.getComponent_type());
                        }

                        Log.d(String.valueOf(c.getId()),ctype.getName());
                        Log.d(c.getOwner().getUsername(),c.getCreatedDate()+ " " + c.getModifiedDate());

                    }
                }

                @Override
                public void onFailure(Call<List<Content>> call, Throwable t) {

                }
            });
          */
        //

        final InterestHub hub = (InterestHub) getApplication();
       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               hub.getApiService().login(e.getText().toString(),e2.getText().toString()).enqueue(new Callback<Token>() {
                   @Override
                   public void onResponse(Call<Token> call, Response<Token> response) {
                      if(response==null || response.body() == null || response.body().getToken().equals(null))
                      {
                          Log.d("TOKEN","ERROR");
                          Toast toast = Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG);
                          toast.show();
                          return;
                      }
                      else{
                          hub.getSessionController().setToken(response.body().getToken());
                          Log.d("TOKEN",response.body().getToken());
                          hub.authApiService(hub.getSessionController().getToken());
                          hub.getApiService().getMe().enqueue(new Callback<User>() {
                              @Override
                              public void onResponse(Call<User> call, Response<User> response) {
                                  hub.getSessionController().setUser(response.body());

                                  Intent intent= new Intent(view.getContext(), UserActivity.class);
                                  startActivity(intent);
                                  return;
                              }

                              @Override
                              public void onFailure(Call<User> call, Throwable t) {

                              }
                          });
                          /*
                          hub.getApiService().getUsers().enqueue(new Callback<List<User>>() {
                              @Override
                              public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                  for(User u : response.body()){
                                      if(u.getUsername().equals(e.getText().toString())){
                                          hub.getSessionController().setUser(u);
                                          Intent intent= new Intent(view.getContext(), UserActivity.class);
                                          startActivity(intent);
                                          return;
                                      }
                                  }
                              }

                              @Override
                              public void onFailure(Call<List<User>> call, Throwable t) {

                              }
                          });
                          */
                          /*
                          User user = new User();
                          user.setUsername(e.getText().toString());
                          user.setId(1);
                          user.setEmail("admin@interesthub.com");
                          hub.getSessionController().setUser(user);


                            */

                      }
                   }

                   @Override
                   public void onFailure(Call<Token> call, Throwable t) {

                   }
               });
           }
       });
       /* b.setOnClickListener(new View.OnClickListener() {
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

                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast toast = Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });


            }
        });

        */
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
