package ppl.com.absensy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import ppl.com.absensy.component.DaggerHomePresenterComponent;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.HomePresenterModule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HomePresenterImplTest {

    HomePresenterImpl homePresenter;

    @Before
    public void setUp() throws Exception {
        homePresenter = DaggerHomePresenterComponent.builder()
                .homePresenterModule(new HomePresenterModule(RuntimeEnvironment.application.getApplicationContext()))
                .build()
                .provideHomePresenter();
    }

    @Test
    public void getAllMataKuliahFromDatabase() {
        saveMataKuliah();
        List<MataKuliah> mataKuliahList = homePresenter.getAllMataKuliahFromDatabase();
        assertThat(mataKuliahList, notNullValue());
        assertThat(mataKuliahList.isEmpty(), equalTo(false));
    }

    @Test
    public void saveMataKuliah() {
        long status = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(status, equalTo(1L));
    }

    @Test
    public void saveMataKuliahGagalKarenaNamaMataKuliahAngka() {
        long status = homePresenter.saveMataKuliah("1", "12345", 0);
        assertThat(status, equalTo(-2L));
    }

}