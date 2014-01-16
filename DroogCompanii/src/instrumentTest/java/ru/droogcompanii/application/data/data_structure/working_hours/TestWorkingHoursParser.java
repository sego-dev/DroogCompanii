package ru.droogcompanii.application.data.data_structure.working_hours;

import junit.framework.TestCase;

import ru.droogcompanii.application.data.data_structure.working_hours.time.Time;
import ru.droogcompanii.application.data.data_structure.working_hours.working_hours_impl.DayAndNightWorkingHours;
import ru.droogcompanii.application.data.data_structure.working_hours.working_hours_impl.WorkingHoursOnBusinessDay;
import ru.droogcompanii.application.data.data_structure.working_hours.working_hours_impl.WorkingHoursOnHoliday;
import static ru.droogcompanii.application.util.DroogCompaniiStringConstants.XmlConstants.TypesOfDay;

/**
 * Created by ls on 09.01.14.
 */
public class TestWorkingHoursParser extends TestCase {

    private WorkingHoursParser parser;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        parser = new WorkingHoursParser();
    }

    public void testParseWorkingHoursOnHoliday() {
        String text = "Holiday";
        WorkingHours expectedWorkingHours = new WorkingHoursOnHoliday(text);
        assertEquals(expectedWorkingHours, parser.parse(TypesOfDay.holiday, text));
    }

    public void testParseWorkingHoursOnUsualDay() {
        Time from = new Time(9, 0);
        Time to = new Time(18, 30);
        String text = from + WorkingHoursOnBusinessDay.SEPARATOR_BETWEEN_TIMES + to;
        WorkingHours expectedWorkingHours = new WorkingHoursOnBusinessDay(from, to);
        assertEquals(expectedWorkingHours, parser.parse(TypesOfDay.usualDay, text));
    }

    public void testParseDayAndNightWorkingHours() {
        String text = "Day & Night";
        WorkingHours expectedWorkingHours = new DayAndNightWorkingHours(text);
        assertEquals(expectedWorkingHours, parser.parse(TypesOfDay.dayAndNight, text));
    }
}
