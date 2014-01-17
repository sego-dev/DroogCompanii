package ru.droogcompanii.application.view.activity_3.filter_activity.standard_filter;

import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import ru.droogcompanii.application.R;
import ru.droogcompanii.application.data.hierarchy_of_partners.PartnerPoint;
import ru.droogcompanii.application.view.activity_3.filter_activity.filter.Filter;

/**
 * Created by ls on 16.01.14.
 */
class StandardFiltersReader {

    private final View viewOfFilters;

    private final boolean sortByDistance;
    private final boolean sortBySaleValue;

    private final boolean cashlessPayments;

    private final boolean worksNow;

    private final boolean saleTypeBonus;
    private final boolean saleTypeDiscount;
    private final boolean saleTypeCashBack;


    StandardFiltersReader(View viewOfFilters) {
        this.viewOfFilters = viewOfFilters;

        sortByDistance = checkBoxIsMarked(R.id.sortByDistanceCheckBox);
        sortBySaleValue = checkBoxIsMarked(R.id.sortBySaleValueCheckBox);

        cashlessPayments = checkBoxIsMarked(R.id.cashlessPaymentsCheckBox);

        worksNow = checkBoxIsMarked(R.id.worksNowCheckBox);

        saleTypeBonus = checkBoxIsMarked(R.id.bonusCheckBox);
        saleTypeDiscount = checkBoxIsMarked(R.id.discountCheckBox);
        saleTypeCashBack = checkBoxIsMarked(R.id.cashBackCheckBox);
    }

    private boolean checkBoxIsMarked(int idOfCheckBox) {
        CheckBox checkBox = (CheckBox) viewOfFilters.findViewById(idOfCheckBox);
        return checkBox.isChecked();
    }

    public List<Filter<PartnerPoint>> read() {
        List<Filter<PartnerPoint>> filters = new ArrayList<Filter<PartnerPoint>>();
        if (sortByDistance) {
            filters.add(new SortByDistanceBasedOnCurrentLocationFilter());
        }
        if (sortBySaleValue) {
            filters.add(new SortBySaleValueFilter());
        }
        if (cashlessPayments) {
            filters.add(new CashlessPaymentsFilter());
        }
        if (worksNow) {
            filters.add(new WorksNowFilter());
        }
        SaleTypeFilter saleTypeFilter = new SaleTypeFilter(saleTypeBonus, saleTypeDiscount, saleTypeCashBack);
        filters.add(saleTypeFilter);

        return filters;
    }
}