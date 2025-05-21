
# 🚗 Java ile Geliştirilmiş Araç Kiralama Sistemi

Bu proje, Java ile geliştirilmiş terminal tabanlı bir **Araç Kiralama Uygulaması**dır. Kullanıcılar sisteme giriş yapabilir, araçları görüntüleyebilir ve kiralama işlemlerini yönetebilirler. Aynı zamanda araç ve kullanıcı yönetimini sağlayan servis yapıları da bulunmaktadır.

## 📌 Özellikler

- Kullanıcı kayıt ve giriş işlemleri
- Araç listeleme ve filtreleme
- Kiralık araçların kiralanması ve iade edilmesi
- Saatlik ve günlük kiralama türleri
- Araç kategorilerine göre sınıflandırma
- Modüler ve katmanlı mimari (Model, Service, Enum, Main)

## 🛠️ Kullanılan Teknolojiler

- **Java 21**
- **Katmanlı Mimari**
  - `Models`, `Services`, `Enums`, `Main`
- **OOP (Nesne Yönelimli Programlama)**
- **BigDecimal** ile para hesaplamaları
- **Exception Handling** (SQLException dahil)
- **Enum** ile kiralama türü ve araç kategorisi modelleme
- **Maven** Archetype kullanıldı.

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Java 21 veya üzeri
- Bir IDE (Örneğin IntelliJ IDEA veya Eclipse)
- Terminal veya konsol

### Adımlar

1. Bu repoyu klonlayın:
   ```bash
   git clone https://github.com/MustafaTvs51/Car_Rental_System.git
   ```
2. Projeyi bir Java IDE ile açın.

## 📂 Proje Yapısı

```
Car_Rental_System/
├── Models/
│   ├── User.java
│   ├── Vehicle.java
│   ├── Rental.java
│   └── enums/
│       ├── RentalType.java
│       └── VehicleCategory.java
├── Services/
│   ├── AuthService.java
│   ├── RentalService.java
│   └── VehicleService.java
├── CarRentalSystemMain.java
```

## 📸 Ekran Görüntüsü (Terminal)

```
🚗 Araç Kiralama Uygulamasına Hoş Geldiniz!
1- Giriş Yap
2- Kayıt Ol
3- Çıkış
```
## 📄 Lisans

Bu proje MIT lisansı ile lisanslanmıştır.
