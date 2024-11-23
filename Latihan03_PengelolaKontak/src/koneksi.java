/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author ASUS
 */
public class koneksi {
    // URL SQLite database
    private static final String DB_URL = "jdbc:sqlite:kontak.db";

    // Fungsi untuk membuat koneksi ke database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Fungsi untuk membuat tabel 'kontak' jika belum ada
    public static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS kontak ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nama TEXT, "
                + "noTelp TEXT, "
                + "kategori TEXT"
                + ");";

        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            // Eksekusi perintah untuk membuat tabel
            stmt.execute(createTableSQL);
            System.out.println("Tabel 'kontak' berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.err.println("Gagal membuat tabel: " + e.getMessage());
        }
    }

    // Fungsi untuk menambahkan data kontak
    public static void addKontak(String nama, String noTelp, String kategori) {
        String insertSQL = "INSERT INTO kontak (nama, noTelp, kategori) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); 
             var pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, noTelp);
            pstmt.setString(3, kategori);
            pstmt.executeUpdate();
            System.out.println("Kontak berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan kontak: " + e.getMessage());
        }
    }

    // Fungsi untuk mengedit data kontak
    public static void editKontak(int id, String nama, String noTelp, String kategori) {
        String updateSQL = "UPDATE kontak SET nama = ?, noTelp = ?, kategori = ? WHERE id = ?";

        try (Connection conn = getConnection(); 
             var pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, noTelp);
            pstmt.setString(3, kategori);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Kontak berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Gagal mengedit kontak: " + e.getMessage());
        }
    }

    // Fungsi untuk menghapus data kontak
    public static void deleteKontak(int id) {
        String deleteSQL = "DELETE FROM kontak WHERE id = ?";

        try (Connection conn = getConnection(); 
             var pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Kontak berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal menghapus kontak: " + e.getMessage());
        }
    }

    // Fungsi untuk mencari kontak berdasarkan nama atau nomor telepon
    public static ArrayList<String[]> searchKontak(String keyword) {
        ArrayList<String[]> kontakList = new ArrayList<>();
        String searchSQL = "SELECT * FROM kontak WHERE nama LIKE ? OR noTelp LIKE ?";

        try (Connection conn = getConnection(); 
             var pstmt = conn.prepareStatement(searchSQL)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] kontak = new String[4];
                kontak[0] = String.valueOf(rs.getInt("id"));
                kontak[1] = rs.getString("nama");
                kontak[2] = rs.getString("noTelp");
                kontak[3] = rs.getString("kategori");
                kontakList.add(kontak);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari kontak: " + e.getMessage());
        }
        return kontakList;
    }

    // Fungsi untuk menampilkan semua kontak
    public static ArrayList<String[]> getAllKontak() {
        ArrayList<String[]> kontakList = new ArrayList<>();
        String selectSQL = "SELECT * FROM kontak";

        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                String[] kontak = new String[4];
                kontak[0] = String.valueOf(rs.getInt("id"));
                kontak[1] = rs.getString("nama");
                kontak[2] = rs.getString("noTelp");
                kontak[3] = rs.getString("kategori");
                kontakList.add(kontak);
            }
        } catch (SQLException e) {
            System.err.println("Gagal menampilkan kontak: " + e.getMessage());
        }
        return kontakList;
    }
    public void createDatabaseAndTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS kontak (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT NOT NULL,
                nomor_telepon TEXT NOT NULL,
                kategori TEXT NOT NULL
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.execute(createTableSQL);
                System.out.println("Tabel 'kontak' berhasil dibuat atau sudah ada.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal membuat database/tabel: " + e.getMessage());
        }
    }
}
