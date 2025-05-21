package Car_Rental_System;

import Car_Rental_System.Models.Rental;
import Car_Rental_System.Models.User;
import Car_Rental_System.Models.Vehicle;
import Car_Rental_System.Models.enums.RentalType;
import Car_Rental_System.Models.enums.VehicleCategory;
import Car_Rental_System.Services.AuthService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Car_Rental_System.Services.RentalService;
import Car_Rental_System.Services.VehicleService;

public class CarRentalSystemMain {

        private static final Scanner scanner = new Scanner(System.in);
        private static final AuthService authService = new AuthService();
        private static final VehicleService vehicleService = new VehicleService();
        private static final RentalService rentalService = new RentalService();

        public static void main(String[] args) {
            System.out.println("🚗 Araç Kiralama Uygulamasına Hoş Geldiniz!");

            User currentUser = null;

            // Kullanıcı giriş işlemleri
            while (currentUser == null) {
                System.out.println("\n1. Giriş Yap");
                System.out.println("2. Kayıt Ol");
                System.out.print("Seçiminiz: ");
                String input = scanner.nextLine();

                switch (input) {
                    case "1" -> currentUser = login();
                    case "2" -> currentUser = register();
                    default -> System.out.println("Geçersiz seçim.");
                }
            }

            // Giriş başarılı, kullanıcı menüsüne geç
            if (currentUser.isAdmin()) {
                adminPanel(currentUser);  // Eğer adminse, admin paneli
            } else {
                userPanel(currentUser);   // Eğer kullanıcıysa, normal kullanıcı paneli
            }
        }

        // Kullanıcı giriş metodu
        private static User login() {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Şifre: ");
            String password = scanner.nextLine();

            try {
                User user = authService.login(email, password);
                System.out.println("Hoş geldiniz, " + user.getName());
                return user;
            } catch (Exception e) {
                System.out.println("Giriş başarısız: " + e.getMessage());
                return null;
            }
        }

        // Kullanıcı kayıt metodu
        private static User register() {
            System.out.print("Adınız: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Şifre: ");
            String password = scanner.nextLine();
            System.out.print("Yaşınız: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Kurumsal mısınız? (evet/hayır): ");
            boolean isCorporate = scanner.nextLine().trim().equalsIgnoreCase("evet");

            try {
                authService.register(name, email, password, age, isCorporate);
                System.out.println("Kayıt başarılı. Giriş yapabilirsiniz.");
                return null;
            } catch (Exception e) {
                System.out.println("Kayıt başarısız: " + e.getMessage());
                return null;
            }
        }

        // Normal kullanıcı paneli
        private static void userPanel(User user) {
            boolean running = true;
            while (running) {
                System.out.println("\n🔹 Menü 🔹");
                System.out.println("1. Araçları Listele");
                System.out.println("2. Araç Kirala");
                System.out.println("3. Kiralama Geçmişim");
                System.out.println("4. Kategoriye Göre Filtrele");
                System.out.println("5. Çıkış");
                System.out.print("Seçiminiz: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> listVehicles();
                    case "2" -> rentVehicle(user);
                    case "3" -> viewRentals(user);
                    case "4" -> filterVehicles();
                    case "5" -> {
                        System.out.println("Çıkış yapılıyor. Görüşmek üzere!");
                        running = false;
                    }
                    default -> System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
                }
            }
        }

        // Admin paneli
        private static void adminPanel(User admin) {
            boolean running = true;
            while (running) {
                System.out.println("\n🔹 Admin Paneli 🔹");
                System.out.println("1. Araç Ekle");
                System.out.println("2. Çıkış");
                System.out.print("Seçiminiz: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addVehicle(admin);  // Admin araç ekler
                    case "2" -> {
                        System.out.println("Çıkış yapılıyor. Görüşmek üzere!");
                        running = false;
                    }
                    default -> System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
                }
            }
        }

        // Admin'in araç ekleme işlemi
        private static void addVehicle(User admin) {
            System.out.print("Marka: ");
            String brand = scanner.nextLine();
            System.out.print("Model: ");
            String model = scanner.nextLine();
            System.out.print("Kategori (CAR, MOTORCYCLE, HELICOPTER): ");
            VehicleCategory category = VehicleCategory.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Fiyat (₺): ");
            BigDecimal price = new BigDecimal(scanner.nextLine());
            System.out.print("Kiralama Ücreti (₺/saat): ");
            BigDecimal rentalRate = new BigDecimal(scanner.nextLine());

            Vehicle newVehicle = new Vehicle();
            newVehicle.setBrand(brand);
            newVehicle.setModel(model);
            newVehicle.setCategory(category);
            newVehicle.setPrice(price);
            newVehicle.setRentalRate(rentalRate);

            try {
                vehicleService.addVehicle(admin, newVehicle);  // Admin araç ekler
                System.out.println("Araç başarıyla eklendi.");
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }

        // Araçları listeleme
        private static void listVehicles() {
            try {
                List<Vehicle> vehicles = vehicleService.listVehiclesPaginated(0, 10);
                System.out.println("\n📋 Araç Listesi:");
                for (Vehicle v : vehicles) {
                    System.out.println(v.getId() + " - " + v.getBrand() + " " + v.getModel() +
                            " | ₺" + v.getRentalRate() + " / " + v.getCategory());
                }
            } catch (Exception e) {
                System.out.println("Araçlar listelenemedi: " + e.getMessage());
            }
        }

        // Araç kiralama işlemi
        private static void rentVehicle(User user) {
            try {
                List<Vehicle> vehicles = vehicleService.listVehiclesPaginated(0, 10);
                System.out.println("\nKiralanabilir Araçlar:");
                for (Vehicle v : vehicles) {
                    System.out.println(v.getId() + ": " + v.getBrand() + " " + v.getModel() +
                            " (₺" + v.getRentalRate() + " / " + v.getCategory() + ")");
                }

                System.out.print("Kiralamak istediğiniz aracın ID'si: ");
                int vehicleId = Integer.parseInt(scanner.nextLine());

                Vehicle selectedVehicle = vehicleService.getVehicleById(vehicleId);
                if (selectedVehicle == null) {
                    System.out.println("Geçersiz araç ID'si.");
                    return;
                }

                System.out.print("Kiralama türü (HOURLY, DAILY, WEEKLY, MONTHLY): ");
                RentalType rentalType = RentalType.valueOf(scanner.nextLine().toUpperCase());

                System.out.print("Süre (saat/gün/hafta/ay): ");
                int duration = Integer.parseInt(scanner.nextLine());

                rentalService.rentVehicle(user, selectedVehicle, rentalType, duration);
            } catch (IllegalArgumentException e) {
                System.out.println("Hatalı giriş: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Kiralama işlemi başarısız oldu: " + e.getMessage());
            }
        }

        // Kiralama geçmişini görüntüleme
        private static void viewRentals(User user) {
            try {
                List<Rental> rentals = rentalService.getUserRentals(user.getId());

                if (rentals.isEmpty()) {
                    System.out.println("Hiç kiralama yapılmamış.");
                } else {
                    System.out.println("📜 Kiralama Geçmişiniz:");
                    for (Rental r : rentals) {
                        System.out.println("Araç: " + r.getVehicleInfo());
                        System.out.println("Tarih: " + r.getStartDate() + " - " + r.getEndDate());
                        System.out.println("Tür: " + r.getRentalType());
                        System.out.println("Ücret: ₺" + r.getTotalPrice());
                        System.out.println("-----------");
                    }
                }
            } catch (Exception e) {
                System.out.println("Geçmiş görüntülenemedi: " + e.getMessage());
            }
        }

        // Araç kategorilerine göre filtreleme
        private static void filterVehicles() {
            try {
                System.out.print("Kategori seçin (CAR / MOTORCYCLE / HELICOPTER): ");
                VehicleCategory category = VehicleCategory.valueOf(scanner.nextLine().toUpperCase());

                List<Vehicle> filtered = vehicleService.filterByCategory(category);
                if (filtered.isEmpty()) {
                    System.out.println("Bu kategoriye ait araç bulunamadı.");
                } else {
                    System.out.println("Kategoriye ait araçlar:");
                    for (Vehicle v : filtered) {
                        System.out.println(v.getId() + ": " + v.getBrand() + " " + v.getModel() +
                                " - ₺" + v.getRentalRate());
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Geçersiz kategori. CAR, MOTORCYCLE veya HELICOPTER yazmalısınız.");
            } catch (Exception e) {
                System.out.println("Filtreleme hatası: " + e.getMessage());
            }
        }
    }


