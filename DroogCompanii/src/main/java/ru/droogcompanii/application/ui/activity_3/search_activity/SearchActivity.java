package ru.droogcompanii.application.ui.activity_3.search_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

import ru.droogcompanii.application.R;
import ru.droogcompanii.application.data.hierarchy_of_partners.PartnerCategory;
import ru.droogcompanii.application.util.Keys;
import ru.droogcompanii.application.ui.activity_3.search_result_activity.SearchResultActivity;
import ru.droogcompanii.application.ui.fragment.partner_category_list_fragment.PartnerCategoryListFragment;

/**
 * Created by ls on 14.01.14.
 */
public class SearchActivity extends android.support.v4.app.FragmentActivity
                implements PartnerCategoryListFragment.OnPartnerCategoryClickListener {

    private EditText searchQueryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_search);

        searchQueryInput = (EditText) findViewById(R.id.searchQueryInputEditText);

        findViewById(R.id.searchByQueryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch();
            }
        });
    }

    private void onSearch() {
        String searchQuery = getSearchQuery();
        if (searchQuery.isEmpty()) {
            searchQueryInput.requestFocus();
        } else {
            onSearch(searchQuery);
        }
    }

    private String getSearchQuery() {
        return searchQueryInput.getText().toString().trim();
    }

    private void onSearch(String searchQuery) {
        showSearchResult(new SearchResultProviderBySearchQuery(searchQuery));
    }

    @Override
    public void onPartnerCategoryClick(PartnerCategory partnerCategory) {
        showSearchResult(new SearchResultProviderByPartnerCategory(partnerCategory));
    }

    private void showSearchResult(SearchResultProvider searchResultProvider) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(Keys.searchResultProvider, (Serializable) searchResultProvider);
        startActivity(intent);
    }

}
