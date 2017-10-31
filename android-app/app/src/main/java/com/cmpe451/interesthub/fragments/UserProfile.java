package com.cmpe451.interesthub.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.UserActivity;
import com.cmpe451.interesthub.adapters.UserFragmentsAdapter;
import com.cmpe451.interesthub.adapters.UserGroupListAdapter;
import com.cmpe451.interesthub.adapters.UserHomeGroupListAdapter;
import com.cmpe451.interesthub.adapters.UserHomeListAdapter;
import com.cmpe451.interesthub.adapters.UserProfileTabsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    InterestHub hub;
    Button userProfileGroups;
    Button userProfileTimeline;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserProfileTabsAdapter viewPagerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfile.
     */

    // TODO: Rename and change types and number of parameters
    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        TextView userName=view.findViewById(R.id.user_name);
        userName.setText(hub.getSessionController().getUser().getUsername());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        tabLayout = (TabLayout) view.findViewById(R.id.TabLayoutProfile);
        viewPager = (ViewPager) view.findViewById(R.id.ViewPagerProfile);
        viewPagerAdapter = new UserProfileTabsAdapter(getFragmentManager());
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab group = tabLayout.newTab();
        final TabLayout.Tab events = tabLayout.newTab();
        final TabLayout.Tab profile = tabLayout.newTab();

        home.setText("My Posts");
        group.setText("Followers");
        events.setText("Following");
        profile.setText("Groups");

        tabLayout.addTab(home,0);
        tabLayout.addTab(group,1);
        tabLayout.addTab(events,2);
        tabLayout.addTab(profile,3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setTabTextColors(Color.BLACK,Color.BLACK);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

/*
        final ListView list = view.findViewById(R.id.home_list_view);
        UserHomeListAdapter adapter = new UserHomeListAdapter((UserActivity)getActivity());
        list.setAdapter(adapter);
        userProfileGroups = view.findViewById(R.id.user_profile_groups);
        userProfileTimeline = view.findViewById(R.id.user_profile_timeline);

        userProfileGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("BUTTON","groups button pressed");
                UserHomeGroupListAdapter adapter = new UserHomeGroupListAdapter((UserActivity)getActivity(),hub.getSessionController().getUser().getGroupList());
                list.setAdapter(adapter);


            }
        });

        userProfileTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserHomeListAdapter adapter = new UserHomeListAdapter((UserActivity)getActivity());
                list.setAdapter(adapter);

            }
        });
        */
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
