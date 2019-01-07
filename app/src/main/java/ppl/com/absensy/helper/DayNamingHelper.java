package ppl.com.absensy.helper;

public class DayNamingHelper {
    private DayNamingHelper() {
    }

    public static String dayNameInBahasaIndonesia(String dayInEnglishAbbreviated) {
        switch (dayInEnglishAbbreviated.toLowerCase()) {
            case "mon":
                return "Senin";
            case "tue":
                return "Selasa";
            case "wed":
                return "Rabu";
            case "thu":
                return "Kamis";
            case "fri":
                return "Jumat";
            case "sat":
                return "Sabtu";
            case "sun":
                return "Minggu";
            default:
                return "";
        }
    }
}
