package com.cmpe451.interesthub.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.TagAdapter;
import com.cmpe451.interesthub.models.Interest;
import com.cmpe451.interesthub.models.Profile;
import com.cmpe451.interesthub.models.User;
import com.cmpe451.interesthub.models.wikiDataModels.Search;
import com.cmpe451.interesthub.models.wikiDataModels.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Button b4=(Button) findViewById(R.id.button4);
        Button interets=(Button) findViewById(R.id.addInterest);

        final EditText firstName = (EditText) findViewById(R.id.FirstName);
        final EditText secondName = (EditText) findViewById(R.id.SecondName);
        final EditText email = (EditText) findViewById(R.id.Email);
        final EditText password = (EditText) findViewById(R.id.Password);
        final EditText password2 = (EditText) findViewById(R.id.Password2);
        final EditText username = (EditText) findViewById(R.id.UserName);
        final ListView interestList = (ListView) findViewById(R.id.interestList);

        final EditText about = (EditText) findViewById(R.id.about);
        final EditText contact = (EditText) findViewById(R.id.contact);

        final String tagUrl = "https://limitless-sands-55256.herokuapp.com/https://www.wikidata.org/w/api.php?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=";

        final InterestHub hub = (InterestHub) getApplication();
        final List<Search> adapterTags = new ArrayList<>();
        final List<Search> myTags = new ArrayList<>();
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
                    for(Search s : myTags){
                        Interest interest = new Interest();
                        interest.setLabel(s.getLabel());
                        interest.setDescription(s.getDescription());
                        interest.setUrl(s.getDescription());
                        interest.setId(null);
                        interestList.add(interest);
                    }

                    p.setInterests(interestList);
                    p.setLastname(secondName.getText().toString());
                    user.setProfile(p);
                    user.setId(null);
                    Gson gson = new GsonBuilder().serializeNulls().create();

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
        interets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                final View layout = getLayoutInflater().inflate(R.layout.add_interest_layout, null);
                final Spinner spinner = layout.findViewById(R.id.interestSpinner);
                final EditText text = layout.findViewById(R.id.interestEditText);
                builder.setView(layout)
                .setTitle("My Interest")
                .setMessage("Write and choose from list");
                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                        //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                    }
                });


                builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       Search s = (Search) spinner.getSelectedItem();
                        myTags.add(s);
                        TagAdapter arrayAdapter = new TagAdapter(getBaseContext(),android.R.layout.simple_list_item_1, myTags,myTags.size());
                        interestList.setAdapter(arrayAdapter);
                        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100*myTags.size());
                        interestList.setLayoutParams(mParam);


                    }
                });
                text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(text.getText().toString().length()<1) return;
                        String url = tagUrl+text.getText().toString();
                        hub.getApiService().getTags(url).enqueue(new Callback<SearchResult>() {
                            @Override
                            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                                if(response==null || response.body()==null) return;
                                adapterTags.clear();

                                for(Search s : response.body().getSearch()){
                                    adapterTags.add(s);

                                }
                                TagAdapter arrayAdapter = new TagAdapter(getBaseContext(),android.R.layout.simple_list_item_1, adapterTags,0);
                                spinner.setAdapter(arrayAdapter);
                                spinner.performClick();
                            }

                            @Override
                            public void onFailure(Call<SearchResult> call, Throwable t) {

                            }
                        });
                    }
                });


                builder.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


}
