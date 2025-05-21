package Car_Rental_System.Models;

import Car_Rental_System.DAO.VehicleDAO;
import Car_Rental_System.Models.enums.VehicleCategory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private VehicleCategory category;
    private BigDecimal price; // Araç bedeli
    private BigDecimal depositRate; // Depozito oranı (örneğin: 0.10)
    private BigDecimal rentalRate; // Saatlik/günlük/aylık ücret

    public Vehicle() {}

    public Vehicle(String brand, String model, VehicleCategory category, BigDecimal price, BigDecimal depositRate, BigDecimal rentalRate) {
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.price = price;
        this.depositRate = depositRate;
        this.rentalRate = rentalRate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(VehicleCategory category) {
        this.category = category;
    }

    public BigDecimal getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(BigDecimal depositRate) {
        this.depositRate = depositRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public List<Vehicle> getVehiclesByCategoryAndPage(VehicleCategory category, int page, int pageSize) throws SQLException {
        return VehicleDAO.listVehiclesByCategoryWithPagination(category.name(), page, pageSize);
    }

}
