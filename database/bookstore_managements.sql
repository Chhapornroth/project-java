-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 30, 2024 at 02:42 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookstore_managements`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_book_records`
--

DROP TABLE IF EXISTS `tbl_book_records`;
CREATE TABLE IF NOT EXISTS `tbl_book_records` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `author_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `stock` int NOT NULL,
  `adding_date` date NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_book_records`
--

INSERT INTO `tbl_book_records` (`book_id`, `title`, `author_name`, `stock`, `adding_date`) VALUES
(1, 'Think and Grow Rich', 'Napoleon Hill', 4, '2024-03-03'),
(2, 'Everyting Is F***ed', 'Mark Manson', 5, '2024-05-15'),
(3, 'What You Think of Me is None of My Business', 'Terry Cole-Whittaker', 5, '2024-12-24');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_employee_records`
--

DROP TABLE IF EXISTS `tbl_employee_records`;
CREATE TABLE IF NOT EXISTS `tbl_employee_records` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `gender` varchar(6) COLLATE utf8mb4_general_ci NOT NULL,
  `phone_number` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `birthday` date NOT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_employee_records`
--

INSERT INTO `tbl_employee_records` (`employee_id`, `name`, `gender`, `phone_number`, `birthday`) VALUES
(1, 'YIN CHHAPORNROTH', 'Male', '069305880', '2004-09-15');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_transactions_records`
--

DROP TABLE IF EXISTS `tbl_transactions_records`;
CREATE TABLE IF NOT EXISTS `tbl_transactions_records` (
  `txn_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `book_id` int NOT NULL,
  `employee_id` int NOT NULL,
  `price` float NOT NULL,
  `qty` int NOT NULL,
  `discount` float NOT NULL,
  `total` float GENERATED ALWAYS AS ((case when (`discount` <= 0) then (`price` * `qty`) else ((`price` * `qty`) - ((`price` * `qty`) * `discount`)) end)) STORED,
  `date` date NOT NULL,
  PRIMARY KEY (`txn_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_transactions_records`
--

INSERT INTO `tbl_transactions_records` (`txn_id`, `customer_name`, `book_id`, `employee_id`, `price`, `qty`, `discount`, `date`) VALUES
(1, 'sebastian', 3, 1, 15, 2, 0.05, '2024-08-18'),
(2, 'Tom', 2, 2, 10, 2, 0, '2024-08-18'),
(3, 'Alfia', 4, 2, 20, 3, 0.2, '2024-08-18');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
