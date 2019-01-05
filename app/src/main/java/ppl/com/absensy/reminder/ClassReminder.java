package ppl.com.absensy.reminder;

import ppl.com.absensy.model.Subject;

public interface ClassReminder {
    void cancelExistingReminder(Subject uneditedSubject);

    void setReminder(Subject subject);
}
