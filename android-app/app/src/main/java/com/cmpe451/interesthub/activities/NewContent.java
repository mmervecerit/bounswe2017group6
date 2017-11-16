package com.cmpe451.interesthub.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;
import com.cmpe451.interesthub.models.TypeData;
import com.cmpe451.interesthub.services.ContentRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewContent extends AppCompatActivity {

    Long groupId;
    String groupName;
    ListView templateList ;
    LinearLayout template;
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
        template = (LinearLayout) findViewById(R.id.template);
        Clist = new ArrayList<ContentType>();
        groupId = getIntent().getExtras().getLong("groupId");
        groupName = getIntent().getExtras().getString("groupName");
        setTitle("Post on " + groupName);
        templateList = (ListView) findViewById(R.id.content_list);
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
        templateList.setAdapter(new ArrayAdapter<String>(this,R.layout.post_component_text,R.id.post_header, list));
        templateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("clicked", String.valueOf(i));
                changeLayout(i);
            }
        });
    }

    public void changeLayout(int pos){
        template.removeAllViews();
        final ContentType c = Clist.get(pos);
        template.setOrientation(LinearLayout.VERTICAL);
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
            template.addView(l);
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
                for(int i =0 ; i<inputs.size(); i++){
                    Component comp = new Component();
                    comp.setComponent_type(c.getComponents().get(i));
                    comp.setOrder(i);
                    TypeData t = new TypeData();
                    t.setData(inputs.get(i).toString());
                    comp.setType_data(t);

                }

                sendPost(content);

            }

        });
        template.addView(b);


    }

    private void sendPost(Content content) {
        ContentRequest contentR = new ContentRequest();
        contentR.setContent(content);
        hub.getApiService().postContent(groupId,contentR).enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                Log.d("SUCCESS","asdas");

            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.d("uweuwuew","osas");
            }
        });
    }

}
