package com.cmpe451.interesthub.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.GroupFragmentsAdapter;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.Message;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends BaseActivity {

    private final int PICK_PHOTO_FOR_COVER = 2;
    private final int PICK_PHOTO_FOR_ICON = 3;
    public InterestHub hub ;
    Long groupId;
    String groupName,groupImg,groupCover;
    List<Content> contentList;
    RecyclerView contentView;
    private TabLayout tabLayout;
    private GroupFragmentsAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        hub = (InterestHub) getApplication();

        if(getIntent().getExtras().getString("groupName")!=null){
            Log.d("Group activity started with: ", (String) getIntent().getExtras().getString("groupName"));
            groupId = getIntent().getExtras().getLong("groupId");
            if(hub.getSessionController().getGroups()!=null){
                for(Group g : hub.getSessionController().getGroups()){
                    if(g.getId() == groupId){
                        groupCover = g.getCover_photo();
                        groupImg = g.getLogo();
                    }
                }
            }
            groupName = getIntent().getExtras().getString("groupName");

            setTitle(groupName);
            Log.d("Group activity started with: ", String.valueOf(groupId));
        }
        img = (ImageView) findViewById(R.id.group_profile_img);

        Picasso.Builder builder = new Picasso.Builder(getBaseContext());
        builder.listener(new Picasso.Listener()
        {


            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                Log.d("IMAGE",exception.toString());
            }
        });
        if(groupCover!=null && !groupCover.equals(null) && !groupCover.equals(""))
            builder.build().load(groupCover).into(img);
        //img.setScaleType(ImageView.ScaleType.FIT_CENTER);

        FloatingActionButton joinfab = (FloatingActionButton) findViewById(R.id.joinFab);
        TextView joinfabText = (TextView) findViewById(R.id.joinFabtext);
        FloatingActionButton leavefab = (FloatingActionButton) findViewById(R.id.leaveFab);
        TextView leavefabText = (TextView) findViewById(R.id.leaveFabtext);

        checkJoinFab(joinfab,joinfabText,leavefab,leavefabText);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.ViewPager_group);
        viewPagerAdapter = new GroupFragmentsAdapter(getSupportFragmentManager(),groupId,groupName);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final TabLayout.Tab posts = tabLayout.newTab();
        final TabLayout.Tab users = tabLayout.newTab();
        final TabLayout.Tab info = tabLayout.newTab();

        posts.setText("Posts");
        users.setText("Users");
        info.setText("About");

        tabLayout.addTab(posts,0);
        tabLayout.addTab(users,1);
        tabLayout.addTab(info,2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setTabTextColors(Color.DKGRAY,Color.DKGRAY);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        hub = (InterestHub) getApplication();
        hub.getApiService().getGroupContents(groupId).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentList = new ArrayList<Content>();
                Log.d("SUCCSEFUL","calling group coıntents");
                if(response.body()==null) return;
                for(Content c : response.body()){
                   contentList.add(c);
                    if(c.getComponents()==null || c.getComponents().size()==0)
                        Log.d("null","null component");

                }
                //setPost();

            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.d("FAIL","failuer when calilng group contents");
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.groupoptions, menu);

        return true;
    }
    public void checkJoinFab(final FloatingActionButton joinFab, final TextView joinFabText,final FloatingActionButton leaveFab, final TextView leaveFabText){
        if(hub.getSessionController().isGroupsSet()) {
            for (Group g : hub.getSessionController().getGroups()) {
                if (groupId == g.getId()) {
                    joinFab.setVisibility(View.GONE);
                    joinFabText.setVisibility(View.GONE);
                    leaveFab.setVisibility(View.VISIBLE);
                    leaveFabText.setVisibility(View.VISIBLE);
                    leaveFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hub.getApiService().leaveGroup(groupId).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    hub.getSessionController().updateGroups(hub,getIntent(),getBaseContext());

                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {

                                }
                            });
                        }
                    });
                    return;
                }
            }
            leaveFab.setVisibility(View.GONE);
            leaveFabText.setVisibility(View.GONE);
            joinFab.setVisibility(View.VISIBLE);
            joinFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hub.getApiService().joinGroup(groupId).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {

                            hub.getSessionController().updateGroups(hub,getIntent(),getBaseContext());
                            //startActivity(getIntent());


                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(getBaseContext(),UserActivity.class);
                startActivity(intent);
                return true;
            case R.id.group_cover_change:
                Log.d("COVER","GROUP");
                pickImage(PICK_PHOTO_FOR_COVER);
                return true;
            case R.id.group_icon_change:
                Log.d("COVER","GROUP");
                pickImage(PICK_PHOTO_FOR_ICON);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void pickImage(int id) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, id);
    }
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_COVER && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            Uri uri = data.getData();
            String realuri = getRealPathFromURI(uri);

            File file = new File(realuri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            hub.getApiService().updateGroupCoverPhoto(groupId,body).enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    hub.getSessionController().updateGroups(hub,getIntent(),getBaseContext());
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });

        }
        else if (requestCode == PICK_PHOTO_FOR_ICON && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            Uri uri = data.getData();
            String realuri = getRealPathFromURI(uri);

            File file = new File(realuri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            hub.getApiService().updateGroupIconPhoto(groupId,body).enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    hub.getSessionController().updateGroups(hub,getIntent(),getBaseContext());
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });

        }
    }

    public void settitle(String name){
        setTitle(name);
    }


}
