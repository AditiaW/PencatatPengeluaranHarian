import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val formatTanggal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    println("=== Pencatat Pengeluaran Harian ===")

    // Meminta pengguna memasukkan nama
    print("Masukkan nama Anda: ")
    val nama = scanner.nextLine()

    // Mendapatkan tanggal dan waktu hari ini
    val hariIni = Date()
    val tanggalSekarang = formatTanggal.format(hariIni)

    // Menampilkan pesan sambutan dan tanggal hari ini
    println("Halo, $nama! Tanggal hari ini adalah $tanggalSekarang.")
    println("Mari mulai mencatat pengeluaran harian Anda.")

    // Membuat daftar kosong untuk menyimpan pengeluaran
    val daftarPengeluaran = mutableListOf<Pengeluaran>()

    // Variabel untuk mengontrol apakah pengguna ingin melanjutkan pencatatan pengeluaran
    var lanjutkanPencatatan = true

    // Loop untuk mencatat pengeluaran
    while (lanjutkanPencatatan) {
        // Meminta pengguna memasukkan deskripsi pengeluaran atau keluar
        print("Masukkan deskripsi pengeluaran (atau 'keluar' untuk berhenti): ")
        val deskripsi = scanner.nextLine()

        // Jika pengguna memilih 'keluar', keluar dari loop
        if (deskripsi.equals("keluar", ignoreCase = true)) {
            lanjutkanPencatatan = false
        } else {
            // Jika bukan 'keluar', minta pengguna memasukkan jumlah pengeluaran
            print("Masukkan jumlah pengeluaran: ")
            val jumlah = scanner.nextDouble()

            // Membersihkan karakter newline yang masih ada dalam buffer
            scanner.nextLine()

            // Membuat objek Pengeluaran berdasarkan input pengguna
            val pengeluaran = Pengeluaran(deskripsi, jumlah, hariIni)

            // Menambahkan objek pengeluaran ke dalam daftarPengeluaran
            daftarPengeluaran.add(pengeluaran)

            // Menampilkan konfirmasi pencatatan pengeluaran
            println("Pengeluaran tercatat: $deskripsi - Rp$jumlah")
        }
    }

    // Format tanggal untuk digunakan dalam nama file laporan
    val formatTanggalFile = formatTanggal.format(hariIni).replace(" ", "_").replace(":", "-")

    // Membuat nama file laporan berdasarkan format tanggal
    val namaFileLaporan = "laporan_pengeluaran_$formatTanggalFile.txt"

    // Memanggil fungsi generateLaporanPengeluaran untuk membuat laporan pengeluaran
    generateLaporanPengeluaran(namaFileLaporan, nama, daftarPengeluaran)

    // Menampilkan pesan bahwa laporan pengeluaran telah dibuat
    println("Laporan pengeluaran telah dibuat: $namaFileLaporan")
}


data class Pengeluaran(val deskripsi: String, val jumlah: Double, val tanggal: Date)

fun generateLaporanPengeluaran(namaFile: String, nama: String, daftarPengeluaran: List<Pengeluaran>) {
    val file = File(namaFile)
    file.writeText("Laporan Pengeluaran untuk $nama\n\n")

    val formatTanggal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    // Menghasilkan entri laporan untuk setiap pengeluaran dalam daftarPengeluaran
    daftarPengeluaran.forEachIndexed { index, pengeluaran ->
        val formatTanggalPengeluaran = formatTanggal.format(pengeluaran.tanggal)
        val entri = "${index + 1}. [$formatTanggalPengeluaran] - ${pengeluaran.deskripsi}: Rp${pengeluaran.jumlah}\n"

        // Menambahkan entri ke dalam file laporan
        file.appendText(entri)
    }

    // Menghitung total jumlah pengeluaran dari seluruh daftarPengeluaran
    val totalJumlah = daftarPengeluaran.sumByDouble { it.jumlah }

    // Membuat entri untuk total pengeluaran dan menambahkannya ke dalam file laporan
    val entriTotal = "\nTotal pengeluaran: Rp$totalJumlah"
    file.appendText(entriTotal)
}
