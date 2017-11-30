package com.cmpe451.interesthub.activities;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        final EditText groupTags;
        final EditText groupIcon;
        groupName = (EditText)findViewById(R.id.group_name);
        groupDes = (EditText)findViewById(R.id.group_desc);
        groupIcon = (EditText)findViewById(R.id.group_icon);
        groupTags = (EditText) findViewById(R.id.tagText);
        final Button addTag = (Button)findViewById(R.id.addTagButton);
        final ListView tags = (ListView) findViewById(R.id.tagList);
        final List<String> tagList = new ArrayList<>();
        final List<SearchResult> resultTag = new ArrayList<>();
        final String tagUrl = "https://limitless-sands-55256.herokuapp.com/https://www.wikidata.org/w/api.php?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=";
        final List<Search> myTags = new ArrayList<>();
        final List<Search> adapterTags = new ArrayList<>();
        tags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", String.valueOf(position));
                if(position<myTags.size()) return;
                if(position<adapterTags.size()) {

                    Search s = adapterTags.remove(position);
                    adapterTags.add(myTags.size(),s);
                    myTags.add(s);
                    TagAdapter arrayAdapter = new TagAdapter(getBaseContext(), android.R.layout.simple_list_item_1, adapterTags, myTags.size());
                    tags.setAdapter(arrayAdapter);
                }
            }
        });

        groupTags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(groupTags.getText().toString().equals("")) return;
                String tag = groupTags.getText().toString();
                String url = tagUrl+tag;
                hub.getApiService().getTags(url).enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        if(response==null || response.body()==null) return;
                        adapterTags.clear();
                        int myTagsSize = myTags.size();
                        for(Search s:myTags)
                            adapterTags.add(s);

                        for(Search s : response.body().getSearch()){
                            adapterTags.add(s);

                       }
                        TagAdapter arrayAdapter = new TagAdapter(getBaseContext(),android.R.layout.simple_list_item_1, adapterTags,myTagsSize);
                        tags.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {

                    }
                });
            }
        });
         /*addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupTags.getText().toString().equals("")) return;
                String tag = groupTags.getText().toString();
                String url = tagUrl+tag;
                hub.getApiService().getTags(url).enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        if(response==null || response.body()==null) return;
                        for(Search s : response.body().getSearch()){
                            if(s!=null)
                                Log.d(s.getLabel(),s.getDescription());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {

                    }
                });
                tagList.add(groupTags.getText().toString());
                groupTags.setText("");

                ArrayAdapter arrayAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, android.R.id.text1, tagList);
                tags.setAdapter(arrayAdapter);

            }
        });*/
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
                g.setLogo("http://cohenwoodworking.com/wp-content/uploads/2016/09/image-placeholder-500x500.jpg");
                g.setCover_photo("http://cohenwoodworking.com/wp-content/uploads/2016/09/image-placeholder-500x500.jpg");
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
                for(Tag t : grouptaglist){
                    Log.d(t.getLabel(),"tag");
                }


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
