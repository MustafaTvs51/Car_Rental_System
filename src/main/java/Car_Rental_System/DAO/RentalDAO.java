package Car_Rental_System.DAO;

import Car_Rental_System.Models.Rental;
import Car_Rental_System.Models.enums.RentalType;
import Car_Rental_System.Utils.DB_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    public void saveRental(Rental rental) throws SQLException {
        String sql = "INSERT INTO rentals (user_id, vehicle_id, start_date, end_date, rental_type, total_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rental.getUserId());
            stmt.setInt(2, rental.getVehicleId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getStartDate()));
            stmt.setTimestamp(4, Timestamp.valueOf(rental.getEndDate()));
            stmt.setString(5, rental.getRentalType().name());
            stmt.setBigDecimal(6, rental.getTotalPrice());
            stmt.executeUpdate();
        }
    }

    public List<Rental> getRentalsByUserId(int userId) {
        List<Rental> rentals = new ArrayList<>();

        String sql = """
        SELECT r.*, v.brand, v.model
        FROM rentals r
        JOIN vehicles v ON r.vehicle_id = v.id
        WHERE r.user_id = ?
        ORDER BY r.start_date DESC
    """;

        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setUserId(rs.getInt("user_id"));
                rental.setVehicleId(rs.getInt("vehicle_id"));
                rental.setRentalType(RentalType.valueOf(rs.getString("rental_type")));
                rental.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
                rental.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
                rental.setTotalPrice(rs.getBigDecimal("total_price"));

                // Ek bilgi: araç markası + modeli
                String vehicleInfo = rs.getString("brand") + " " + rs.getString("model");
                rental.setVehicleInfo(vehicleInfo);

                rentals.add(rental);
            }

        } catch (SQLException e) {
            System.err.println("Kiralama geçmişi alınamadı: " + e.getMessage());
        }

        return rentals;
    }

}
