package ru.droogcompanii.application.ui.fragment.filter_fragment.standard.search_criteria_and_comparators.partner_point;

import java.io.Serializable;
import java.util.Calendar;

import ru.droogcompanii.application.data.hierarchy_of_partners.PartnerPoint;
import ru.droogcompanii.application.data.searchable_sortable_listing.SearchCriterion;
import ru.droogcompanii.application.ui.fragment.filter_fragment.standard.search_criteria_and_comparators.CalendarProvider;

/**
 * Created by ls on 16.01.14.
 */
public class PartnerPointSearchCriterionByWorksAtSomeTime
        implements SearchCriterion<PartnerPoint>, Serializable {

    private final CalendarProvider calendarProvider;

    public PartnerPointSearchCriterionByWorksAtSomeTime(CalendarProvider calendarProvider) {
        this.calendarProvider = calendarProvider;
    }

    @Override
    public boolean meetCriterion(PartnerPoint partnerPoint) {
        Calendar calendar = calendarProvider.getCalendar();
        return partnerPoint.workingHours.includes(calendar);
    }
}