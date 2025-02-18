package dao;

import model.Voucher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/voucher_db"; // Địa chỉ cơ sở dữ liệu
    private static final String USER = "root"; // Tên người dùng của cơ sở dữ liệu
    private static final String PASSWORD = "password"; // Mật khẩu của cơ sở dữ liệu

    // Phương thức kết nối đến cơ sở dữ liệu
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Lấy tất cả voucher từ cơ sở dữ liệu (cho Manager)
    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        String query = "SELECT * FROM vouchers";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String voucherCode = rs.getString("voucher_code");
                int discountAmount = rs.getInt("discount_amount");
                String validUntil = rs.getString("valid_until");
                vouchers.add(new Voucher(voucherCode, discountAmount, validUntil));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    // Lọc voucher theo mã voucher (cho Customer)
    public Voucher getVoucherByCode(String voucherCode) {
        Voucher voucher = null;
        String query = "SELECT * FROM vouchers WHERE voucher_code = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, voucherCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int discountAmount = rs.getInt("discount_amount");
                String validUntil = rs.getString("valid_until");
                voucher = new Voucher(voucherCode, discountAmount, validUntil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    // Thêm voucher (cho Manager)
    public void addVoucher(Voucher voucher) {
        String query = "INSERT INTO vouchers (voucher_code, discount_amount, valid_until) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, voucher.getVoucherCode());
            stmt.setInt(2, voucher.getDiscountAmount());
            stmt.setString(3, voucher.getValidUntil());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật voucher (cho Manager)
    public void updateVoucher(Voucher voucher) {
        String query = "UPDATE vouchers SET discount_amount = ?, valid_until = ? WHERE voucher_code = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, voucher.getDiscountAmount());
            stmt.setString(2, voucher.getValidUntil());
            stmt.setString(3, voucher.getVoucherCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa voucher (cho Manager)
    public void deleteVoucher(String voucherCode) {
        String query = "DELETE FROM vouchers WHERE voucher_code = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, voucherCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
