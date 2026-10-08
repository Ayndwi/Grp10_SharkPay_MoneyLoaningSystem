package manager;

import java.time.LocalDate;

public class TestDate {

    private static LocalDate testDate = null;

    public static LocalDate today() {

        if (testDate != null) {
            return testDate;
        }

        return LocalDate.now();
    }

    public static void setDate(LocalDate date) {
        testDate = date;
    }

    public static void reset() {
        testDate = null;
    }
}