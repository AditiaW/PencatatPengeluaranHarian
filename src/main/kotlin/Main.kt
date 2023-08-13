import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val formatTanggal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    println("=== Pencatat Pengeluaran Harian ===")

    print("Masukkan nama Anda: ")
    val nama = scanner.nextLine()

    val hariIni = Date()
    val tanggalSekarang = formatTanggal.format(hariIni)

    println("Halo, $nama! Tanggal hari ini adalah $tanggalSekarang.")
    println("Mari mulai mencatat pengeluaran harian Anda.")

    val daftarPengeluaran = mutableListOf<Pengeluaran>()

    var lanjutkanPencatatan = true

    while (lanjutkanPencatatan) {
        print("Masukkan deskripsi pengeluaran (atau 'keluar' untuk berhenti): ")
        val deskripsi = scanner.nextLine()

        if (deskripsi.equals("keluar", ignoreCase = true)) {
            lanjutkanPencatatan = false
        } else {
            print("Masukkan jumlah pengeluaran: ")
            val jumlah = scanner.nextDouble()

            // Membersihkan karakter newline yang masih ada dalam buffer
            scanner.nextLine()

            val pengeluaran = Pengeluaran(deskripsi, jumlah, hariIni)
            daftarPengeluaran.add(pengeluaran)

            println("Pengeluaran tercatat: $deskripsi - Rp$jumlah")
        }
    }

    val formatTanggalFile = formatTanggal.format(hariIni).replace(" ", "_").replace(":", "-")
    val namaFileLaporan = "laporan_pengeluaran_$formatTanggalFile.txt"
    generateLaporanPengeluaran(namaFileLaporan, nama, daftarPengeluaran)

    println("Laporan pengeluaran telah dibuat: $namaFileLaporan")
}

data class Pengeluaran(val deskripsi: String, val jumlah: Double, val tanggal: Date)

fun generateLaporanPengeluaran(namaFile: String, nama: String, daftarPengeluaran: List<Pengeluaran>) {
    val file = File(namaFile)
    file.writeText("Laporan Pengeluaran untuk $nama\n\n")

    val formatTanggal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    daftarPengeluaran.forEachIndexed { index, pengeluaran ->
        val formatTanggalPengeluaran = formatTanggal.format(pengeluaran.tanggal)
        val entri = "${index + 1}. [$formatTanggalPengeluaran] - ${pengeluaran.deskripsi}: Rp${pengeluaran.jumlah}\n"
        file.appendText(entri)
    }

    val totalJumlah = daftarPengeluaran.sumByDouble { it.jumlah }
    val entriTotal = "\nTotal pengeluaran: Rp$totalJumlah"
    file.appendText(entriTotal)
}