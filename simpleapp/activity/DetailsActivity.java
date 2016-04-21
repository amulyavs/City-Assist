package nantha91.com.simpleapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nantha91.com.simpleapp.MainActivity;
import nantha91.com.simpleapp.R;
import nantha91.com.simpleapp.adapter.ImagePagerAdapter;
import nantha91.com.simpleapp.model.ContentEntry;

public class DetailsActivity extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener {
    //TAGS
    private final String TAG = getClass().getName();

    private ContentEntry entry;
    private byte[] photo1, photo2, photo3;
    private TextView name, address, mobile, website, timings, cuisine, reviews;
    private LinearLayout layout_Timings, layout_Cuisine, layout_MapLink, layout_Reviews;
    private Button maplink;


    private ViewPager viewPager;
    private ImagePagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        entry = (ContentEntry) getIntent().getParcelableExtra(MainActivity.KEY_OBJECT);
        photo1 = getIntent().getExtras().getByteArray(ContentEntry.PHOTO1);
        photo2 = getIntent().getExtras().getByteArray(ContentEntry.PHOTO2);
        photo3 = getIntent().getExtras().getByteArray(ContentEntry.PHOTO3);
        if (entry == null) {
            Log.e(TAG, "null obj ");
        }
        findViews();
        fillDetailstoView();
        loadImageinViewPager();

    }

    //finding views
    private void findViews() {
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        mobile = (TextView) findViewById(R.id.mobileno);
        website = (TextView) findViewById(R.id.website);
        timings = (TextView) findViewById(R.id.timings);
        cuisine = (TextView) findViewById(R.id.cuisine);
        maplink = (Button) findViewById(R.id.btn_maplink);
        reviews = (TextView) findViewById(R.id.reviews);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        layout_Timings = (LinearLayout) findViewById(R.id.layout_timings);
        layout_Cuisine = (LinearLayout) findViewById(R.id.layout_cuisine);
        layout_MapLink = (LinearLayout) findViewById(R.id.layout_maplink);
        layout_Reviews = (LinearLayout) findViewById(R.id.layout_reviews);

        //maplink onclicklistener
        maplink.setOnClickListener(this);
    }

    private void fillDetailstoView() {

        try {
            name.setText(entry.getName());
            website.setText(entry.getWebsite());
            address.setText(entry.getAddress());
            mobile.setText(entry.getMobileno());
            //timings layout visibility
            if (entry.getTimings() != null) {
                layout_Timings.setVisibility(View.VISIBLE);
                timings.setText(entry.getTimings());
            }
            //cuisine layout visibility
            if (entry.getCuisine() != null) {
                layout_Cuisine.setVisibility(View.VISIBLE);
                cuisine.setText(entry.getCuisine());
            }
            //maplink layout visibility
            if (entry.getMaplink() != null) {
                if (entry.getMaplink().length() > 0) {
                    layout_MapLink.setVisibility(View.VISIBLE);
                    maplink.setTag(entry.getMaplink());
                }
            }
            //review layout
            if (entry.getReviews() != null) {
                if (entry.getReviews().length() > 0) {
                    layout_Reviews.setVisibility(View.VISIBLE);
                    reviews.setText(entry.getReviews());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception ");
            e.printStackTrace();
        }
    }

    //loading images
    private void loadImageinViewPager() {
        int[] resources = new int[]{R.drawable.images1, R.drawable.images2, R.drawable.images3};
        adapter = new ImagePagerAdapter(getSupportFragmentManager(), entry.getName());
        viewPager.setAdapter(adapter);
    }

    //onTouch


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.v(TAG, "onTouch  - called");
        return true;
    }

    //onclick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_maplink:
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse(maplink.getTag().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
