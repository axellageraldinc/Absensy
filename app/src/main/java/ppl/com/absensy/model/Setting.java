package ppl.com.absensy.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Setting {
    private int maxAbsenceAmount;
    private boolean subjectReminder;
}
