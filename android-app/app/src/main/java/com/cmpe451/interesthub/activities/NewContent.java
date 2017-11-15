package com.cmpe451.interesthub.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.interesthub.R;

import java.util.List;

public class NewContent extends AppCompatActivity {

    Long groupId;
    String groupName;
    ListView contentList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        groupId = getIntent().getExtras().getLong("groupId");
        groupName = getIntent().getExtras().getString("groupName");
        setTitle("Post on " + groupName);
        contentList = (ListView) findViewById(R.id.content_list);
        String[] contents= {"StandartPost1","StandartPost2","StandartPost3"};
        contentList.setAdapter(new ArrayAdapter<String>(this,R.layout.post_item2,R.id.post_header,contents));
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("clicked", String.valueOf(i));
            }
        });

    }

}
