-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 12 Kas 2023, 00:54:01
-- Sunucu sürümü: 8.0.31
-- PHP Sürümü: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `tourismagency`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `book`
--

CREATE TABLE `book` (
  `id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `lodgings_id` int NOT NULL,
  `room_id` int NOT NULL,
  `book_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `book_idno` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `book_phone` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `book_mail` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `book_start_date` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `book_end_date` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `book_price` double NOT NULL,
  `book_note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `book`
--

INSERT INTO `book` (`id`, `hotel_id`, `lodgings_id`, `room_id`, `book_name`, `book_idno`, `book_phone`, `book_mail`, `book_start_date`, `book_end_date`, `book_price`, `book_note`) VALUES
(1, 1, 1, 2, 'Ayşe Yıldız', '11111111111', '05355555555', 'aysyldz@gmail.com', '03/03/2024', '05/03/2024', 8000, ''),
(2, 5, 9, 7, 'Ali Akten', '22222222222', '5366666666', 'alktn@gmail.com', '03/03/2024', '05/03/2024', 6000, '16.00 gibi giriş yapacağım'),
(3, 2, 4, 3, 'Yasin Taner Yılmaz', '33333333333', '05355555444', 'ysntnr@gmail.com', '01/04/2024', '05/04/2024', 2400, 'yok),
(4, 5, 9, 8, 'Sude Çam', '44444444444', '5466666666', 'sdcm@gmail.com', '03/12/2024', '06/12/2024', 3600, '');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hotel`
--

CREATE TABLE `hotel` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `city` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `region` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `eposta` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone_number` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `star` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `feature` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hotel`
--

INSERT INTO `hotel` (`id`, `name`, `city`, `region`, `address`, `eposta`, `phone_number`, `star`, `feature`) VALUES
(1, 'Aksu Hotel', 'Giresun', 'Karadeniz', 'Giresun/Merkez', 'aksu@htl.com', '4542150328', '5', 'Ücretsiz Otopark, Ücretsiz Wifi,SPA...'),
(2, 'Başar Hotel', 'İstanbul', 'Marmara', 'Beşiktaş/İstanbul', 'bsr@htl.com', '02124444444', '5', 'SPA, 7/24 Oda Servisi, Ücretsiz Wifi'),
(3, 'Aydın Hotel', 'İstanbul', 'Marmara', 'Ataşehir/İstanbul', 'aydn@htl.com', '02164714545', '4', 'Ücretsiz Otopark, Yüzme Havuzu, SPA...'),
(4, 'Hilton Hotel', 'Kütahya', 'Ege', 'Kütahya/Merkez', 'hltn@htl.com', '05555555555', '3', 'Ücretsiz WİFİ'),
(5, 'Aydınlık Hotel', 'Ankara', 'İç Anadolu', 'Çankaya/Ankara', 'aydnlk@htl.com', '05355555555', '4', 'Ücretsiz Otopar, Yüzme Havuzu, 7/24 Oda Servis...');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `lodgings`
--

CREATE TABLE `lodgings` (
  `id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `lodgings`
--

INSERT INTO `lodgings` (`id`, `hotel_id`, `type`) VALUES
(1, 1, 'Ultra Herşey Dahil'),
(2, 1, 'Herşey Dahil'),
(3, 1, 'Alkol Hariç Full credit'),
(4, 2, 'Oda Kahvaltı'),
(5, 2, 'Sadece Yatak'),
(6, 3, 'Tam Pansiyon'),
(7, 3, 'Yarım Pansiyon'),
(8, 4, 'Yarım Pansiyon'),
(9, 5, 'Oda Kahvaltı'),
(10, 5, 'Tam Pansiyon'),
(11, 5, 'Yarım Pansiyon'),
(12, 5, 'Herşey Dahil'),
(13, 5, 'Alkol Hariç Full credit'),
(14, 2, 'Alkol Hariç Full credit'),
(15, 2, 'Ultra Herşey Dahil'),
(16, 2, 'Herşey Dahil');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `room`
--

CREATE TABLE `room` (
  `id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `lodgings_id` int NOT NULL,
  `season_id` int NOT NULL,
  `room_type` enum('Single','Double','Suit') COLLATE utf8mb4_general_ci NOT NULL,
  `stock` int NOT NULL,
  `bed_number` int NOT NULL,
  `sqr_meter` int NOT NULL,
  `other_features` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `adult_price` double NOT NULL,
  `child_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `room`
--

INSERT INTO `room` (`id`, `hotel_id`, `lodgings_id`, `season_id`, `room_type`, `stock`, `bed_number`, `sqr_meter`, `other_features`, `adult_price`, `child_price`) VALUES
(1, 1, 1, 1, 'Single', 3, 5, 50, 'Kasa, Oyun Konsolu,Mini Bar...', 1000, 750),
(2, 1, 1, 2, 'Double', 3, 4, 40, 'Televizyon, Mini Bar', 800, 500),
(3, 2, 4, 3, 'Suit', 1, 10, 100, 'Televizyon,Mini Bar, Kasa...', 1200, 1000),
(4, 2, 4, 4, 'Single', 2, 2, 30, 'Kasa,Projeksiyon, Televizyon', 800, 500),
(5, 2, 4, 3, 'Double', 3, 5, 45, 'Oyun Konsolu,Minibar,Televizyon..', 1200, 1000),
(6, 3, 6, 5, 'Suit', 4, 3, 30, 'Minibar, Projeksiyon', 750, 700),
(7, 5, 9, 9, 'Double', 4, 10, 80, 'Televizyon,Kasa,Oyun Konsolu', 1500, 1000),
(8, 5, 9, 10, 'Suit', 2, 4, 50, 'Oyun Konsolu, Kasa, Minibar...', 600, 400);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `season`
--

CREATE TABLE `season` (
  `id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `season_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `end_date` varchar(10) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `season`
--

INSERT INTO `season` (`id`, `hotel_id`, `season_name`, `start_date`, `end_date`) VALUES
(1, 1, 'Kış Dönemi', '01/11/2023', '01/03/2024'),
(2, 1, 'Yaz Dönemi', '02/03/2024', '31/10/2024'),
(3, 2, 'Kış Dönemi', '01/01/2024', '31/05/2024'),
(4, 2, 'Yaz Dönemi', '01/06/2024', '31/12/2024'),
(5, 3, 'Yaz Dönemi', '01/06/2024', '31/12/2024'),
(6, 3, 'Kış Dönemi', '01/01/2024', '31/05/2024'),
(7, 4, 'Kış Dönemi', '01/11/2024', '15/03/2024'),
(8, 4, 'Yaz Dönemi', '16/03/2024', '31/10/2024'),
(9, 5, 'Kış Dönemi', '01/01/2024', '31/05/2024'),
(10, 5, 'Yaz Dönemi', '01/06/2024', '31/12/2024');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `user_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_pass` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_type` enum('admin','employee') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `user`
--

INSERT INTO `user` (`id`, `user_name`, `user_uname`, `user_pass`, `user_type`) VALUES
(1, 'Tuğba İşleyen', 'tuba', '123', 'admin'),
(3, 'Yasin İşleyen', 'yasin', '123', 'employee'),
(4, 'Talip Çoban', 'talip', '123', 'employee'),
(5, 'Gaye Ak', 'x', 'x', 'employee');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `lodgings`
--
ALTER TABLE `lodgings`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `season`
--
ALTER TABLE `season`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `book`
--
ALTER TABLE `book`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Tablo için AUTO_INCREMENT değeri `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Tablo için AUTO_INCREMENT değeri `lodgings`
--
ALTER TABLE `lodgings`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Tablo için AUTO_INCREMENT değeri `room`
--
ALTER TABLE `room`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Tablo için AUTO_INCREMENT değeri `season`
--
ALTER TABLE `season`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Tablo için AUTO_INCREMENT değeri `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
