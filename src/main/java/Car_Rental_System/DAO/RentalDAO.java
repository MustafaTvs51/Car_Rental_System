package Car_Rental_System.DAO;

import Car_Rental_System.Models.Rental;
import Car_Rental_System.Models.Vehicle;
import Car_Rental_System.Models.enums.RentalType;
import Car_Rental_System.Models.enums.VehicleCategory;
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

    public List<Rental> getRentalsByUserId(int userId) throws SQLException {
        String sql = """
        SELECT 
            r.id AS rental_id,
            r.user_id,
            r.vehicle_id,
            r.start_date,
            r.end_date,
            r.rental_type,
            r.total_price,
            v.id AS v_id,
            v.brand AS v_brand,
            v.model AS v_model,
            v.category AS v_category,
            v.price AS v_price,
            v.rental_rate AS v_rental_rate
        FROM rentals r
        JOIN vehicles v ON r.vehicle_id = v.id
        WHERE r.user_id = ?
    """;

        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("rental_id"));
                rental.setUserId(rs.getInt("user_id"));
                rental.setVehicleId(rs.getInt("vehicle_id"));
                rental.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
                rental.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
                rental.setRentalType(RentalType.valueOf(rs.getString("rental_type")));
                rental.setTotalPrice(rs.getBigDecimal("total_price"));

                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("v_id"));
                vehicle.setBrand(rs.getString("v_brand"));
                vehicle.setModel(rs.getString("v_model"));
                vehicle.setCategory(VehicleCategory.valueOf(rs.getString("v_category")));
                vehicle.setPrice(rs.getBigDecimal("v_price"));
                vehicle.setRentalRate(rs.getBigDecimal("v_rental_rate"));

                rental.setVehicle(vehicle);

                rentals.add(rental);
            }
        }

        return rentals;
    }



}
