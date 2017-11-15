package com.cmpe451.interesthub.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.UserActivity;
import com.cmpe451.interesthub.adapters.UserTimelineListAdapter;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView postList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserTimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserTimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTimelineFragment newInstance(String param1, String param2) {
        UserTimelineFragment fragment = new UserTimelineFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_timeline, container, false);

        final LinearLayoutManager ll = new LinearLayoutManager(((UserActivity)getActivity()));
        ll.setOrientation(LinearLayoutManager.VERTICAL);


        UserTimelineListAdapter adapter = new UserTimelineListAdapter(getActivity().getApplicationContext());

        postList = (RecyclerView)view.findViewById(R.id.recycler_view);
        postList.setLayoutManager(ll);

        postList.setAdapter(adapter);

        final InterestHub hub = (InterestHub) ((UserActivity) getActivity()).getApplication();
        hub.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                final User user = response.body().get(0);
                Log.d(user.getUsername(),user.getEmail());


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("USER RESPONSE","ERROR");
            }
        });

        return view;
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
}
