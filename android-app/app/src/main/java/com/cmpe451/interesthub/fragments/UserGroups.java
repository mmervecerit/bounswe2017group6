package com.cmpe451.interesthub.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.UserActivity;
import com.cmpe451.interesthub.adapters.UserGroupListAdapter;
import com.cmpe451.interesthub.models.Dummy;
import com.cmpe451.interesthub.models.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
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
        final ListView list = view.findViewById(R.id.userGroupListView);
        final List<Dummy> dummyList = new ArrayList<Dummy>();
        InterestHub hub = (InterestHub) ((UserActivity) getActivity()).getApplication();
        Log.d("RESPONSE","request is wanting from BACKEND");
        hub.getApiService().getDummy().enqueue(new Callback<List<Dummy>>() {
                    @Override
                    public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
                         for(Dummy d : response.body())
                                dummyList.add(d);
                        Log.d("RESPONSE","SUCCESFUL FROM BACKEND");
                        UserGroupListAdapter adapter = new UserGroupListAdapter((UserActivity)getActivity(),dummyList);
                        list.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<List<Dummy>> call, Throwable t) {
                        Log.d("RESPONSE","ERROR FROM BACKEND " +t.getMessage());
                     }
        });
        if(hub.getSessionController().isLoggedIn()) {
            Log.d("session", hub.getSessionController().getUser().getUsername());
            for (Group g : hub.getSessionController().getUser().getGroupList()) {
                Log.d("session", g.getName());
            }
        }
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