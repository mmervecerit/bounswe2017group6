package com.cmpe451.interesthub.activities.baseActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.LoginActivity;
import com.cmpe451.interesthub.controllers.SessionController;
import com.cmpe451.interesthub.services.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eren on 26.10.2017.
 */

public class BaseActivity  extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
                return true;
            case R.id.menu_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.group_cover_change:
                Intent inten = new Intent();
                inten.setType("image/*");
                inten.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(inten, "Select Picture"), PICK_IMAGE);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Log.d("DATA","IMAGE");
        }
    }

}
