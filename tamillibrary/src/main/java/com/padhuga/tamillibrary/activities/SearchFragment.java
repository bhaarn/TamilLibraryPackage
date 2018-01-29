package com.padhuga.tamillibrary.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.padhuga.tamillibrary.R;
import com.padhuga.tamillibrary.models.ParentModel;
import com.padhuga.tamillibrary.models.SearchRetriever;
import com.padhuga.tamillibrary.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private ArrayList<SearchRetriever> searchRetriever = null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ListView searchResultsListView = rootView.findViewById(R.id.search_results_list);
        String query = getArguments() != null ? getArguments().getString(Constants.ARG_QUERY_TEXT):null;
        final List<String> queryResults = new ArrayList<>();
        MenuLogic menuLogic = new MenuLogic();
        ParentModel parentModel = menuLogic.readJSONFromAssetsAndConvertTogson(getActivity(), BaseActivity.jsonFileName);
        searchRetriever = showSearchResults(parentModel, query);
        for(int titleIndex = 0;titleIndex < searchRetriever.size(); titleIndex++) {
            queryResults.add(titleIndex, searchRetriever.get(titleIndex).getTitle());
        }
            ArrayAdapter<String> searchArrayAdapter = getContext() != null ? new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, queryResults) : null;
            searchResultsListView.setAdapter(searchArrayAdapter);

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                setupUI(searchRetriever.get(position).getPosition(), searchRetriever.get(position).getGroupPosition(), searchRetriever.get(position).getChildPosition());
            }
        });
        return rootView;
    }

    private void setupUI(int position, int groupPosition, int childPosition) {
        Fragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();

        args.putInt(Constants.ARG_SECTION_POSITION, position);
        args.putInt(Constants.ARG_PARENT_POSITION, groupPosition);
        args.putInt(Constants.ARG_CHILD_POSITION, childPosition);

        detailsFragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, detailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private ArrayList<SearchRetriever> showSearchResults(ParentModel parentModel, String query) {
        ArrayList<SearchRetriever> searchRetriever = new ArrayList<>();
        for (int position = 0; position < parentModel.getData().getType().size(); position++) {
            for (int groupPosition = 0; groupPosition < parentModel.getData().getType().get(position).getType().size(); groupPosition++) {
                for (int childPosition = 0; childPosition < parentModel.getData().getType().get(position).getType().get(groupPosition).getType().size(); childPosition++) {
                    if (parentModel.getData().getType().get(position).getType().get(groupPosition).getType().get(childPosition).getTitle().contains(query) ||
                            parentModel.getData().getType().get(position).getType().get(groupPosition).getType().get(childPosition).getSoothiram().contains(query) ||
                            parentModel.getData().getType().get(position).getType().get(groupPosition).getType().get(childPosition).getDesc().contains(query) ||
                            parentModel.getData().getType().get(position).getType().get(groupPosition).getType().get(childPosition).getExample().contains(query)) {
                        searchRetriever.add(new SearchRetriever(parentModel.getData().getType().get(position).getType().get(groupPosition).getType().get(childPosition).getTitle(),
                                position, groupPosition, childPosition));
                    }
                }
            }
        }
        return searchRetriever;
    }
}