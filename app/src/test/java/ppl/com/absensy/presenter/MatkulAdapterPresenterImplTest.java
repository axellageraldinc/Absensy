package ppl.com.absensy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import ppl.com.absensy.component.DaggerHomePresenterComponent;
import ppl.com.absensy.component.DaggerMatkulAdapterPresenterComponent;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.HomePresenterModule;
import ppl.com.absensy.module.MatkulAdapterPresenterModule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MatkulAdapterPresenterImplTest {

    HomePresenterImpl homePresenter;
    MatkulAdapterPresenterImpl matkulAdapterPresenter;

    @Before
    public void setUp() throws Exception {
        homePresenter = DaggerHomePresenterComponent.builder()
                .homePresenterModule(new HomePresenterModule(RuntimeEnvironment.application.getApplicationContext()))
                .build()
                .provideHomePresenter();
        matkulAdapterPresenter = DaggerMatkulAdapterPresenterComponent.builder()
                .matkulAdapterPresenterModule(new MatkulAdapterPresenterModule(RuntimeEnvironment.application.getApplicationContext()))
                .build()
                .provideMatkulAdapterPresenter();
    }

    @Test
    public void deleteMataKuliah() {
        long statusInsert = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(statusInsert, equalTo(1L));
        int statusDelete = matkulAdapterPresenter.deleteMataKuliah("1");
        assertThat(statusDelete, equalTo(1));
    }

    @Test
    public void updateJumlahAbsen() {
        long statusInsert = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(statusInsert, equalTo(1L));
        int statusUpdate = matkulAdapterPresenter.updateJumlahKosongMataKuliah(new MataKuliah("1", "makul", 0));
        assertThat(statusUpdate, equalTo(1));
    }
}