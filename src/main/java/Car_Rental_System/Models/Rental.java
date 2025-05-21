package Car_Rental_System.Models;

import Car_Rental_System.Models.enums.RentalType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Rental {
    private int id;
    private int userId;
    private int vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RentalType rentalType;
    private BigDecimal totalPrice;

    public Rental() {}

    public Rental(int userId, int vehicleId, LocalDateTime startDate, LocalDateTime endDate, RentalType rentalType, BigDecimal totalPrice) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalType = rentalType;
        this.totalPrice = totalPrice;
    }

    public Rental(int id, int id1, LocalDateTime startDate, Object o) {

    }

    private String vehicleInfo;

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public static void setVehicleInfo(String vehicleInfo) {

    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

}
