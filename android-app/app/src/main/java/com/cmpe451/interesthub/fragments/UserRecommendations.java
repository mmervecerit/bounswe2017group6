package com.cmpe451.interesthub.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.GroupActivity;
import com.cmpe451.interesthub.activities.ProfileActivity;
import com.cmpe451.interesthub.activities.UserActivity;
import com.cmpe451.interesthub.adapters.UserAdapter;
import com.cmpe451.interesthub.adapters.UserCardListAdapter;
import com.cmpe451.interesthub.adapters.UserGroupListAdapter;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRecommendations.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserRecommendations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRecommendations extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView groupList;
    RecyclerView userList;
    InterestHub hub;
    List<Group> groupItems;
    List<User> userItems;
    private OnFragmentInteractionListener mListener;

    public UserRecommendations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRecommendations.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRecommendations newInstance(String param1, String param2) {
        UserRecommendations fragment = new UserRecommendations();
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
        View view = inflater.inflate(R.layout.fragment_user_events, container, false);
        groupList = view.findViewById(R.id.group_recomm_recycler_view);
        userList = view.findViewById(R.id.user_recomm_recycler_view);
        groupItems = new ArrayList<>();
        userItems = new ArrayList<>();

        hub = (InterestHub) getActivity().getApplication();

        hub.getApiService().getRecommGroup().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response!=null&& response.body()!=null)
                    groupItems=response.body();
                setAdapterGroups();
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });
        hub.getApiService().getRecommUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response!=null && response.body()!=null)
                    userItems = response.body();
                setAdapterUsers();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        return view;
    }
    public void setAdapterGroups(){
        final LinearLayoutManager ll = new LinearLayoutManager(((UserActivity) getActivity()));
        ll.setOrientation(LinearLayoutManager.VERTICAL);
        groupList.setLayoutManager(ll);
        UserGroupListAdapter.OnItemClickListener listener = new UserGroupListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("groupName", String.valueOf(groupItems.get(pos).getName()));
                intent.putExtra("groupId", groupItems.get(pos).getId());
                intent.putExtra("groupImg", groupItems.get(pos).getLogo());
                Log.d("STARTACTIVITY", String.valueOf(groupItems.get(pos).getName()) + " " + groupItems.get(pos).getId());
                startActivity(intent);
            }
        };
        final UserGroupListAdapter adapter = new UserGroupListAdapter(getContext(), groupItems, listener);
        groupList.setAdapter(adapter);
    }

    public void setAdapterUsers(){
        final LinearLayoutManager ll = new LinearLayoutManager(((UserActivity) getActivity()));
        ll.setOrientation(LinearLayoutManager.VERTICAL);
        userList.setLayoutManager(ll);
       UserCardListAdapter.OnItemClickListener listener = new UserCardListAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int pos) {
               Intent intent = new Intent(getContext(), ProfileActivity.class);
               intent.putExtra("userId", userItems.get(pos).getId());
               startActivity(intent);


           }
       };
        final UserCardListAdapter adapter = new UserCardListAdapter(getContext(), userItems, listener);
        userList.setAdapter(adapter);
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
