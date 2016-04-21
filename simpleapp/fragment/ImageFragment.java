package nantha91.com.simpleapp.fragment;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.util.List;

import nantha91.com.simpleapp.MainActivity;
import nantha91.com.simpleapp.R;
import nantha91.com.simpleapp.model.ContentEntry;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View mView;
    private Account mAccount;
    private ImageView mImageView;
    private TextView mMessageView;
    private ProgressBar mProgressWheel;
    public Bitmap mBitmap = null;
    public String key, name;
    private List<ParseObject> ob;
    private static final String TAG = ImageFragment.class.getSimpleName();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String param1, String param2) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageFragment() {
        // Required empty public constructor
    }

    public ImageFragment(String name, String key) {
        this.name = name;
        this.key = key;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_image, container, false);
        mImageView = (ImageView) mView.findViewById(R.id.image);
        mImageView.setVisibility(View.GONE);
        mView.setOnTouchListener((View.OnTouchListener) getActivity());
        mMessageView = (TextView) mView.findViewById(R.id.message);
        mMessageView.setVisibility(View.GONE);
        mProgressWheel = (ProgressBar) mView.findViewById(R.id.progressWheel);
        mProgressWheel.setVisibility(View.VISIBLE);
        return mView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof View.OnTouchListener)) {
            throw new ClassCastException(activity.toString() +
                    " must implement " + View.OnTouchListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onStart() {
        super.onStart();
        BitmapLoader bl = new BitmapLoader(mImageView, mMessageView, mProgressWheel);
        bl.execute(new String[]{""});
    }

    @Override
    public void onDestroy() {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        super.onDestroy();
    }

    private class BitmapLoader extends AsyncTask<String, Void, Bitmap> {

        /**
         * Weak reference to the target {@link android.widget.ImageView} where the bitmap will be loaded into.
         * <p/>
         * Using a weak reference will avoid memory leaks if the target ImageView is retired from memory before the load finishes.
         */
        private final WeakReference<ImageView> mImageViewRef;

        /**
         * Weak reference to the target {@link android.widget.TextView} where error messages will be written.
         * <p/>
         * Using a weak reference will avoid memory leaks if the target ImageView is retired from memory before the load finishes.
         */
        private final WeakReference<TextView> mMessageViewRef;


        /**
         * Weak reference to the target {@link Progressbar} shown while the load is in progress.
         * <p/>
         * Using a weak reference will avoid memory leaks if the target ImageView is retired from memory before the load finishes.
         */
        private final WeakReference<ProgressBar> mProgressWheelRef;


        /**
         * Error message to show when a load fails
         */
        private int mErrorMessageId;


        /**
         * Constructor.
         *
         * @param imageView Target {@link ImageView} where the bitmap will be loaded into.
         */
        public BitmapLoader(ImageView imageView, TextView messageView, ProgressBar progressWheel) {
            mImageViewRef = new WeakReference<ImageView>(imageView);
            mMessageViewRef = new WeakReference<TextView>(messageView);
            mProgressWheelRef = new WeakReference<ProgressBar>(progressWheel);
        }


        @SuppressWarnings("deprecation")
        @SuppressLint({"NewApi", "NewApi", "NewApi"}) // to avoid Lint errors since Android SDK r20
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            byte[] file = null;
            if (params.length != 1) return result;
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    MainActivity.CATEGORY);
            //query.orderByDescending("_created_at");
            query.whereContains(ContentEntry.LOCATION, MainActivity.LOCATION);
            query.whereContains(ContentEntry.NAME, name);
           /*  query.whereContains(Hospital.SERVICES, hospital.getService());
            query.whereContains(Hospital.WEBSTIE, hospital.getWebsite());*/
            Log.v(TAG, "query string - " + query.toString());
            query.setLimit(10);
            try {
                ob = query.find();
            } catch (com.parse.ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            if (ob != null) {
                for (ParseObject country : ob) {
                    try {
                        file = (getImageFile(country, key));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
            String storagePath = params[0];
            try {
                // set desired options that will affect the size of the bitmap
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                options.inPurgeable = true;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
                    options.inPreferQualityOverSpeed = false;
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                    options.inMutable = false;
                }
                // make a false load of the bitmap - just to be able to read outWidth, outHeight and outMimeType
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(storagePath, options);

                int width = options.outWidth;
                int height = options.outHeight;
                int scale = 1;

                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                int screenWidth;
                int screenHeight;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
                    display.getSize(size);
                    screenWidth = size.x;
                    screenHeight = size.y;
                } else {
                    screenWidth = display.getWidth();
                    screenHeight = display.getHeight();
                }

                if (width > screenWidth) {
                    // second try to scale down the image , this time depending upon the screen size
                    scale = (int) Math.floor((float) width / screenWidth);
                }
                if (height > screenHeight) {
                    scale = Math.max(scale, (int) Math.floor((float) height / screenHeight));
                }
                options.inSampleSize = scale;

                // really load the bitmap
                options.inJustDecodeBounds = false; // the next decodeFile call will be real
                result = BitmapFactory.decodeByteArray(file, 0, file.length);
                //Log_OC.d(TAG, "Image loaded - width: " + options.outWidth + ", loaded height: " + options.outHeight);

                if (result == null) {
                    // mErrorMessageId = R.string.preview_image_error_unknown_format;
                    Log.e(TAG, "File could not be loaded as a bitmap: ");
                }

            } catch (OutOfMemoryError e) {
                // mErrorMessageId = R.string.preview_image_error_unknown_format;
                Log.e(TAG, "Out of memory occured for file ");

            } catch (NoSuchFieldError e) {
                // mErrorMessageId = R.string.common_error_unknown;
                Log.e(TAG, "Error from access to unexisting field despite protection; file ");

            } catch (Throwable t) {
                //  mErrorMessageId = R.string.common_error_unknown;
                Log.e(TAG, "Unexpected error loading ");
                t.printStackTrace();

            }
            mErrorMessageId = R.string.store_picture_message;
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            hideProgressWheel();
            if (result != null) {
                showLoadedImage(result);
            } else {
                showErrorMessage();
            }
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

        private void showLoadedImage(Bitmap result) {
            if (mImageViewRef != null) {
                final ImageView imageView = mImageViewRef.get();
                if (imageView != null) {
                    imageView.setImageBitmap(result);
                    imageView.setVisibility(View.VISIBLE);
                    mBitmap = result;
                } // else , silently finish, the fragment was destroyed
            }
            if (mMessageViewRef != null) {
                final TextView messageView = mMessageViewRef.get();
                if (messageView != null) {
                    messageView.setVisibility(View.GONE);
                } // else , silently finish, the fragment was destroyed
            }
        }

        private void showErrorMessage() {
            if (mImageViewRef != null) {
                final ImageView imageView = mImageViewRef.get();
                if (imageView != null) {
                    // shows the default error icon
                    imageView.setVisibility(View.VISIBLE);
                } // else , silently finish, the fragment was destroyed
            }
            if (mMessageViewRef != null) {
                final TextView messageView = mMessageViewRef.get();
                if (messageView != null) {
                    messageView.setText(mErrorMessageId);
                    messageView.setVisibility(View.VISIBLE);
                } // else , silently finish, the fragment was destroyed
            }
        }

        private void hideProgressWheel() {
            if (mProgressWheelRef != null) {
                final ProgressBar progressWheel = mProgressWheelRef.get();
                if (progressWheel != null) {
                    progressWheel.setVisibility(View.GONE);
                }
            }
        }

    }


}
