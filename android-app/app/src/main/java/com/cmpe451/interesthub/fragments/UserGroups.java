package com.cmpe451.interesthub.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.GroupActivity;
import com.cmpe451.interesthub.activities.GroupCreation;
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

    FloatingActionButton fab;
    RecyclerView groupViewList;

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
        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), GroupCreation.class);
                startActivity(intent);
            }
        });
        groupViewList = view.findViewById(R.id.user_group_recycler_view);
        final List<Group> groupList = new ArrayList<Group>();
        InterestHub hub = (InterestHub) ((UserActivity) getActivity()).getApplication();
        hub.getApiService().getGroup().enqueue(new Callback<List<Group>>() {
                    @Override
                    public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                        Log.d("GROUPRESPOnse","basarili");
                        if(!response.isSuccessful()){

                            Toast toast = Toast.makeText(getActivity(), "An error occurred!", Toast.LENGTH_SHORT);
                            toast.show();
                            return ;
                        }
                         for(Group d : response.body())
                                groupList.add(d);
                        final LinearLayoutManager ll = new LinearLayoutManager(((UserActivity)getActivity()));
                        ll.setOrientation(LinearLayoutManager.VERTICAL);
                        groupViewList.setLayoutManager(ll);
                        final UserGroupListAdapter adapter = new UserGroupListAdapter(getContext(),groupList);
                        groupViewList.setAdapter(adapter);
                        adapter.setClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int pos = groupViewList.indexOfChild(view);
                                Intent intent = new Intent(getContext(), GroupActivity.class);
                                intent.putExtra("groupName", String.valueOf(groupList.get(pos).getName()));
                                intent.putExtra("groupId", groupList.get(pos).getId());
                                Log.d("STARTACTIVITY",String.valueOf(groupList.get(pos).getName())+" "+groupList.get(pos).getId());
                                startActivity(intent);
                            }
                        });




                    }

                    @Override
                    public void onFailure(Call<List<Group>> call, Throwable t) {
                     }
        });
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