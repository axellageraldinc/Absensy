package ppl.com.absensy.helper;

public class DayNamingHelper {
    private DayNamingHelper() {
    }

    public static String dayNameInBahasaIndonesia(int dayIndex) {
        switch (dayIndex) {
            case 1:
                return "Senin";
            case 2:
                return "Selasa";
            case 3:
                return "Rabu";
            case 4:
                return "Kamis";
            case 5:
                return "Jumat";
            case 6:
                return "Sabtu";
            default:
                return "";
        }
    }
}
