package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.adapters.TemplateListAdapter;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;
import com.cmpe451.interesthub.models.Tag;
import com.cmpe451.interesthub.models.TypeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewContent extends AppCompatActivity {

    Long groupId;
    String groupName;
    RecyclerView templateList ;
    RelativeLayout template;
    List<ContentType> Clist;

    InterestHub hub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hub = (InterestHub) getApplication();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        template = (RelativeLayout) findViewById(R.id.template_base);
        Clist = new ArrayList<ContentType>();
        groupId = getIntent().getExtras().getLong("groupId");
        groupName = getIntent().getExtras().getString("groupName");
        setTitle("Post on " + groupName);
        templateList = (RecyclerView) findViewById(R.id.content_list_layout);
        InterestHub hub = (InterestHub) getApplication();

        hub.getApiService().getGroupContentTypes(groupId).enqueue(new Callback<List<ContentType>>() {
            @Override
            public void onResponse(Call<List<ContentType>> call, Response<List<ContentType>> response) {
                List<String> list = new ArrayList<String>();
                for(ContentType c : response.body()){
                    list.add(c.getName());
                    Clist.add(c);
                }
                setTemplateList(list);
            }

            @Override
            public void onFailure(Call<List<ContentType>> call, Throwable t) {

            }
        });


    }

    public void setTemplateList(List<String> list){
        TemplateListAdapter.OnItemClickListener listener = new TemplateListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("clicked", String.valueOf(pos));
                if(pos< Clist.size())
                    changeLayout(pos);
                else
                    newTemplate();
            }
        };
        TemplateListAdapter adapter = new TemplateListAdapter(list,listener);
        final LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        templateList.setLayoutManager(ll);
        templateList.setAdapter(adapter);



    }

    private void newTemplate() {
        template.removeAllViews();
        final LinearLayout l = (LinearLayout) getLayoutInflater().inflate(R.layout.template_creation_layout,null);
        final LinearLayout rowHolder = l.findViewById(R.id.template_row);
        LinearLayout singleRow = (LinearLayout) getLayoutInflater().inflate(R.layout.template_creation_row,null);
        Spinner spinner = (Spinner) singleRow.findViewById(R.id.template_component_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.template_components, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        rowHolder.addView(singleRow);
        Button button = l.findViewById(R.id.template_add_row_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout singleRow = (LinearLayout) getLayoutInflater().inflate(R.layout.template_creation_row,null);
                rowHolder.addView(singleRow);
                Spinner spinner = (Spinner) singleRow.findViewById(R.id.template_component_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                        R.array.template_components, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                if(singleRow.getParent()!=null) ((ViewGroup)singleRow.getParent()).removeView(singleRow);
                rowHolder.addView(singleRow);
            }
        });
        final Button button2 = l.findViewById(R.id.template_create);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                button2.setEnabled(false);
                ContentType newContentType = new ContentType();
                newContentType.setName(((EditText)l.findViewById(R.id.template_name)).getText().toString());
                List<String> names = new ArrayList<String>();
                List<String> fields = new ArrayList<String>();
                for(int i =0;i<rowHolder.getChildCount();i++){
                    LinearLayout row = (LinearLayout) rowHolder.getChildAt(i);
                    EditText t = (EditText) row.getChildAt(0);
                    Spinner s = (Spinner) row.getChildAt(1);

                    names.add(t.getText().toString());
                    if(s.getSelectedItem().toString().equals("Text"))
                        fields.add("text");
                    else if(s.getSelectedItem().toString().equals("Long Text"))
                        fields.add("longtext");
                    else if(s.getSelectedItem().toString().equals("Image"))
                        fields.add("image");
                    else if(s.getSelectedItem().toString().equals("Video"))
                        fields.add("video");
                    else if(s.getSelectedItem().toString().equals("Date"))
                        fields.add("datetime");
                    else fields.add("number");


                }
                newContentType.setComponent_names(names);
                newContentType.setComponents(fields);
                Gson gson = new Gson();
                newContentType.setId(null);
                String json = gson.toJson(newContentType);
                Log.d("json" ,json);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                hub.getApiService().postNewTemplate(groupId,requestBody).enqueue(new Callback<ContentType>() {
                    @Override
                    public void onResponse(Call<ContentType> call, Response<ContentType> response) {
                        Toast toast =  Toast.makeText(getBaseContext(),"New Template Created",Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<ContentType> call, Throwable t) {
                        Toast toast =  Toast.makeText(getBaseContext(),"New Template Failed",Toast.LENGTH_LONG);
                        toast.show();
                        button2.setEnabled(true);
                    }
                });


            }
        });
        Toolbar.LayoutParams param = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        l.setLayoutParams(param);



        template.addView(l);
    }

    public void changeLayout(int pos){

        template.removeAllViews();
        LinearLayout lay = new LinearLayout(getBaseContext());
        Toolbar.LayoutParams param = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.setLayoutParams(param);
        final ContentType c = Clist.get(pos);
        lay.setOrientation(LinearLayout.VERTICAL);
        final List<EditText> inputs = new ArrayList<>(c.getComponents().size());
        for(String s : c.getComponent_names()){
            LinearLayout l = new LinearLayout(getBaseContext());
            l.setOrientation(LinearLayout.HORIZONTAL);
            TextView t = new TextView(getBaseContext());
            t.setText(s);
            l.addView(t);
            EditText e = new EditText(getBaseContext());
            inputs.add(e);
            Toolbar.LayoutParams ll = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            e.setLayoutParams(ll);
            l.addView(e);
            lay.addView(l);
        }
        Button b = new Button(getBaseContext());
        b.setText("Post");
        b.setGravity(Gravity.CENTER);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Content content  = new Content();
                content.setContentType(c);
                content.setOwner(hub.getSessionController().getUser());
                content.setContent_type_id(c.getId());
                content.setOwner_id(content.getOwner().getId());
                List<Component> clist = new ArrayList<Component>();
                for(int i =0 ; i<inputs.size(); i++){
                    Component comp = new Component();
                    comp.setComponent_type(c.getComponents().get(i));
                    comp.setOrder(i+1);
                    TypeData t = new TypeData();
                    t.setData(inputs.get(i).getText().toString());
                    comp.setType_data(t);
                    clist.add(comp);

                }

                content.setComponents(clist);

                //TODO
                content.setTags(new ArrayList<Tag>());
                sendPost(content);

            }

        });
        lay.addView(b);
        template.addView(lay);


    }

    private void sendPost(Content content) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(content);
        Log.d("JSON",json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        hub.getApiService().postContent(groupId,requestBody).enqueue(new Callback<Content>() {

            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                Intent intent = new Intent(getBaseContext(), GroupActivity.class);
                intent.putExtra("groupName", groupName);
                intent.putExtra("groupId", groupId);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.d("Post ekleme"," BASARISIZ "+t.getMessage());
            }
        });
    }

}
