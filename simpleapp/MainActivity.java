package nantha91.com.simpleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nantha91.com.simpleapp.activity.About;
import nantha91.com.simpleapp.activity.DetailsActivity;
import nantha91.com.simpleapp.adapter.NavigationMenuAdapter;
import nantha91.com.simpleapp.adapter.SimpleAdapter;
import nantha91.com.simpleapp.fragment.HospitalFragment;
import nantha91.com.simpleapp.model.Category;
import nantha91.com.simpleapp.model.ContentEntry;


public class MainActivity extends ActionBarActivity implements HospitalFragment.onHospitalFragmentListener, AdapterView.OnItemClickListener {
    private ListView listview;
    private List<ParseObject> ob;
    private ProgressDialog mProgressDialog;
    private SimpleAdapter adapter;


    //TAGS
    private final String TAG = getClass().getSimpleName();
    private final String TAG_HOSPITAL_FRAGMENT = "Hospital";
    public static final String KEY_OBJECT = "DETAIL";
    public static final String KEY_CATEGORY = "CATEGORY";

    public static final String KEY_BYTE = "BYTE";
    public static String CATEGORY, LOCATION;


    //Fragment View
    private View mHospitalFragmentContainer;
    private View mLawsFragmentContainer;
    private View mHotelFragmentContainer;
    private View mRestaurantFragmentContainer;
    private View mPlacesFragmentContainer;
    private View mFitnessFragmentContainer;
    private View mLibrariesFragmentContainer;
    private View mFlightFragmentContainer;


    //Drawer Variables
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout layout;
    private TextView resultTitle;


    public static final String CONTENT_ENTRY_TABLE = "ContentEntry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initFragments();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    //initialization of fragments
    private void initFragments() {
        HospitalFragment hosQueryForm = new HospitalFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.hospital_fragment_container, hosQueryForm, TAG_HOSPITAL_FRAGMENT);
        transaction.commit();
        updateFragmentVisibility(0);
    }


    private void findViews() {
        listview = (ListView) findViewById(R.id.result_listview);
        listview.setOnItemClickListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_listview);
        layout = (LinearLayout) findViewById(R.id.layout);
        resultTitle = (TextView) findViewById(R.id.result_title);
        mDrawerList.setOnItemClickListener(this);
        //enable visibility of toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        //prepare drawerlistview
        prepareNavigationDrawer();

        mHospitalFragmentContainer = findViewById(R.id.hospital_fragment_container);
        mLawsFragmentContainer = findViewById(R.id.laws_fragment_container);
        mHotelFragmentContainer = findViewById(R.id.hotel_fragment_container);
        mRestaurantFragmentContainer = findViewById(R.id.restaurant_fragment_container);
        mPlacesFragmentContainer = findViewById(R.id.places_fragment_container);
        mFitnessFragmentContainer = findViewById(R.id.fitness_fragment_container);
        mLibrariesFragmentContainer = findViewById(R.id.libraries_fragment_container);
        mFlightFragmentContainer = findViewById(R.id.flight_fragment_container);

    }

    //DrawerListView filling up with content
    private void prepareNavigationDrawer() {
        //prepare list of items for navigation drawer
        ArrayList<String> navigationMenu = new ArrayList<String>();
        //  navigationMenu.add("Laws");
        navigationMenu.add("Hospital");
        navigationMenu.add("Hotel");
        navigationMenu.add("Restaurant");
        navigationMenu.add("Places");
        navigationMenu.add("Fitness");
        navigationMenu.add("Libraries");
        // navigationMenu.add("Flight");
        NavigationMenuAdapter adapter = new NavigationMenuAdapter(MainActivity.this, navigationMenu);
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name,
                R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // TODO Auto-generated method stub
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private final Category category;

        public RemoteDataTask(Category category) {
            this.category = category;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Fetching Data ...");
            // Set progressdialog message
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com
            CATEGORY = category.getCategoryName();
            LOCATION = category.getLocation();
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    MainActivity.CATEGORY);
            query.orderByAscending(ContentEntry.NAME);
            query.whereContains(ContentEntry.LOCATION, MainActivity.LOCATION);
             /*  query.whereContains(Hospital.MOBILE, hospital.getMobileno());
            query.whereContains(Hospital.SERVICES, hospital.getService());
            query.whereContains(Hospital.WEBSTIE, hospital.getWebsite());*/
            Log.v(TAG, "query string - " + query.toString());
            query.setLimit(1000);
            try {
                ob = query.find();
            } catch (com.parse.ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
          /*  listview = (ListView) findViewById(R.id.listview);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(MainActivity.this,
                    R.layout.listview_item);*/
            // Retrieve object "name" from Parse.com database
            final ArrayList<ContentEntry> contentEntries = new ArrayList<ContentEntry>();
            if (ob != null) {
                for (ParseObject country : ob) {
                    ContentEntry entry = new ContentEntry();
                    try {
                        //timings is only for
                        // Restaurant
                        // Places
                        // Libraries
                        // Fitness
                        entry.setTimings(getValue(country, ContentEntry.TIMINGS));
                        //cuisine only for Restaurant
                        entry.setCuisine(getValue(country, ContentEntry.CUISINE));
                        //maplink only for hospital
                        entry.setMaplink(getValue(country, ContentEntry.MAPLINK));
                        //review for Fitness,library,place
                        entry.setReviews(getValue(country, ContentEntry.REVIEWS));
                        entry.setName(getValue(country, ContentEntry.NAME));
                        entry.setAddress(getValue(country, ContentEntry.ADDRESS));
                        entry.setWebsite(getValue(country, ContentEntry.WEBSITE));
                        entry.setMobileno(getValue(country, ContentEntry.MOBILENO));
                       /* entry.setPhoto1(getImageFile(country, ContentEntry.PHOTO1));
                        entry.setPhoto1length(entry.getPhoto1() == null ? 0 : entry.getPhoto1().length);
                        entry.setPhoto2(getImageFile(country, ContentEntry.PHOTO2));
                        entry.setPhoto2length(entry.getPhoto2() == null ? 0 : entry.getPhoto2().length);
                        entry.setPhoto3(getImageFile(country, ContentEntry.PHOTO3));
                        entry.setPhoto3length(entry.getPhoto3() == null ? 0 : entry.getPhoto3().length);*/
                        contentEntries.add(entry);
                        resultTitle.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }


            Log.v(TAG, "total objects are =" + contentEntries.size());
            adapter = new SimpleAdapter(contentEntries, getBaseContext());

            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            listview.setOnItemClickListener(MainActivity.this);
        }

        private byte[] getImageFile(ParseObject object, String key) {
            byte[] result = null;
            try {
                result = object.getParseFile(key).getData();
            } catch (Exception e) {
                Log.e(TAG, "getimagefile");
                e.printStackTrace();
            }
            return result;
        }

        private String getValue(ParseObject obj, String key) {
            String result = null;
            try {
                result = obj.get(key).toString();
            } catch (NullPointerException e) {
                //  Log.e(TAG,"NullPointerException for key -"+key);
            }
            return result;
        }
    }


    private void prepareCurrentFragment() {

    }

    private void updateFragmentVisibility(int position) {
        //make all view visible gone
        mHospitalFragmentContainer.setVisibility(View.GONE);
        mLawsFragmentContainer.setVisibility(View.GONE);
        mHotelFragmentContainer.setVisibility(View.GONE);
        mRestaurantFragmentContainer.setVisibility(View.GONE);
        mPlacesFragmentContainer.setVisibility(View.GONE);
        mFitnessFragmentContainer.setVisibility(View.GONE);
        mLibrariesFragmentContainer.setVisibility(View.GONE);
        mFlightFragmentContainer.setVisibility(View.GONE);


        switch (position) {
            case 0:
                Log.v(TAG, "updateFragmentVisibility-" + TAG_HOSPITAL_FRAGMENT);
                mHospitalFragmentContainer.setVisibility(View.VISIBLE);
                break;
            default:
                mHospitalFragmentContainer.setVisibility(View.VISIBLE);
        }


    }




    /*
    onitemClick listener
     */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Log.v(TAG, "onItemclick");
        //change the title of actionbar
        switch (adapterView.getId()) {
            case R.id.drawer_listview:
                Log.v(TAG, "onItemclick - drawer_listview");
                setActionBarTitle(position);
                prepareCurrentFragment();
                updateFragmentVisibility(position);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.result_listview:
                Log.v(TAG, "onItemclick- result_listview");
                //result needs to open Details Activity page
                Intent detail = new Intent(this, DetailsActivity.class);
                detail.putExtra(KEY_OBJECT, adapter.getItem(position));
               /* ContentEntry entry = adapter.getItem(position);
                try {
                    detail.putExtra(ContentEntry.PHOTO1, ((byte[]) entry.getImageFiles().get(0).getData()));
                    detail.putExtra(ContentEntry.PHOTO2, ((byte[]) entry.getImageFiles().get(1).getData()));
                    detail.putExtra(ContentEntry.PHOTO3, ((byte[]) entry.getImageFiles().get(2).getData()));
                } catch (Exception e) {
                    Log.e(TAG, "parsefile exception");
                }*/
                startActivity(detail);
                break;

        }

    }


    //set title to the actionbar
    private void setActionBarTitle(int position) {

        try {
            getSupportActionBar().setTitle(((TextView) mDrawerList.getChildAt(position).findViewById(R.id.category_name)).getText());
        } catch (NullPointerException e) {
            Log.e(TAG, "nullpointer in setactionbartitle");
            e.printStackTrace();
        }
    }


    /*
      The following code contains all fragment listeners
     */

    @Override
    public void queryFromHospital(Category category) {

        ob = null;
        new RemoteDataTask(category).execute();
    }


    /*
     The default menu implementations
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
      /*  boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!isDrawerOpen);*/
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
