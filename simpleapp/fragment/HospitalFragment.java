package nantha91.com.simpleapp.fragment;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import nantha91.com.simpleapp.R;
import nantha91.com.simpleapp.model.Category;

import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link HospitalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HospitalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HospitalFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getName();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";

    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;

    private PlusOneButton mPlusOneButton;
    private Button mSubmit;
    private Spinner name, service, mobileno, website, location;
    private String qName, qWebsite, qMobileNo, qService, qLocation;


    private onHospitalFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HospitalFragment newInstance(String param1, String param2) {
        HospitalFragment fragment = new HospitalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HospitalFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        mSubmit = (Button) view.findViewById(R.id.query_button);
        mSubmit.setOnClickListener(HospitalFragment.this);
        name = (Spinner) view.findViewById(R.id.name);
        location = (Spinner) view.findViewById(R.id.location);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        // mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onHospitalFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface onHospitalFragmentListener {
        // TODO: Update argument type and name
        public void queryFromHospital(Category hospital);
    }


    @Override
    public void onClick(View view) {
        Log.v(TAG, "onClick");
        if (networkConnection()) {
            if (mListener != null && validateEntry()) {
                Category category = new Category();
                category.setCategoryName(qName);
                category.setLocation(qLocation);
                mListener.queryFromHospital(category);
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_network_connection), Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateEntry() {
        qName = qMobileNo = qService = qWebsite = "";
        qName = name.getSelectedItem().toString();
        qLocation = location.getSelectedItem().toString();
       /* qWebsite = website.getText().toString().trim();
        qMobileNo = mobileno.getText().toString().trim();*/
        Log.v(TAG, "category and location is -" + qName + "-" + qLocation);

        int categoryPos = name.getSelectedItemPosition();
        int locationPos = location.getSelectedItemPosition();
        if (categoryPos == 0) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.select_category_error), Toast.LENGTH_SHORT).show();
            return false;
        } else if (locationPos == 0) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.select_location_error), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }


    private boolean networkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getBaseContext().getSystemService(getActivity().getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
