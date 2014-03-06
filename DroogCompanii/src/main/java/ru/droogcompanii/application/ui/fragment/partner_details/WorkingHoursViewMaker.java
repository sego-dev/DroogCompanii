package ru.droogcompanii.application.ui.fragment.partner_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.droogcompanii.application.R;
import ru.droogcompanii.application.data.working_hours.WeekWorkingHours;
import ru.droogcompanii.application.data.working_hours.WorkingHours;
import ru.droogcompanii.application.util.CalendarUtils;

/**
 * Created by ls on 12.02.14.
 */
public class WorkingHoursViewMaker {

    private final LayoutInflater layoutInflater;

    private View view;
    private WeekWorkingHours workingHours;

    public WorkingHoursViewMaker(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public View makeView(WeekWorkingHours workingHours) {
        this.workingHours = workingHours;
        view = layoutInflater.inflate(R.layout.view_working_hours, null);
        setView();
        return view;
    }

    private void setView() {
        setOpeningHours();
        setLunchBreakHours();
    }

    private void setOpeningHours() {
        setOpeningWorkingHoursIndicator();
        setOpeningWorkingHoursText();
    }

    private void setOpeningWorkingHoursIndicator() {
        ImageView indicatorImageView = (ImageView) view.findViewById(R.id.workingHoursIndicator);
        indicatorImageView.setImageResource(defineIndicatorResource());
    }

    private int defineIndicatorResource() {
        return (isOpenNow())
                ? R.drawable.ic_working_hours_is_open
                : R.drawable.ic_working_hours_is_close;
    }

    private boolean isOpenNow() {
        return workingHours.includes(CalendarUtils.now());
    }

    private void setOpeningWorkingHoursText() {
        String openingText = TextByWorkingHoursProvider.getOpeningTextFrom(nowWorkingHours());
        TextView workingHoursTextView = (TextView) view.findViewById(R.id.workingHoursText);
        workingHoursTextView.setText(openingText);
    }

    private WorkingHours nowWorkingHours() {
        return workingHours.getWorkingHoursOf(CalendarUtils.now());
    }

    private void setLunchBreakHours() {
        String lunchBreakText = TextByWorkingHoursProvider.getLunchBreakTextFrom(nowWorkingHours());
        if (lunchBreakText.isEmpty()) {
            hideLunchBreakHours();
        } else {
            setLunchBreakText(lunchBreakText);
        }
    }

    private void hideLunchBreakHours() {
        view.findViewById(R.id.lunchBreakIndicator).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.lunchBreakText).setVisibility(View.INVISIBLE);
    }

    private void setLunchBreakText(String text) {
        TextView lunchBreakTextView = (TextView) view.findViewById(R.id.lunchBreakText);
        lunchBreakTextView.setText(text);
    }
}
