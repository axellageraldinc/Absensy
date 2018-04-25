package ppl.com.absensy.model;

public class MataKuliah {

  private String id, nama;
  private int jumlahKosong;

  public MataKuliah(String id, String nama, int jumlahKosong) {
    this.id = id;
    this.nama = nama;
    this.jumlahKosong = jumlahKosong;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public int getJumlahKosong() {
    return jumlahKosong;
  }

  public void setJumlahKosong(int jumlahKosong) {
    this.jumlahKosong = jumlahKosong;
  }

}
