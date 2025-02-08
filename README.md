# NutriJourney - Final Project Pemrograman Berbasis Objek

## Deskripsi Proyek
NutriJourney adalah aplikasi berbasis JavaFX yang dikembangkan sebagai final proyek praktikum **Pemrograman Berbasis Objek** pada semester dua. Aplikasi ini dirancang untuk membantu pengguna dalam memilih makanan sehat, mencatat asupan nutrisi harian, serta melacak kemajuan kesehatan mereka. Aplikasi ini memungkinkan pengguna untuk mencatat konsumsi makanan harian, menghitung kebutuhan kalori, serta mendapatkan rekomendasi nutrisi berdasarkan data yang dimasukkan.

## Fitur Utama
- **Penghitungan Kalori**: Menampilkan total kalori yang dikonsumsi setiap hari.
- **Log Makanan**: Menyimpan riwayat makanan yang dikonsumsi dan mengkalkulasi kebutuhan gizi.
- **BMI dan BMR**: Menghitung BMI dan BMR berdasarkan berat badan, tinggi badan, usia, dan jenis kelamin.
- **Rekomendasi Nutrisi**: Menghitung kebutuhan kalori dan makronutrien berdasarkan tingkat aktivitas.
- **Manajemen Pengguna**: Mendukung registrasi, login, dan pengelolaan data pengguna.
- **Dukungan Database**: Menyimpan data menggunakan SQLite untuk menjaga persistensi data pengguna.

## Teknologi dan Versi yang Digunakan
- **Java**: 21.0.2 (Oracle Corporation 21.0.2+13-LTS-58)
- **JavaFX**: Sesuai dengan JDK 21
- **SQLite**: 3.44.2 (64-bit)
- **Gradle**: 8.7 (Build time: 2024-03-22 15:52:46 UTC)
- **Kotlin**: 1.9.22
- **JUnit**: Untuk pengujian unit aplikasi
- **OS**: Windows 11 10.0 amd64

## Cara Menjalankan Proyek
1. Clone repository ini ke komputer Anda menggunakan Git:
   ```bash
   git clone https://github.com/username/NutriJourney-JavaFXApp.git
   ```

2. Masuk ke direktori proyek:
   ```bash
   cd NutriJourney-JavaFXApp/NutriJourney
   ```

3. Pastikan Anda sudah menginstal **JDK 21** dan **Gradle 8.7**.

4. Bangun proyek dengan perintah berikut:
   ```bash
   gradle build
   ```

5. Jalankan aplikasi menggunakan perintah berikut:
   ```bash
   gradle run
   ```

6. Aplikasi akan terbuka dengan tampilan login. Anda dapat mendaftar akun baru atau masuk dengan akun yang sudah ada.

## Screenshot Aplikasi
Berikut adalah beberapa tampilan dari aplikasi NutriJourney:

### 1. Halaman Login
![Login Screen](https://raw.githubusercontent.com/xebec51/NutriJourney-JavaFXApp/main/Login.png)

### 2. Dashboard Utama
![Main Dashboard](https://raw.githubusercontent.com/xebec51/NutriJourney-JavaFXApp/main/Dashboard.png)

### 3. Ringkasan Nutrisi Harian
![Daily Nutrition Summary](https://raw.githubusercontent.com/xebec51/NutriJourney-JavaFXApp/main/Nutrition%20Summary%20&%20Consumed%20Food.png)

### 4. Detail Makanan
![Food Details](https://raw.githubusercontent.com/xebec51/NutriJourney-JavaFXApp/main/Food%20Details.png)

### 5. Halaman Registrasi
![Sign Up Screen](https://raw.githubusercontent.com/xebec51/NutriJourney-JavaFXApp/main/Register.png)

## Struktur Direktori
```
NutriJourney-JavaFXApp/
│-- NutriJourney/
│   │-- app/
│   │   │-- src/
│   │   │   │-- main/java/nutrijourney/   (Kode sumber utama)
│   │   │   │-- main/resources/          (File resources seperti gambar dan CSS)
│   │   │   │-- test/java/nutrijourney/  (Kode pengujian unit)
│   │-- build.gradle
│   │-- settings.gradle
│-- README.md
│-- LICENSE
```

## Cara Berkontribusi
1. Fork repository ini.
2. Buat branch untuk fitur baru atau perbaikan bug.
3. Kirim pull request untuk perubahan yang telah Anda buat.

## Lisensi
Proyek ini dilisensikan di bawah **MIT License**. Anda dapat melihat lisensi lengkap di tautan berikut:  
[LICENSE](https://raw.githubusercontent.com/xebec51/NutriJourney-JavaFXApp/refs/heads/main/NutriJourney/LICENSE)

## Kontributor
- **Nama**: Muh. Rinaldi Ruslan

## Kontak
Jika Anda memiliki pertanyaan atau masukan terkait proyek ini, silakan hubungi saya melalui:
- **Email**: rinaldi.ruslan51@gmail.com
- **LinkedIn**: [linkedin.com/in/rinaldiruslan](https://www.linkedin.com/in/rinaldiruslan)
- **GitHub**: [github.com/rinaldiruslan](https://github.com/rinaldiruslan)
- **Instagram**: [instagram.com/rinaldiruslan](https://www.instagram.com/rinaldiruslan)

---
Terima kasih telah mengunjungi repository ini!

