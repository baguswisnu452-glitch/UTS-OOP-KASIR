import java.util.ArrayList;
import java.util.Scanner;

class Barang {
    private String nama;
    private double harga;

    public Barang(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
}

class Gudang {
    private ArrayList<Barang> daftarBarang = new ArrayList<>();

    public void tambahBarang(String nama, double harga) {
        daftarBarang.add(new Barang(nama, harga));
        System.out.println("Barang berhasil ditambahkan!");
    }

    public void tampilkanBarang() {
        System.out.println("\nDaftar barang");
        for (int i = 0; i < daftarBarang.size(); i++) {
            System.out.println((i + 1) + ". " + daftarBarang.get(i).getNama() + " - Rp" + daftarBarang.get(i).getHarga());
        }
    }

    public Barang getBarang(int index) {
        if (index >= 0 && index < daftarBarang.size()) return daftarBarang.get(index);
        return null;
    }
    
    public int totalJenisBarang() { return daftarBarang.size(); }
}

class Transaksi {
    private Barang barang;
    private int jumlah;

    public Transaksi(Barang barang, int jumlah) {
        this.barang = barang;
        this.jumlah = jumlah;
    }
    public double hitungSubtotal() { return barang.getHarga() * jumlah; }
    public String detail() {
        return barang.getNama() + " x" + jumlah + " = Rp" + hitungSubtotal();
    }
}

class Kasir {
    private ArrayList<Transaksi> keranjang = new ArrayList<>();

    public void beliBarang(Barang b, int qty) {
        keranjang.add(new Transaksi(b, qty));
        System.out.println("Dimasukkan ke keranjang.");
    }

    public void tampilkanTransaksi() {
        System.out.println("\nKeranjang anda");
        for (Transaksi t : keranjang) {
            System.out.println("- " + t.detail());
        }
    }

    public double hitungTotal() {
        double total = 0;
        for (Transaksi t : keranjang) total += t.hitungSubtotal();
        return total;
    }

    public void bayar(double uang) {
        double total = hitungTotal();
        if (uang >= total) {
            System.out.println("Kembalian: Rp" + (uang - total));
            keranjang.clear(); // Reset keranjang setelah bayar
            System.out.println("Belanja selesai.");
        } else {
            System.out.println("Uang tidak cukup! Kurang: Rp" + (total - uang));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Gudang gudang = new Gudang();
        Kasir kasir = new Kasir();
        int pilihan;

        do {
            System.out.println("\nKASIR");
            System.out.println("1. Tambah barang");
            System.out.println("2. Tampilkan barang");
            System.out.println("3. Beli barang");
            System.out.println("4. Tampilkan transaksi");
            System.out.println("5. Hitung total & bayar");
            System.out.println("6. Keluar");
            System.out.print("Pilih: ");
            pilihan = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (pilihan) {
                case 1:
                    System.out.print("Nama barang: "); String n = sc.nextLine();
                    System.out.print("Harga: "); double h = sc.nextDouble();
                    gudang.tambahBarang(n, h);
                    break;
                case 2:
                    gudang.tampilkanBarang();
                    break;
                case 3:
                    gudang.tampilkanBarang();
                    System.out.print("Pilih nomor barang: "); int no = sc.nextInt() - 1;
                    System.out.print("Jumlah: "); int qty = sc.nextInt();
                    Barang b = gudang.getBarang(no);
                    if (b != null) kasir.beliBarang(b, qty);
                    else System.out.println("Barang tidak ditemukan.");
                    break;
                case 4:
                    kasir.tampilkanTransaksi();
                    break;
                case 5:
                    double total = kasir.hitungTotal();
                    System.out.println("Total tagihan: Rp" + total);
                    System.out.print("Masukkan uang: "); double bayar = sc.nextDouble();
                    kasir.bayar(bayar);
                    break;
            }
        } while (pilihan != 6);
        System.out.println("Sampai jumpa!");
    }
}