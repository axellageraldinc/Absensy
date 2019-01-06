package ppl.com.absensy.reminder;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ServiceScope;
import ppl.com.absensy.repository.AppDatabase;
import ppl.com.absensy.repository.SubjectDao;

@Module
public class AlarmServiceModule {
    @Provides
    @ServiceScope
    public SubjectDao providesSubjectDao(AppDatabase appDatabase) {
        return appDatabase.subjectDao();
    }
}
