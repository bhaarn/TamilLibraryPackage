package com.padhuga.tamillibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.padhuga.tamillibrary.R;
import com.padhuga.tamillibrary.adapters.ExpandableListViewAdapter;
import com.padhuga.tamillibrary.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.padhuga.tamillibrary.activities.MenuLogic.parentModel;

public class MainFragment extends Fragment {
    private int position;

    public static MainFragment newInstance(int position) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_POSITION, position);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if(getArguments() != null) {
            position = getArguments().getInt(Constants.ARG_SECTION_POSITION);
        }
        initializeList(rootView);
        return rootView;
    }

    private void initializeList(View rootView) {
        List<String> listDataHeader = new ArrayList<>();
        HashMap<String, List<String>> listDataChild = new HashMap<>();
        ArrayList<String> st;
        for (int i = 0; i < parentModel.getData().getType().get(position).getType().size(); i++) {
            listDataHeader.add(parentModel.getData().getType().get(position).getType().get(i).getTitle());
            st = new ArrayList<>();
            for (int j = 0; j < parentModel.getData().getType().get(position).getType().get(i).getType().size(); j++) {
                st.add(j, parentModel.getData().getType().get(position).getType().get(i).getType().get(j).getTitle());
            }
            listDataChild.put(listDataHeader.get(i), st);
        }

        ExpandableListView expandableListView = rootView.findViewById(R.id.expandableListView);
        ExpandableListAdapter listAdapter = new ExpandableListViewAdapter(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                setupUI(groupPosition, childPosition);
                return false;
            }
        });
    }

    private void setupUI(int groupPosition, int childPosition) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.ARG_SECTION_POSITION, position);
        intent.putExtra(Constants.ARG_PARENT_POSITION, groupPosition);
        intent.putExtra(Constants.ARG_CHILD_POSITION, childPosition);
        startActivity(intent);
    }
}
