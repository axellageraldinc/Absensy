package ppl.com.absensy.presenter;

import java.util.List;

import javax.inject.Inject;

import ppl.com.absensy.contract.HomeContract;
import ppl.com.absensy.model.MataKuliah;

public class HomePresenterImpl implements HomeContract.Presenter {

    private HomeContract.Interactor homeInteractor;

    @Inject
    public HomePresenterImpl(HomeContract.Interactor homeInteractor) {
        this.homeInteractor = homeInteractor;
    }

    @Override
    public List<MataKuliah> getAllMataKuliahFromDatabase() {
        return homeInteractor.getAllMataKuliah();
    }

    @Override
    public long saveMataKuliah(String id, String namaMataKuliah, int jumlahKosong) {
        try{
            Integer.parseInt(namaMataKuliah);
            return -2;
        } catch (Exception ex){
            return homeInteractor.saveMataKuliah(new MataKuliah(id, namaMataKuliah, jumlahKosong));
        }
    }

    @Override
    public boolean isMatkulNameExists(String namaMataKuliah) {
        return homeInteractor.isMatkulNameExists(new MataKuliah("", namaMataKuliah, 0));
    }
}
