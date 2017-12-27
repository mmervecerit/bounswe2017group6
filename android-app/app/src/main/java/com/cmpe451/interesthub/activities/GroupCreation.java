package com.cmpe451.interesthub.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.adapters.CustomAdapter;
import com.cmpe451.interesthub.adapters.TagAdapter;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.Message;
import com.cmpe451.interesthub.models.Tag;
import com.cmpe451.interesthub.models.wikiDataModels.Search;
import com.cmpe451.interesthub.models.wikiDataModels.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final InterestHub hub = (InterestHub) getApplication();
        final EditText groupName;
        final EditText groupDes;
        final EditText groupIcon;

        final LinearLayout taglistParent = (LinearLayout) findViewById(R.id.taglistparent);
        groupName = (EditText)findViewById(R.id.group_name);
        groupDes = (EditText)findViewById(R.id.group_desc);
        groupIcon = (EditText)findViewById(R.id.group_icon);
        final Button addTag = (Button)findViewById(R.id.addTagButton);
        final ListView tags = (ListView) findViewById(R.id.tagList);
        final List<String> tagList = new ArrayList<>();
        final List<SearchResult> resultTag = new ArrayList<>();
        final String tagUrl = "https://limitless-sands-55256.herokuapp.com/https://www.wikidata.org/w/api.php?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=";
        final List<Search> myTags = new ArrayList<>();
        final List<Search> adapterTags = new ArrayList<>();

        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupCreation.this);
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
                        tags.setAdapter(arrayAdapter);

                        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100*myTags.size());
                        taglistParent.setLayoutParams(mParam);


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
                        if(s.toString().length()<1) return;
                        String url = tagUrl+s.toString();
                        hub.getApiService().getTags(url).enqueue(new Callback<SearchResult>() {
                            @Override
                            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                                if(response==null || response.body()==null ||response.body().getSearch()==null) return;
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
        tags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", String.valueOf(position));

                myTags.remove(position);
                TagAdapter arrayAdapter = new TagAdapter(getBaseContext(),android.R.layout.simple_list_item_1, myTags,myTags.size());
                tags.setAdapter(arrayAdapter);

                LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100*myTags.size());
                taglistParent.setLayoutParams(mParam);

            }
        });


        final Button createButton = (Button)findViewById(R.id.group_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createButton.setEnabled(false);
                if(groupName.getText().toString().equals("") )
                {
                    Toast toast = Toast.makeText(GroupCreation.this, "Enter Group Name", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }if(groupDes.getText().toString().equals("") )
                {

                    Toast toast = Toast.makeText(GroupCreation.this, "Enter Group Description", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                RadioButton public_group = (RadioButton) findViewById(R.id.group_public);
                Group g = new Group();
                g.setDesc(groupDes.getText().toString());
                g.setIs_public(public_group.isChecked() ?true:false);
                g.setName(groupName.getText().toString());
                g.setLogo(null);
                g.setCover_photo(null);
                List<Tag> grouptaglist = new ArrayList<Tag>();
                g.setId(null);
                for(int i = 0;i<myTags.size();i++){
                    Tag t = new Tag();
                    t.setDescription(myTags.get(i).getDescription());
                    t.setLabel(myTags.get(i).getLabel());
                    t.setUrl("http:" + myTags.get(i).getUrl());
                    grouptaglist.add(t);
                }

                g.setTags(grouptaglist);


                Gson gson = new GsonBuilder().create();

                String json = gson.toJson(g);
                Log.d("JSON",json);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                hub.getApiService().addGroup(requestBody).enqueue(new Callback<Group>() {
                    @Override
                    public void onResponse(Call<Group> call, Response<Group> response) {
                        Toast toast = Toast.makeText(GroupCreation.this, groupName.getText() + " is created!", Toast.LENGTH_SHORT);
                        toast.show();
                        hub.getApiService().joinGroup(response.body().getId()).enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {

                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {

                            }
                        });
                        Intent intent= new Intent(getBaseContext(), UserActivity.class);
                        startActivity(intent);
                        createButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<Group> call, Throwable t) {
                        Toast toast = Toast.makeText(GroupCreation.this, "An error occured!", Toast.LENGTH_SHORT);
                        toast.show();
                        createButton.setEnabled(true);
                    }
                });



              /* hub.getApiService().addGroup(groupName.getText().toString(),
                       groupDes.getText().toString(),groupIcon.getText().toString()).enqueue(new Callback<Group>() {
                   @Override
                   public void onResponse(Call<Group> call, Response<Group> response) {
                       Toast toast = Toast.makeText(GroupCreation.this, groupName.getText() + " is created!", Toast.LENGTH_SHORT);
                       toast.show();
                       Intent intent= new Intent(GroupCreation.this, UserActivity.class);
                       startActivity(intent);
                       createButton.setEnabled(true);
                   }
                   @Override
                   public void onFailure(Call<Group> call, Throwable t) {
                       Toast toast = Toast.makeText(GroupCreation.this, "An error occured!", Toast.LENGTH_SHORT);
                       toast.show();
                       createButton.setEnabled(true);
                   }
               });*/
            }
        });
    }

}
