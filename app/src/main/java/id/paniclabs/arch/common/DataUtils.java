package id.paniclabs.arch.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ali@pergikuliner
 * @created 22/05/2017.
 * @project ArchitectureComponents.
 */

public class DataUtils {
    public static String trimYearDate(String fullDate) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date yourDate = parser.parse(fullDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yourDate);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
