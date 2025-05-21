package Car_Rental_System.DAO;

import Car_Rental_System.Models.Vehicle;
import Car_Rental_System.Models.enums.VehicleCategory;
import Car_Rental_System.Utils.DB_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public void addVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicles (brand, model, category, price, deposit_rate, rental_rate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicle.getBrand());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getCategory().name());
            stmt.setBigDecimal(4, vehicle.getPrice());
            stmt.setBigDecimal(5, vehicle.getDepositRate());
            stmt.setBigDecimal(6, vehicle.getRentalRate());
            stmt.executeUpdate();
        }
    }

    public List<Vehicle> listAllVehicles() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DB_util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setBrand(rs.getString("brand"));
                v.setModel(rs.getString("model"));
                v.setCategory(VehicleCategory.valueOf(rs.getString("category")));
                v.setPrice(rs.getBigDecimal("price"));
                v.setDepositRate(rs.getBigDecimal("deposit_rate"));
                v.setRentalRate(rs.getBigDecimal("rental_rate"));
                list.add(v);
            }
        }
        return list;
    }

    public static List<Vehicle> listVehiclesByCategoryWithPagination(String category, int page, int pageSize) throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE category = ? ORDER BY id LIMIT ? OFFSET ?";
        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.toUpperCase());
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setBrand(rs.getString("brand"));
                v.setModel(rs.getString("model"));
                v.setCategory(VehicleCategory.valueOf(rs.getString("category")));
                v.setPrice(rs.getBigDecimal("price"));
                v.setDepositRate(rs.getBigDecimal("deposit_rate"));
                v.setRentalRate(rs.getBigDecimal("rental_rate"));
                list.add(v);
            }
        }
        return list;
    }

    public List<Vehicle> getVehiclesPaginated(int offset, int limit) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setCategory(VehicleCategory.valueOf(rs.getString("category")));
                vehicle.setPrice(rs.getBigDecimal("price"));
                vehicle.setRentalRate(rs.getBigDecimal("rental_rate"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println("Araç listeleme hatası: " + e.getMessage());
        }

        return vehicles;
    }
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setCategory(VehicleCategory.valueOf(rs.getString("category")));
                vehicle.setPrice(rs.getBigDecimal("price"));
                vehicle.setRentalRate(rs.getBigDecimal("rental_rate"));
                return vehicle;
            }
        } catch (SQLException e) {
            System.err.println("Araç sorgulanamadı: " + e.getMessage());
        }

        return null; // Eğer araç bulunmazsa
    }
    public List<Vehicle> getVehiclesByCategory(VehicleCategory category) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE category = ? ORDER BY id";

        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setCategory(VehicleCategory.valueOf(rs.getString("category")));
                vehicle.setPrice(rs.getBigDecimal("price"));
                vehicle.setRentalRate(rs.getBigDecimal("rental_rate"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println("Kategoriye göre araç listelenemedi: " + e.getMessage());
        }

        return vehicles;
    }
    public void saveVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicles (brand, model, category, price, rental_rate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB_util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicle.getBrand());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getCategory().name()); // enum'ı string'e çeviriyoruz
            stmt.setBigDecimal(4, vehicle.getPrice());
            stmt.setBigDecimal(5, vehicle.getRentalRate());
            stmt.executeUpdate();
        }
    }

}