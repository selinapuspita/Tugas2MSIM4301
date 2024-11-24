import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Menu> daftarMenu = new ArrayList<>();
    private static ArrayList<Menu> pesanan = new ArrayList<>();
    private static ArrayList<Integer> jumlahPesanan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inisialisasi menu
        daftarMenu.add(new Menu("Nasi Goreng", 25000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Goreng", 30000, "Makanan"));
        daftarMenu.add(new Menu("Mie Goreng", 20000, "Makanan"));
        daftarMenu.add(new Menu("Sate Ayam", 35000, "Makanan"));
        daftarMenu.add(new Menu("Es Teh", 5000, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 7000, "Minuman"));
        daftarMenu.add(new Menu("Kopi Hitam", 10000, "Minuman"));
        daftarMenu.add(new Menu("Jus Alpukat", 15000, "Minuman"));

        // Navigasi utama
        while (true) {
            System.out.println("\nSelamat datang di Restoran!");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Manajemen Menu Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int opsi = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (opsi) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    manajemenMenu();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi restoran!");
                    return;
                default:
                    System.out.println("Opsi tidak valid, coba lagi.");
            }
        }
    }

    private static void menuPelanggan() {
        while (true) {
            System.out.println("\n--- Menu Pelanggan ---");
            tampilkanMenu();
            System.out.println("Pilih menu berdasarkan nama (ketik 'selesai' jika selesai): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                hitungTotalBiaya();
                return;
            }

            boolean ditemukan = false;
            for (Menu menu : daftarMenu) {
                if (menu.getNama().equalsIgnoreCase(input)) {
                    pesanan.add(menu);
                    System.out.print("Masukkan jumlah: ");
                    int jumlah = scanner.nextInt();
                    scanner.nextLine();
                    jumlahPesanan.add(jumlah);
                    ditemukan = true;
                    break;
                }
            }

            if (!ditemukan) {
                System.out.println("Menu tidak ditemukan.");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\nDaftar Menu:");
        System.out.println("--- Makanan ---");
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equalsIgnoreCase("Makanan")) {
                System.out.println(menu.getNama() + " - Rp " + menu.getHarga());
            }
        }
        System.out.println("\n--- Minuman ---");
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equalsIgnoreCase("Minuman")) {
                System.out.println(menu.getNama() + " - Rp " + menu.getHarga());
            }
        }
    }

    private static void hitungTotalBiaya() {
        double total = 0;
        double diskonMinuman = 0;
        ArrayList<Menu> pesananMinuman = new ArrayList<>();
    
        System.out.println("\n--- Struk Pesanan ---");
        for (int i = 0; i < pesanan.size(); i++) {
            Menu menu = pesanan.get(i);
            int jumlah = jumlahPesanan.get(i);
            double subtotal = menu.getHarga() * jumlah;
            total += subtotal;
    
            if (menu.getKategori().equalsIgnoreCase("Minuman")) {
                for (int j = 0; j < jumlah; j++) {
                    pesananMinuman.add(menu); 
                }
            }
    
            System.out.println(menu.getNama() + " x" + jumlah + " - Rp " + subtotal);
        }
    
        System.out.println("\nSubtotal: Rp " + total);
    
        double pajak = total * 0.1;
        double biayaPelayanan = 20000;
        total += pajak + biayaPelayanan;
    
        if (total > 100000) {
            double diskon = total * 0.1;
            total -= diskon;
            System.out.println("Diskon 10%: -Rp " + diskon);
        }
    
        if (total > 50000 && !pesananMinuman.isEmpty()) {
            Menu minumanTermurah = pesananMinuman.get(0);
            for (Menu minuman : pesananMinuman) {
                if (minuman.getHarga() < minumanTermurah.getHarga()) {
                    minumanTermurah = minuman;
                }
            }
    
            diskonMinuman = minumanTermurah.getHarga();
            total -= diskonMinuman;
            System.out.println("Penawaran beli satu gratis satu: -Rp " + diskonMinuman + " (" + minumanTermurah.getNama() + ")");
        }
    
        System.out.println("Pajak (10%): Rp " + pajak);
        System.out.println("Biaya Pelayanan: Rp " + biayaPelayanan);
        System.out.println("Total: Rp " + total);
    } 

    private static void manajemenMenu() {
        while (true) {
            System.out.println("\n--- Manajemen Menu Restoran ---");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih opsi: ");
            int opsi = scanner.nextInt();
            scanner.nextLine();

            switch (opsi) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opsi tidak valid, coba lagi.");
            }
        }
    }

    private static void tambahMenu() {
        System.out.print("Masukkan nama menu: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Masukkan kategori (Makanan/Minuman): ");
        String kategori = scanner.nextLine();

        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahHargaMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = scanner.nextLine();

        for (Menu menu : daftarMenu) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                System.out.print("Masukkan harga baru: ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine();
                menu.setHarga(hargaBaru);
                System.out.println("Harga berhasil diubah.");
                return;
            }
        }
        System.out.println("Menu tidak ditemukan.");
    }

    private static void hapusMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = scanner.nextLine();

        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).getNama().equalsIgnoreCase(nama)) {
                daftarMenu.remove(i);
                System.out.println("Menu berhasil dihapus.");
                return;
            }
        }
        System.out.println("Menu tidak ditemukan.");
    }
}