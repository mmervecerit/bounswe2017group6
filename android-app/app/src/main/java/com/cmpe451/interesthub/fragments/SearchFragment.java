package com.cmpe451.interesthub.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.GroupActivity;
import com.cmpe451.interesthub.activities.ProfileActivity;
import com.cmpe451.interesthub.adapters.CustomAdapter;
import com.cmpe451.interesthub.adapters.SearchGroupAdapter;
import com.cmpe451.interesthub.adapters.SearchUserAdapter;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayAdapter groupAdapter;
    private static ArrayAdapter userAdapter;
    ListView userListView;
    ListView groupListView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private InterestHub hub;
    private HashMap<String,Group> groupHashMap;
    private HashMap<String,User> userHashMap;
    final List<Group> groupList = new ArrayList<>();
    final List<User> userList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    List<String> groups,users;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        hub = (InterestHub) getActivity().getApplication();






    }

    private void setGroupAdapter(View view) {
        groups = new ArrayList<String>();
        for(int i = 0; i< groupList.size(); i++)
            groups.add(groupList.get(i).getName());

        ListView listView = view.findViewById(R.id.list_view_search_groups);
        groupAdapter= new CustomAdapter(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, groups);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Group selected = groupHashMap.get(parent.getAdapter().getItem(pos));
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("groupName", String.valueOf(selected.getName()));
                intent.putExtra("groupId", selected.getId());
                intent.putExtra("groupImg", selected.getLogo());
                startActivity(intent);


            }
        });
        listView.setAdapter(groupAdapter);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20*groupAdapter.getCount());
        listView.setLayoutParams(params);



    }
    private void setUserAdapter(View view) {
        users = new ArrayList<String>();
        for(int i = 0; i< userList.size(); i++)
            users.add(userList.get(i).getUsername());
        ListView listView = view.findViewById(R.id.list_view_search_users);
        userAdapter= new CustomAdapter(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, users);
        listView.setAdapter(userAdapter);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20*userAdapter.getCount());
        listView.setLayoutParams(params);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_search, container, false);


        userListView = view.findViewById(R.id.list_view_search_users);
        groupListView = view.findViewById(R.id.list_view_search_groups);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selected = userList.get(position);
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("userId", selected.getId());
                startActivity(intent);
            }
        });
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group selected = groupList.get(position);
                Intent intent = new Intent(getContext(),GroupActivity.class);
                intent.putExtra("groupName", String.valueOf(selected.getName()));
                intent.putExtra("groupId", selected.getId());
                intent.putExtra("groupImg", selected.getLogo());
                startActivity(intent);
            }
        });
        return view;

    }

    private void setGroupHashMap(List<Group> list) {
        groupHashMap = new HashMap<String,Group>();
        for(Group g : list)
            groupHashMap.put(g.getName(),g);
    }

    private void setUserHashMap(List<User> list) {
        userHashMap = new HashMap<String ,User>();
        for(User g : list)
            userHashMap.put(g.getUsername(),g);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public  void changeAdapter(String text){
        if(text.length()<1) return;
       final String user = "http://34.209.230.231:8000/search/users/?q=" + text;

        String group = "http://34.209.230.231:8000/search/groups/?q=" + text;

        hub.getApiService().searchGroups(group).enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response == null || response.body() == null) return;
               groupList.clear();
                for(Group g:response.body()) groupList.add(g);
                SearchGroupAdapter adapter = new SearchGroupAdapter(getContext(),android.R.layout.simple_list_item_1,groupList);

                groupListView.setAdapter(adapter);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20*adapter.getCount());
                groupListView.setLayoutParams(params);


            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });

        hub.getApiService().searchUser(user).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response == null || response.body() == null) return;
                userList.clear();
                for(User s:response.body()) userList.add(s);
                SearchUserAdapter adapter = new SearchUserAdapter(getContext(),android.R.layout.simple_list_item_1,userList);

                userListView.setAdapter(adapter);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20*adapter.getCount());
                userListView.setLayoutParams(params);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

}
