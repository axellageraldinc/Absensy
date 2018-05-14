package ppl.com.absensy.contract;

import java.util.List;

import ppl.com.absensy.model.MataKuliah;

public interface HomeContract {
    interface View{
        void showDaftarMataKuliah(List<MataKuliah> mataKuliahList);
    }
    interface Interactor{
        long saveMataKuliah(MataKuliah mataKuliah);
        List<MataKuliah> getAllMataKuliah();
    }
    interface Presenter{
        List<MataKuliah> getAllMataKuliahFromDatabase();
        long saveMataKuliah(String id, String namaMataKuliah, int jumlahKosong);
    }
}
