package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/wallet_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public double getWalletBalance(Long walletId) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT balance FROM wallet WHERE id=?")) {

            statement.setLong(1, walletId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getTransferStatus(String reference) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT status FROM transfer WHERE reference=?")) {

            statement.setString(1, reference);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("status");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateWalletBalance(Long walletId, double balance) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE wallet SET balance=? WHERE id=?")) {

            statement.setDouble(1, balance);
            statement.setLong(2, walletId);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}