package ppl.com.absensy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ppl.com.absensy.interactor.HomeInteractorImpl;
import ppl.com.absensy.model.MataKuliah;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

public class HomePresenterImplTest {

    @Mock
    HomeInteractorImpl homeInteractor;
    @Mock
    HomePresenterImpl homePresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveMataKuliah() {
        MataKuliah mataKuliah = new MataKuliah("1", "makul", 0);
        given(homeInteractor.saveMataKuliah(mataKuliah)).willReturn(0L);
        long status = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(status, equalTo(0L));
    }

}