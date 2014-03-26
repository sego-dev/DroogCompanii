package ru.droogcompanii.application.ui.main_screen_2.filters_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import ru.droogcompanii.application.R;
import ru.droogcompanii.application.ui.main_screen_2.filters_dialog.filters.Filters;

/**
 * Created by ls on 25.03.14.
 */
public class FiltersDialog extends Dialog {

    private final FiltersDialogFragment.Callbacks callbacks;
    private final Filters filters;
    private View contentView;

    public FiltersDialog(Context context, Filters filters, FiltersDialogFragment.Callbacks callbacks) {
        super(context);
        this.callbacks = callbacks;
        this.filters = filters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.contentView = prepareContentView();
        setContentView(contentView);
    }

    private View prepareContentView() {
        View contentView = wrapInScrollView(prepareFiltersContentView());
        View panelClearDone = prepareClearDonePanel();
        return combineIntoVerticalLinearLayout(contentView, panelClearDone);
    }

    private View wrapInScrollView(View view) {
        ScrollView scrollView = prepareScrollView();
        scrollView.addView(view);
        return scrollView;
    }

    private ScrollView prepareScrollView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        return (ScrollView) layoutInflater.inflate(R.layout.view_scroll, null);
    }

    private View prepareFiltersContentView() {
        View filtersContentView = filters.inflateContentView(getContext());
        filters.displayOn(filtersContentView);
        return filtersContentView;
    }

    private View prepareClearDonePanel() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.view_clear_done_panel, null);
        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onFiltersClear();
            }
        });
        view.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onFiltersDone();
            }
        });
        return view;
    }

    private View combineIntoVerticalLinearLayout(View... viewsToCombine) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.vertical_linear_layout, null);
        for (View each : viewsToCombine) {
            viewGroup.addView(each);
        }
        return viewGroup;
    }

    public View getContentView() {
        return contentView;
    }


}