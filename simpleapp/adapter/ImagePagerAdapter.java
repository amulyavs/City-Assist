package nantha91.com.simpleapp.adapter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import nantha91.com.simpleapp.MainActivity;
import nantha91.com.simpleapp.fragment.ImageFragment;
import nantha91.com.simpleapp.model.Category;
import nantha91.com.simpleapp.model.ContentEntry;

/**
 * Created by nantha on 1/5/15.
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private final String TAG = getClass().getName();
    private List<ParseObject> ob;
    private String name;


    public ImagePagerAdapter(FragmentManager fragmentManager, String name) {
        super(fragmentManager);
        this.name = name;


    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        fragment = new ImageFragment(name,getKey(position));
        return fragment;
    }

    private String getKey(int i) {
        switch (i) {
            case 0:
                return ContentEntry.PHOTO1;
            case 1:
                return ContentEntry.PHOTO2;
            case 2:
                return ContentEntry.PHOTO3;

        }
        return null;
    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private final String key;
        private byte[] photo;

        public RemoteDataTask(String key) {
            this.key = key;
        }

        public void setPhoto(byte[] photo) {
            this.photo = photo;
        }

        public byte[] getPhoto() {
            return photo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }




    }


    @Override
    public int getCount() {
        return 3;
    }
}
