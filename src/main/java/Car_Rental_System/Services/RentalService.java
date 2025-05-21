package Car_Rental_System.Services;

import Car_Rental_System.Models.enums.RentalType;
import Car_Rental_System.DAO.RentalDAO;
import Car_Rental_System.Models.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {
    private final RentalDAO rentalDAO = new RentalDAO();

    public void rentVehicle(User user, Vehicle vehicle, RentalType rentalType, int duration) throws Exception {
        // Kural kontrolleri:
        if (user.isCorporate() && rentalType != RentalType.MONTHLY) {
            throw new Exception("Kurumsal kullanıcılar yalnızca aylık kiralama yapabilir.");
        }

        if (vehicle.getPrice().compareTo(new BigDecimal("2000000")) > 0 && user.getAge() < 30) {
            throw new Exception("2 milyon TL üzeri araçları yalnızca 30 yaş üstü kullanıcılar kiralayabilir.");
        }

        Rental rental = new Rental();
        rental.setUserId(user.getId());
        rental.setVehicleId(vehicle.getId());
        rental.setRentalType(rentalType);

        LocalDateTime now = LocalDateTime.now();
        rental.setStartDate(now);

        LocalDateTime endDate = switch (rentalType) {
            case HOURLY -> now.plusHours(duration);
            case DAILY -> now.plusDays(duration);
            case WEEKLY -> now.plusWeeks(duration);
            case MONTHLY -> now.plusMonths(duration);
        };
        rental.setEndDate(endDate);

        BigDecimal totalPrice = vehicle.getRentalRate().multiply(new BigDecimal(duration));

        // Ek olarak depozito kontrolü:
        if (vehicle.getPrice().compareTo(new BigDecimal("2000000")) > 0 && user.getAge() >= 30) {
            BigDecimal deposit = vehicle.getPrice().multiply(new BigDecimal("0.10"));
            totalPrice = totalPrice.add(deposit);
        }

        rental.setTotalPrice(totalPrice);

        // Veritabanına kaydet
        rentalDAO.saveRental(rental);

        System.out.println("✅ Kiralama başarıyla tamamlandı!");
        System.out.println("Toplam Ücret: ₺" + totalPrice);
        System.out.println("Başlangıç: " + rental.getStartDate());
        System.out.println("Bitiş: " + rental.getEndDate());
    }
    public List<Rental> getUserRentals(int userId) {
        return rentalDAO.getRentalsByUserId(userId);
    }


}