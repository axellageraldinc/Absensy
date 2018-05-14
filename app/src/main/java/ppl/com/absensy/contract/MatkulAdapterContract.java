package ppl.com.absensy.contract;

import ppl.com.absensy.model.MataKuliah;

public interface MatkulAdapterContract {
    interface Interactor{
        int updateJumlahKosongMataKuliah(MataKuliah mataKuliah);
        int deleteMataKuliah(String mataKuliahId);
    }
    interface Presenter{
        int updateJumlahKosongMataKuliah(MataKuliah mataKuliah);
        int deleteMataKuliah(String mataKuliahId);
    }
}
