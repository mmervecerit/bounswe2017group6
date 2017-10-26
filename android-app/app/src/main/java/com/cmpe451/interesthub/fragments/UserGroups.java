package com.cmpe451.interesthub.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.interesthub.MainActivity;
import com.cmpe451.interesthub.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserGroups.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserGroups#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserGroups extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserGroups() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserGroups.
     */
    // TODO: Rename and change types and number of parameters
    public static UserGroups newInstance(String param1, String param2) {
        UserGroups fragment = new UserGroups();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_user_groups, container, false);
        ListView list = view.findViewById(R.id.userGroupListView);
        UserGroupListAdapter adapter = new UserGroupListAdapter((MainActivity)getActivity());
        list.setAdapter(adapter);
        //RequestHandler requestHandler = new RequestHandler();
        Log.d("REQUEST","SENDREQUEsT");
        //requestHandler.getHttpRequest("http://localhost:8000/dummy/");

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
class UserGroupListAdapter extends BaseAdapter{

    private static String[] text1={"Emre","Merve","Baris"};
    private static String[] text2={"Eren","Cerit","Ucakturk"};

    private Activity mainActivity;
    public UserGroupListAdapter(Activity mainActivity){
        this.mainActivity = mainActivity;
    }


    @Override
    public int getCount() {
        return text1.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mainActivity.getLayoutInflater().inflate(R.layout.user_group_list_layout,null );
        ImageView  image= (ImageView) view.findViewById(R.id.imageView_user_group_list);
        TextView  text1= (TextView) view.findViewById(R.id.textView_user_group_list);
        TextView text2= (TextView) view.findViewById(R.id.textView2_user_group_list);

        text1.setText(this.text1[i]);
        text2.setText(this.text2[i]);


        return view;
    }
}
