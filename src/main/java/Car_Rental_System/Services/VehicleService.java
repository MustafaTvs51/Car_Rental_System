package Car_Rental_System.Services;

import Car_Rental_System.DAO.VehicleDAO;
import Car_Rental_System.Models.User;
import Car_Rental_System.Models.Vehicle;
import Car_Rental_System.Models.enums.VehicleCategory;

import java.sql.SQLException;
import java.util.List;

public class VehicleService {
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    public void addVehicle(Vehicle vehicle) throws SQLException {
        vehicleDAO.addVehicle(vehicle);
    }

    public List<Vehicle> listVehicles() throws SQLException {
        return vehicleDAO.listAllVehicles();
    }
    public List<Vehicle> getVehiclesByCategoryAndPage(VehicleCategory category, int page, int pageSize) throws SQLException {
        return vehicleDAO.listVehiclesByCategoryWithPagination(category.name(), page, pageSize);
    }
    public List<Vehicle> listVehiclesPaginated(int page, int pageSize) {
        int offset = page * pageSize;
        return vehicleDAO.getVehiclesPaginated(offset, pageSize);
    }
    public Vehicle getVehicleById(int id) {
        return vehicleDAO.getVehicleById(id);
    }
    public List<Vehicle> filterByCategory(VehicleCategory category) {
        return vehicleDAO.getVehiclesByCategory(category);
    }
    public void addVehicle(User admin, Vehicle vehicle) throws Exception {
        if (!admin.isAdmin()) {
            throw new Exception("Sadece admin kullanıcıları araç ekleyebilir.");
        }
        vehicleDAO.saveVehicle(vehicle); // Araç veritabanına kaydedilir
        System.out.println("Araç başarıyla sisteme eklendi.");
    }

}
