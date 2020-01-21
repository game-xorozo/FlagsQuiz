-- phpMyAdmin SQL Dump
-- version 4.7.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 07, 2017 at 09:11 AM
-- Server version: 5.6.38
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nileworx_flags`
--

-- --------------------------------------------------------

--
-- Table structure for table `coins`
--

CREATE TABLE `coins` (
  `_coid` int(11) NOT NULL,
  `total_coins` int(11) DEFAULT NULL,
  `used_coins` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `coins`
--

INSERT INTO `coins` (`_coid`, `total_coins`, `used_coins`) VALUES
(1, 25, 0);

-- --------------------------------------------------------

--
-- Table structure for table `flags`
--

CREATE TABLE `flags` (
  `_flid` int(11) NOT NULL,
  `fl_country` text,
  `fl_continent` text,
  `fl_image` text,
  `fl_wikipedia` text,
  `fl_letter` text,
  `fl_tries` int(11) DEFAULT NULL,
  `fl_score` int(11) DEFAULT NULL,
  `fl_points` int(11) DEFAULT NULL,
  `fl_completed` text,
  `fl_image_sdcard` int(11) DEFAULT NULL,
  `fl_order` int(11) DEFAULT NULL,
  `fl_status` int(11) DEFAULT NULL,
  `fl_web_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flags`
--

INSERT INTO `flags` (`_flid`, `fl_country`, `fl_continent`, `fl_image`, `fl_wikipedia`, `fl_letter`, `fl_tries`, `fl_score`, `fl_points`, `fl_completed`, `fl_image_sdcard`, `fl_order`, `fl_status`, `fl_web_id`) VALUES
(1, 'United Kingdom', 'Europe', '001.png', 'https://en.wikipedia.org/wiki/Flag_of_the_United_Kingdom', NULL, 0, 0, 0, '0', 0, 1, 1, 1),
(2, 'France', 'Europe', '002.png', 'https://en.wikipedia.org/wiki/Flag_of_France', NULL, 0, 0, 0, '0', 0, 2, 1, 2),
(3, 'Italy', 'Europe', '003.png', 'https://en.wikipedia.org/wiki/Italy_flag', NULL, 0, 0, 0, '0', 0, 3, 1, 3),
(4, 'Spain', 'Europe', '004.png', 'https://en.wikipedia.org/wiki/Flag_of_Spain', NULL, 0, 0, 0, '0', 0, 4, 1, 4),
(5, 'Germany', 'Europe', '005.png', 'https://en.wikipedia.org/wiki/Flag_of_Germany', NULL, 0, 0, 0, '0', 0, 5, 1, 5),
(6, 'Sweden', 'Europe', '006.png', 'https://en.wikipedia.org/wiki/Flag_of_Sweden', NULL, 0, 0, 0, '0', 0, 6, 1, 6),
(7, 'Belgium', 'Europe', '007.png', 'https://en.wikipedia.org/wiki/Flag_of_Belgium', NULL, 0, 0, 0, '0', 0, 7, 1, 7),
(8, 'Denmark', 'Europe', '008.png', 'https://en.wikipedia.org/wiki/Flag_of_Denmark', NULL, 0, 0, 0, '0', 0, 8, 1, 8),
(9, 'Greece', 'Europe', '009.png', 'https://en.wikipedia.org/wiki/Flag_of_Greece', NULL, 0, 0, 0, '0', 0, 9, 1, 9),
(10, 'Turkey', 'Europe', '010.png', 'https://en.wikipedia.org/wiki/Flag_of_Turkey', NULL, 0, 0, 0, '0', 0, 10, 1, 10),
(11, 'Portugal', 'Europe', '011.png', 'https://en.wikipedia.org/wiki/Flag_of_Portugal', NULL, 0, 0, 0, '0', 0, 11, 1, 11),
(12, 'Romania', 'Europe', '012.png', 'https://en.wikipedia.org/wiki/Flag_of_Romania', NULL, 0, 0, 0, '0', 0, 12, 1, 12),
(13, 'Switzerland', 'Europe', '013.png', 'https://en.wikipedia.org/wiki/Flag_of_Switzerland', NULL, 0, 0, 0, '0', 0, 13, 1, 13),
(14, 'Croatia', 'Europe', '014.png', 'https://en.wikipedia.org/wiki/Flag_of_Croatia', NULL, 0, 0, 0, '0', 0, 14, 1, 14),
(15, 'Finland', 'Europe', '015.png', 'https://en.wikipedia.org/wiki/Flag_of_Finland', NULL, 0, 0, 0, '0', 0, 15, 1, 15),
(16, 'Ireland', 'Europe', '016.png', 'https://en.wikipedia.org/wiki/Flag_of_Ireland', NULL, 0, 0, 0, '0', 0, 16, 1, 16),
(17, 'Netherlands', 'Europe', '017.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Netherlands', NULL, 0, 0, 0, '0', 0, 17, 1, 17),
(18, 'Poland', 'Europe', '018.png', 'https://en.wikipedia.org/wiki/Flag_of_Poland', NULL, 0, 0, 0, '0', 0, 18, 1, 18),
(19, 'Slovakia', 'Europe', '019.png', 'https://en.wikipedia.org/wiki/Flag_of_Slovakia', NULL, 0, 0, 0, '0', 0, 19, 1, 19),
(20, 'Bulgaria', 'Europe', '020.png', 'https://en.wikipedia.org/wiki/Flag_of_Bulgaria', NULL, 0, 0, 0, '0', 0, 20, 1, 20),
(21, 'Albania', 'Europe', '021.png', 'https://en.wikipedia.org/wiki/Flag_of_Albania', NULL, 0, 0, 0, '0', 0, 21, 1, 21),
(22, 'Armenia', 'Europe', '022.png', 'https://en.wikipedia.org/wiki/Flag_of_Armenia', NULL, 0, 0, 0, '0', 0, 22, 1, 22),
(23, 'Norway', 'Europe', '023.png', 'https://en.wikipedia.org/wiki/Flag_of_Norway', NULL, 0, 0, 0, '0', 0, 23, 1, 23),
(24, 'Iceland', 'Europe', '024.png', 'https://en.wikipedia.org/wiki/Flag_of_Iceland', NULL, 0, 0, 0, '0', 0, 24, 1, 24),
(25, 'Georgia', 'Europe', '025.png', 'https://en.wikipedia.org/wiki/Flag_of_Georgia_(country)', NULL, 0, 0, 0, '0', 0, 25, 1, 25),
(26, 'Slovenia', 'Europe', '026.png', 'https://en.wikipedia.org/wiki/Flag_of_Slovenia', NULL, 0, 0, 0, '0', 0, 26, 1, 26),
(27, 'Austria', 'Europe', '027.png', 'https://en.wikipedia.org/wiki/Flag_of_Austria', NULL, 0, 0, 0, '0', 0, 27, 1, 27),
(28, 'Andorra', 'Europe', '028.png', 'https://en.wikipedia.org/wiki/Flag_of_Andorra', NULL, 0, 0, 0, '0', 0, 28, 1, 28),
(29, 'Aruba', 'Europe', '029.png', 'https://en.wikipedia.org/wiki/Flag_of_Aruba', NULL, 0, 0, 0, '0', 0, 29, 1, 29),
(30, 'Azerbaijan', 'Europe', '030.png', 'https://en.wikipedia.org/wiki/Flag_of_Azerbaijan', NULL, 0, 0, 0, '0', 0, 30, 1, 30),
(31, 'Belarus', 'Europe', '031.png', 'https://en.wikipedia.org/wiki/Flag_of_Belarus', NULL, 0, 0, 0, '0', 0, 31, 1, 31),
(32, 'Bosnia', 'Europe', '032.png', 'https://en.wikipedia.org/wiki/Flag_of_Bosnia_and_Herzegovina', NULL, 0, 0, 0, '0', 0, 32, 1, 32),
(33, 'Cyprus', 'Europe', '033.png', 'https://en.wikipedia.org/wiki/Flag_of_Cyprus', NULL, 0, 0, 0, '0', 0, 33, 1, 33),
(34, 'Czech Republic', 'Europe', '034.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Czech_Republic', NULL, 0, 0, 0, '0', 0, 34, 1, 34),
(35, 'Estonia', 'Europe', '035.png', 'https://en.wikipedia.org/wiki/Flag_of_Estonia', NULL, 0, 0, 0, '0', 0, 35, 1, 35),
(36, 'Hungary', 'Europe', '036.png', 'https://en.wikipedia.org/wiki/Flag_of_Hungary', NULL, 0, 0, 0, '0', 0, 36, 1, 36),
(37, 'Kazakhstan', 'Europe', '037.png', 'https://en.wikipedia.org/wiki/Flag_of_Kazakhstan', NULL, 0, 0, 0, '0', 0, 37, 1, 37),
(38, 'Latvia', 'Europe', '038.png', 'https://en.wikipedia.org/wiki/Flag_of_Latvia', NULL, 0, 0, 0, '0', 0, 38, 1, 38),
(39, 'Liechtenstein', 'Europe', '039.png', 'https://en.wikipedia.org/wiki/Flag_of_Liechtenstein', NULL, 0, 0, 0, '0', 0, 39, 1, 39),
(40, 'Lithuania', 'Europe', '040.png', 'https://en.wikipedia.org/wiki/Flag_of_Lithuania', NULL, 0, 0, 0, '0', 0, 40, 1, 40),
(41, 'Luxembourg', 'Europe', '041.png', 'https://en.wikipedia.org/wiki/Flag_of_Luxembourg', NULL, 0, 0, 0, '0', 0, 41, 1, 41),
(42, 'Macedonia', 'Europe', '042.png', 'https://en.wikipedia.org/wiki/Flag_of_Macedonia', NULL, 0, 0, 0, '0', 0, 42, 1, 42),
(43, 'Malta', 'Europe', '043.png', 'https://en.wikipedia.org/wiki/Flag_of_Malta', NULL, 0, 0, 0, '0', 0, 43, 1, 43),
(44, 'Moldova', 'Europe', '044.png', 'https://en.wikipedia.org/wiki/Flag_of_Moldova', NULL, 0, 0, 0, '0', 0, 44, 1, 44),
(45, 'Monaco', 'Europe', '045.png', 'https://en.wikipedia.org/wiki/Flag_of_Monaco', NULL, 0, 0, 0, '0', 0, 45, 1, 45),
(46, 'Russia', 'Europe', '046.png', 'https://en.wikipedia.org/wiki/Flag_of_Russia', NULL, 0, 0, 0, '0', 0, 46, 1, 46),
(47, 'Saint Pierre', 'Europe', '047.png', 'https://en.wikipedia.org/wiki/Flag_of_Saint_Pierre_and_Miquelon', NULL, 0, 0, 0, '0', 0, 47, 1, 47),
(48, 'San Marino', 'Europe', '048.png', 'https://en.wikipedia.org/wiki/Flag_of_San_Marino', NULL, 0, 0, 0, '0', 0, 48, 1, 48),
(49, 'Serbia and Montenegro', 'Europe', '049.png', 'https://en.wikipedia.org/wiki/Flag_of_Serbia_and_Montenegro', NULL, 0, 0, 0, '0', 0, 49, 1, 49),
(50, 'Ukraine', 'Europe', '050.png', 'https://en.wikipedia.org/wiki/Flag_of_Ukraine', NULL, 0, 0, 0, '0', 0, 50, 1, 50),
(51, 'Vatican City', 'Europe', '051.png', 'https://en.wikipedia.org/wiki/Flag_of_Vatican_City', NULL, 0, 0, 0, '0', 0, 51, 1, 51),
(52, 'United States', 'North America', '052.png', 'https://en.wikipedia.org/wiki/Flag_of_the_United_States', NULL, 0, 0, 0, '0', 0, 52, 1, 52),
(53, 'Mexico', 'North America', '053.png', 'https://en.wikipedia.org/wiki/Flag_of_Mexico', NULL, 0, 0, 0, '0', 0, 53, 1, 53),
(54, 'Canada', 'North America', '054.png', 'https://en.wikipedia.org/wiki/Flag_of_Canada', NULL, 0, 0, 0, '0', 0, 54, 1, 54),
(55, 'Cuba', 'North America', '055.png', 'https://en.wikipedia.org/wiki/Flag_of_Cuba', NULL, 0, 0, 0, '0', 0, 55, 1, 55),
(56, 'Costa Rica', 'North America', '056.png', 'https://en.wikipedia.org/wiki/Flag_of_Costa_Rica', NULL, 0, 0, 0, '0', 0, 56, 1, 56),
(57, 'E lSalvador', 'North America', '057.png', 'https://en.wikipedia.org/wiki/Flag_of_El_Salvador', NULL, 0, 0, 0, '0', 0, 57, 1, 57),
(58, 'Jamaica', 'North America', '058.png', 'https://en.wikipedia.org/wiki/Flag_of_Jamaica', NULL, 0, 0, 0, '0', 0, 58, 1, 58),
(59, 'Panama', 'North America', '059.png', 'https://en.wikipedia.org/wiki/Flag_of_Panama', NULL, 0, 0, 0, '0', 0, 59, 1, 59),
(60, 'Honduras', 'North America', '060.png', 'https://en.wikipedia.org/wiki/Flag_of_Honduras', NULL, 0, 0, 0, '0', 0, 60, 1, 60),
(61, 'American Samoa', 'North America', '061.png', 'https://en.wikipedia.org/wiki/Flag_of_American_Samoa', NULL, 0, 0, 0, '0', 0, 61, 1, 61),
(62, 'Antigua and Barbuda', 'North America', '062.png', 'https://en.wikipedia.org/wiki/Flag_of_Antigua_and_Barbuda', NULL, 0, 0, 0, '0', 0, 62, 1, 62),
(63, 'Bahamas', 'North America', '063.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Bahamas', NULL, 0, 0, 0, '0', 0, 63, 1, 63),
(64, 'Barbados', 'North America', '064.png', 'https://en.wikipedia.org/wiki/Flag_of_Barbados', NULL, 0, 0, 0, '0', 0, 64, 1, 64),
(65, 'Belize', 'North America', '065.png', 'https://en.wikipedia.org/wiki/Flag_of_Belize', NULL, 0, 0, 0, '0', 0, 65, 1, 65),
(66, 'Dominica', 'North America', '066.png', 'https://en.wikipedia.org/wiki/Flag_of_Dominica', NULL, 0, 0, 0, '0', 0, 66, 1, 66),
(67, 'Dominican Republic', 'North America', '067.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Dominican_Republic', NULL, 0, 0, 0, '0', 0, 67, 1, 67),
(68, 'Grenada', 'North America', '068.png', 'https://en.wikipedia.org/wiki/Flag_of_Grenada', NULL, 0, 0, 0, '0', 0, 68, 1, 68),
(69, 'Guatemala', 'North America', '069.png', 'https://en.wikipedia.org/wiki/Flag_of_Guatemala', NULL, 0, 0, 0, '0', 0, 69, 1, 69),
(70, 'Haiti', 'North America', '070.png', 'https://en.wikipedia.org/wiki/Flag_of_Haiti', NULL, 0, 0, 0, '0', 0, 70, 1, 70),
(71, 'Nicaragua', 'North America', '071.png', 'https://en.wikipedia.org/wiki/Flag_of_Nicaragua', NULL, 0, 0, 0, '0', 0, 71, 1, 71),
(72, 'Saint Kitts and Nevis', 'North America', '072.png', 'https://en.wikipedia.org/wiki/Flag_of_Saint_Kitts_and_Nevis', NULL, 0, 0, 0, '0', 0, 72, 1, 72),
(73, 'Saint Lucia', 'North America', '073.png', 'https://en.wikipedia.org/wiki/Flag_of_Saint_Lucia', NULL, 0, 0, 0, '0', 0, 73, 1, 73),
(74, 'Saint Vincent and the Grenadines', 'North America', '074.png', 'https://en.wikipedia.org/wiki/Flag_of_Saint_Vincent_and_the_Grenadines', NULL, 0, 0, 0, '0', 0, 74, 1, 74),
(75, 'Trinidad and Tobago', 'North America', '075.png', 'https://en.wikipedia.org/wiki/Flag_of_Trinidad_and_Tobago', NULL, 0, 0, 0, '0', 0, 75, 1, 75),
(76, 'Argentina', 'South America', '076.png', 'https://en.wikipedia.org/wiki/Flag_of_Argentina', NULL, 0, 0, 0, '0', 0, 76, 1, 76),
(77, 'Brazil', 'South America', '077.png', 'https://en.wikipedia.org/wiki/Flag_of_Brazil', NULL, 0, 0, 0, '0', 0, 77, 1, 77),
(78, 'Chile', 'South America', '078.png', 'https://en.wikipedia.org/wiki/Flag_of_Chile', NULL, 0, 0, 0, '0', 0, 78, 1, 78),
(79, 'Bolivia', 'South America', '079.png', 'https://en.wikipedia.org/wiki/Flag_of_Bolivia', NULL, 0, 0, 0, '0', 0, 79, 1, 79),
(80, 'Colombia', 'South America', '080.png', 'https://en.wikipedia.org/wiki/Flag_of_Colombia', NULL, 0, 0, 0, '0', 0, 80, 1, 80),
(81, 'Ecuador', 'South America', '081.png', 'https://en.wikipedia.org/wiki/Flag_of_Ecuador', NULL, 0, 0, 0, '0', 0, 81, 1, 81),
(82, 'Paraguay', 'South America', '082.png', 'https://en.wikipedia.org/wiki/Flag_of_Paraguay', NULL, 0, 0, 0, '0', 0, 82, 1, 82),
(83, 'Peru', 'South America', '083.png', 'https://en.wikipedia.org/wiki/Flag_of_Peru', NULL, 0, 0, 0, '0', 0, 83, 1, 83),
(84, 'Uruguay', 'South America', '084.png', 'https://en.wikipedia.org/wiki/Flag_of_Uruguay', NULL, 0, 0, 0, '0', 0, 84, 1, 84),
(85, 'Guyana', 'South America', '085.png', 'https://en.wikipedia.org/wiki/Flag_of_Guyana', NULL, 0, 0, 0, '0', 0, 85, 1, 85),
(86, 'Suriname', 'South America', '086.png', 'https://en.wikipedia.org/wiki/Flag_of_Suriname', NULL, 0, 0, 0, '0', 0, 86, 1, 86),
(87, 'Venezuela', 'South America', '087.png', 'https://en.wikipedia.org/wiki/Flag_of_Venezuela', NULL, 0, 0, 0, '0', 0, 87, 1, 87),
(88, 'Japan', 'Asia', '088.png', 'https://en.wikipedia.org/wiki/Flag_of_Japan', NULL, 0, 0, 0, '0', 0, 88, 1, 88),
(89, 'India', 'Asia', '089.png', 'https://en.wikipedia.org/wiki/Flag_of_India', NULL, 0, 0, 0, '0', 0, 89, 1, 89),
(90, 'China', 'Asia', '090.png', 'https://en.wikipedia.org/wiki/Flag_of_China', NULL, 0, 0, 0, '0', 0, 90, 1, 90),
(91, 'Saudi Arabia', 'Asia', '091.png', 'https://en.wikipedia.org/wiki/Flag_of_Saudi_Arabia', NULL, 0, 0, 0, '0', 0, 91, 1, 91),
(92, 'Malaysia', 'Asia', '092.png', 'https://en.wikipedia.org/wiki/Flag_of_Malaysia', NULL, 0, 0, 0, '0', 0, 92, 1, 92),
(93, 'South Korea', 'Asia', '093.png', 'https://en.wikipedia.org/wiki/Flag_of_South_Korea', NULL, 0, 0, 0, '0', 0, 93, 1, 93),
(94, 'Palestine', 'Asia', '094.png', 'https://en.wikipedia.org/wiki/Palestinian_flag', NULL, 0, 0, 0, '0', 0, 94, 1, 94),
(95, 'UAE', 'Asia', '095.png', 'https://en.wikipedia.org/wiki/Flag_of_the_United_Arab_Emirates', NULL, 0, 0, 0, '0', 0, 95, 1, 95),
(96, 'Iraq', 'Asia', '096.png', 'https://en.wikipedia.org/wiki/Flag_of_Iraq', NULL, 0, 0, 0, '0', 0, 96, 1, 96),
(97, 'Bahrain', 'Asia', '097.png', 'https://en.wikipedia.org/wiki/Flag_of_Bahrain', NULL, 0, 0, 0, '0', 0, 97, 1, 97),
(98, 'Bangladesh', 'Asia', '098.png', 'https://en.wikipedia.org/wiki/Flag_of_Bangladesh', NULL, 0, 0, 0, '0', 0, 98, 1, 98),
(99, 'Iran', 'Asia', '099.png', 'https://en.wikipedia.org/wiki/Flag_of_Iran', NULL, 0, 0, 0, '0', 0, 99, 1, 99),
(100, 'Jordan', 'Asia', '100.png', 'https://en.wikipedia.org/wiki/Flag_of_Jordan', NULL, 0, 0, 0, '0', 0, 100, 1, 100),
(101, 'Kuwait', 'Asia', '101.png', 'https://en.wikipedia.org/wiki/Flag_of_Kuwait', NULL, 0, 0, 0, '0', 0, 101, 1, 101),
(102, 'Pakistan', 'Asia', '102.png', 'https://en.wikipedia.org/wiki/Flag_of_Pakistan', NULL, 0, 0, 0, '0', 0, 102, 1, 102),
(103, 'Qatar', 'Asia', '103.png', 'https://en.wikipedia.org/wiki/Flag_of_Qatar', NULL, 0, 0, 0, '0', 0, 103, 1, 103),
(104, 'Thailand', 'Asia', '104.png', 'https://en.wikipedia.org/wiki/Flag_of_Thailand', NULL, 0, 0, 0, '0', 0, 104, 1, 104),
(105, 'Yemen', 'Asia', '105.png', 'https://en.wikipedia.org/wiki/Flag_of_Yemen', NULL, 0, 0, 0, '0', 0, 105, 1, 105),
(106, 'Taiwan', 'Asia', '106.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Republic_of_China', NULL, 0, 0, 0, '0', 0, 106, 1, 106),
(107, 'Indonesia', 'Asia', '107.png', 'https://en.wikipedia.org/wiki/Flag_of_Indonesia', NULL, 0, 0, 0, '0', 0, 107, 1, 107),
(108, 'Afghanistan', 'Asia', '108.png', 'https://en.wikipedia.org/wiki/Flag_of_Afghanistan', NULL, 0, 0, 0, '0', 0, 108, 1, 108),
(109, 'Cambodia', 'Asia', '109.png', 'https://en.wikipedia.org/wiki/Flag_of_Cambodia', NULL, 0, 0, 0, '0', 0, 109, 1, 109),
(110, 'Oman', 'Asia', '110.png', 'https://en.wikipedia.org/wiki/Flag_of_Oman', NULL, 0, 0, 0, '0', 0, 110, 1, 110),
(111, 'Syria', 'Asia', '111.png', 'https://en.wikipedia.org/wiki/Flag_of_Syria', NULL, 0, 0, 0, '0', 0, 111, 1, 111),
(112, 'Lebanon', 'Asia', '112.png', 'https://en.wikipedia.org/wiki/Flag_of_Lebanon', NULL, 0, 0, 0, '0', 0, 112, 1, 112),
(113, 'North Korea', 'Asia', '113.png', 'https://en.wikipedia.org/wiki/Flag_of_North_Korea', NULL, 0, 0, 0, '0', 0, 113, 1, 113),
(114, 'Vietnam', 'Asia', '114.png', 'https://en.wikipedia.org/wiki/Flag_of_Vietnam', NULL, 0, 0, 0, '0', 0, 114, 1, 114),
(115, 'Nepal', 'Asia', '115.png', 'https://en.wikipedia.org/wiki/Flag_of_Nepal', NULL, 0, 0, 0, '0', 0, 115, 1, 115),
(116, 'Myanmar', 'Asia', '116.png', 'https://en.wikipedia.org/wiki/Flag_of_Myanmar', NULL, 0, 0, 0, '0', 0, 116, 1, 116),
(117, 'Bhutan', 'Asia', '117.png', 'https://en.wikipedia.org/wiki/Flag_of_Bhutan', NULL, 0, 0, 0, '0', 0, 117, 1, 117),
(118, 'Brunei', 'Asia', '118.png', 'https://en.wikipedia.org/wiki/Flag_of_Brunei', NULL, 0, 0, 0, '0', 0, 118, 1, 118),
(119, 'Kyrgyzstan', 'Asia', '119.png', 'https://en.wikipedia.org/wiki/Flag_of_Kyrgyzstan', NULL, 0, 0, 0, '0', 0, 119, 1, 119),
(120, 'Laos', 'Asia', '120.png', 'https://en.wikipedia.org/wiki/Flag_of_Laos', NULL, 0, 0, 0, '0', 0, 120, 1, 120),
(121, 'Macao', 'Asia', '121.png', 'https://en.wikipedia.org/wiki/Flag_of_Macau', NULL, 0, 0, 0, '0', 0, 121, 1, 121),
(122, 'Maldives', 'Asia', '122.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Maldives', NULL, 0, 0, 0, '0', 0, 122, 1, 122),
(123, 'Mongolia', 'Asia', '123.png', 'https://en.wikipedia.org/wiki/Flag_of_Mongolia', NULL, 0, 0, 0, '0', 0, 123, 1, 123),
(124, 'Philippines', 'Asia', '124.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Philippines', NULL, 0, 0, 0, '0', 0, 124, 1, 124),
(125, 'Singapore', 'Asia', '125.png', 'https://en.wikipedia.org/wiki/Flag_of_Singapore', NULL, 0, 0, 0, '0', 0, 125, 1, 125),
(126, 'Sri Lanka', 'Asia', '126.png', 'https://en.wikipedia.org/wiki/Flag_of_Sri_Lanka', NULL, 0, 0, 0, '0', 0, 126, 1, 126),
(127, 'Tajikistan', 'Asia', '127.png', 'https://en.wikipedia.org/wiki/Flag_of_Tajikistan', NULL, 0, 0, 0, '0', 0, 127, 1, 127),
(128, 'Timor Leste', 'Asia', '128.png', 'https://en.wikipedia.org/wiki/Flag_of_East_Timor', NULL, 0, 0, 0, '0', 0, 128, 1, 128),
(129, 'Turkmenistan', 'Asia', '129.png', 'https://en.wikipedia.org/wiki/Flag_of_Turkmenistan', NULL, 0, 0, 0, '0', 0, 129, 1, 129),
(130, 'Uzbekistan', 'Asia', '130.png', 'https://en.wikipedia.org/wiki/Flag_of_Uzbekistan', NULL, 0, 0, 0, '0', 0, 130, 1, 130),
(131, 'Egypt', 'Africa', '131.png', 'https://en.wikipedia.org/wiki/Flag_of_Egypt', NULL, 0, 0, 0, '0', 0, 131, 1, 131),
(132, 'Algeria', 'Africa', '132.png', 'https://en.wikipedia.org/wiki/Flag_of_Algeria', NULL, 0, 0, 0, '0', 0, 132, 1, 132),
(133, 'Cameroon', 'Africa', '133.png', 'https://en.wikipedia.org/wiki/Flag_of_Cameroon', NULL, 0, 0, 0, '0', 0, 133, 1, 133),
(134, 'Libya', 'Africa', '134.png', 'https://en.wikipedia.org/wiki/Flag_of_Libya', NULL, 0, 0, 0, '0', 0, 134, 1, 134),
(135, 'Nigeria', 'Africa', '135.png', 'https://en.wikipedia.org/wiki/Flag_of_Nigeria', NULL, 0, 0, 0, '0', 0, 135, 1, 135),
(136, 'Tunisia', 'Africa', '136.png', 'https://en.wikipedia.org/wiki/Flag_of_Tunisia', NULL, 0, 0, 0, '0', 0, 136, 1, 136),
(137, 'Morocco', 'Africa', '137.png', 'https://en.wikipedia.org/wiki/Flag_of_Morocco', NULL, 0, 0, 0, '0', 0, 137, 1, 137),
(138, 'Sudan', 'Africa', '138.png', 'https://en.wikipedia.org/wiki/Flag_of_Sudan', NULL, 0, 0, 0, '0', 0, 138, 1, 138),
(139, 'Angola', 'Africa', '139.png', 'https://en.wikipedia.org/wiki/Flag_of_Angola', NULL, 0, 0, 0, '0', 0, 139, 1, 139),
(140, 'Benin', 'Africa', '140.png', 'https://en.wikipedia.org/wiki/Flag_of_Benin', NULL, 0, 0, 0, '0', 0, 140, 1, 140),
(141, 'Botswana', 'Africa', '141.png', 'https://en.wikipedia.org/wiki/Flag_of_Botswana', NULL, 0, 0, 0, '0', 0, 141, 1, 141),
(142, 'Burkina Faso', 'Africa', '142.png', 'https://en.wikipedia.org/wiki/Flag_of_Burkina_Faso', NULL, 0, 0, 0, '0', 0, 142, 1, 142),
(143, 'Burundi', 'Africa', '143.png', 'https://en.wikipedia.org/wiki/Flag_of_Burundi', NULL, 0, 0, 0, '0', 0, 143, 1, 143),
(144, 'Chad', 'Africa', '144.png', 'https://en.wikipedia.org/wiki/Flag_of_Chad', NULL, 0, 0, 0, '0', 0, 144, 1, 144),
(145, 'Rwanda', 'Africa', '145.png', 'https://en.wikipedia.org/wiki/Flag_of_Rwanda', NULL, 0, 0, 0, '0', 0, 145, 1, 145),
(146, 'Niger', 'Africa', '146.png', 'https://en.wikipedia.org/wiki/Flag_of_Niger', NULL, 0, 0, 0, '0', 0, 146, 1, 146),
(147, 'Kenya', 'Africa', '147.png', 'https://en.wikipedia.org/wiki/Flag_of_Kenya', NULL, 0, 0, 0, '0', 0, 147, 1, 147),
(148, 'South Africa', 'Africa', '148.png', 'https://en.wikipedia.org/wiki/Flag_of_South_Africa', NULL, 0, 0, 0, '0', 0, 148, 1, 148),
(149, 'Senegal', 'Africa', '149.png', 'https://en.wikipedia.org/wiki/Flag_of_Senegal', NULL, 0, 0, 0, '0', 0, 149, 1, 149),
(150, 'Ghana', 'Africa', '150.png', 'https://en.wikipedia.org/wiki/Flag_of_Ghana', NULL, 0, 0, 0, '0', 0, 150, 1, 150),
(151, 'Mali', 'Africa', '151.png', 'https://en.wikipedia.org/wiki/Flag_of_Mali', NULL, 0, 0, 0, '0', 0, 151, 1, 151),
(152, 'Togo', 'Africa', '152.png', 'https://en.wikipedia.org/wiki/Flag_of_Togo', NULL, 0, 0, 0, '0', 0, 152, 1, 152),
(153, 'Mauritania', 'Africa', '153.png', 'https://en.wikipedia.org/wiki/Flag_of_Mauritania', NULL, 0, 0, 0, '0', 0, 153, 1, 153),
(154, 'Madagascar', 'Africa', '154.png', 'https://en.wikipedia.org/wiki/Flag_of_Madagascar', NULL, 0, 0, 0, '0', 0, 154, 1, 154),
(155, 'Malawi', 'Africa', '155.png', 'https://en.wikipedia.org/wiki/Flag_of_Malawi', NULL, 0, 0, 0, '0', 0, 155, 1, 155),
(156, 'Zambia', 'Africa', '156.png', 'https://en.wikipedia.org/wiki/Flag_of_Zambia', NULL, 0, 0, 0, '0', 0, 156, 1, 156),
(157, 'Central African Republic', 'Africa', '157.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Central_African_Republic', NULL, 0, 0, 0, '0', 0, 157, 1, 157),
(158, 'Comoros', 'Africa', '158.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Comoros', NULL, 0, 0, 0, '0', 0, 158, 1, 158),
(159, 'Côte d\'Ivoire', 'Africa', '159.png', 'https://en.wikipedia.org/wiki/Flag_of_Ivory_Coast', NULL, 0, 0, 0, '0', 0, 159, 1, 159),
(160, 'Djibouti', 'Africa', '160.png', 'https://en.wikipedia.org/wiki/Flag_of_Djibouti', NULL, 0, 0, 0, '0', 0, 160, 1, 160),
(161, 'Equatorial Guinea', 'Africa', '161.png', 'https://en.wikipedia.org/wiki/Flag_of_Equatorial_Guinea', NULL, 0, 0, 0, '0', 0, 161, 1, 161),
(162, 'Eritrea', 'Africa', '162.png', 'https://en.wikipedia.org/wiki/Flag_of_Eritrea', NULL, 0, 0, 0, '0', 0, 162, 1, 162),
(163, 'Ethiopia', 'Africa', '163.png', 'https://en.wikipedia.org/wiki/Flag_of_Ethiopia ', NULL, 0, 0, 0, '0', 0, 163, 1, 163),
(164, 'Gabon', 'Africa', '164.png', 'https://en.wikipedia.org/wiki/Flag_of_Gabon', NULL, 0, 0, 0, '0', 0, 164, 1, 164),
(165, 'Gambia', 'Africa', '165.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Gambia', NULL, 0, 0, 0, '0', 0, 165, 1, 165),
(166, 'Guinea', 'Africa', '166.png', 'https://en.wikipedia.org/wiki/Flag_of_Guinea', NULL, 0, 0, 0, '0', 0, 166, 1, 166),
(167, 'Guinea Bissau', 'Africa', '167.png', 'https://en.wikipedia.org/wiki/Flag_of_Guinea-Bissau', NULL, 0, 0, 0, '0', 0, 167, 1, 167),
(168, 'Lesotho', 'Africa', '168.png', 'https://en.wikipedia.org/wiki/Flag_of_Lesotho', NULL, 0, 0, 0, '0', 0, 168, 1, 168),
(169, 'Liberia', 'Africa', '169.png', 'https://en.wikipedia.org/wiki/Flag_of_Liberia', NULL, 0, 0, 0, '0', 0, 169, 1, 169),
(170, 'Mauritius', 'Africa', '170.png', 'https://en.wikipedia.org/wiki/Flag_of_Mauritius', NULL, 0, 0, 0, '0', 0, 170, 1, 170),
(171, 'Mozambique', 'Africa', '171.png', 'https://en.wikipedia.org/wiki/Flag_of_Mozambique', NULL, 0, 0, 0, '0', 0, 171, 1, 171),
(172, 'Namibia', 'Africa', '172.png', 'https://en.wikipedia.org/wiki/Flag_of_Namibia', NULL, 0, 0, 0, '0', 0, 172, 1, 172),
(173, 'Republic of the Congo', 'Africa', '173.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Republic_of_the_Congo', NULL, 0, 0, 0, '0', 0, 173, 1, 173),
(174, 'Sao Tomé and Príncipe', 'Africa', '174.png', 'https://en.wikipedia.org/wiki/Flag_of_S%C3%A3o_Tom%C3%A9_and_Pr%C3%ADncipe', NULL, 0, 0, 0, '0', 0, 174, 1, 174),
(175, 'Seychelles', 'Africa', '175.png', 'https://en.wikipedia.org/wiki/Flag_of_Seychelles', NULL, 0, 0, 0, '0', 0, 175, 1, 175),
(176, 'Sierra Leone', 'Africa', '176.png', 'https://en.wikipedia.org/wiki/Flag_of_Sierra_Leone', NULL, 0, 0, 0, '0', 0, 176, 1, 176),
(177, 'Somalia', 'Africa', '177.png', 'https://en.wikipedia.org/wiki/Flag_of_Somalia', NULL, 0, 0, 0, '0', 0, 177, 1, 177),
(178, 'Swaziland', 'Africa', '178.png', 'https://en.wikipedia.org/wiki/Flag_of_Swaziland', NULL, 0, 0, 0, '0', 0, 178, 1, 178),
(179, 'Tanzania', 'Africa', '179.png', 'https://en.wikipedia.org/wiki/Flag_of_Zanzibar', NULL, 0, 0, 0, '0', 0, 179, 1, 179),
(180, 'Uganda', 'Africa', '180.png', 'https://en.wikipedia.org/wiki/Flag_of_Uganda', NULL, 0, 0, 0, '0', 0, 180, 1, 180),
(181, 'Zimbabwe', 'Africa', '181.png', 'https://en.wikipedia.org/wiki/Flag_of_Zimbabwe', NULL, 0, 0, 0, '0', 0, 181, 1, 181),
(182, 'Australia', 'Australia', '182.png', 'https://en.wikipedia.org/wiki/Flag_of_Australia', NULL, 0, 0, 0, '0', 0, 182, 1, 182),
(183, 'Fiji', 'Australia', '183.png', 'https://en.wikipedia.org/wiki/Flag_of_Fiji', NULL, 0, 0, 0, '0', 0, 183, 1, 183),
(184, 'Kiribati', 'Australia', '184.png', 'https://en.wikipedia.org/wiki/Flag_of_Kiribati', NULL, 0, 0, 0, '0', 0, 184, 1, 184),
(185, 'Marshall Islands', 'Australia', '185.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Marshall_Islands', NULL, 0, 0, 0, '0', 0, 185, 1, 185),
(186, 'Micronesia', 'Australia', '186.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Federated_States_of_Micronesia', NULL, 0, 0, 0, '0', 0, 186, 1, 186),
(187, 'Nauru', 'Australia', '187.png', 'https://en.wikipedia.org/wiki/Flag_of_Nauru', NULL, 0, 0, 0, '0', 0, 187, 1, 187),
(188, 'New Zealand', 'Australia', '188.png', 'https://en.wikipedia.org/wiki/Flag_of_New_Zealand', NULL, 0, 0, 0, '0', 0, 188, 1, 188),
(189, 'Palau', 'Australia', '189.png', 'https://en.wikipedia.org/wiki/Flag_of_Palau', NULL, 0, 0, 0, '0', 0, 189, 1, 189),
(190, 'Papua New Guinea', 'Australia', '190.png', 'https://en.wikipedia.org/wiki/Flag_of_Papua_New_Guinea', NULL, 0, 0, 0, '0', 0, 190, 1, 190),
(191, 'Samoa', 'Australia', '191.png', 'https://en.wikipedia.org/wiki/Flag_of_Samoa', NULL, 0, 0, 0, '0', 0, 191, 1, 191),
(192, 'Soloman Islands', 'Australia', '192.png', 'https://en.wikipedia.org/wiki/Flag_of_the_Solomon_Islands', NULL, 0, 0, 0, '0', 0, 192, 1, 192),
(193, 'Tonga', 'Australia', '193.png', 'https://en.wikipedia.org/wiki/Flag_of_Tonga', NULL, 0, 0, 0, '0', 0, 193, 1, 193),
(194, 'Tuvalu', 'Australia', '194.png', 'https://en.wikipedia.org/wiki/Flag_of_Tuvalu', NULL, 0, 0, 0, '0', 0, 194, 1, 194),
(195, 'Vanuatu', 'Australia', '195.png', 'https://en.wikipedia.org/wiki/Flag_of_Vanuatu', NULL, 0, 0, 0, '0', 0, 195, 1, 195);

-- --------------------------------------------------------

--
-- Table structure for table `helps`
--

CREATE TABLE `helps` (
  `_he_id` int(11) NOT NULL DEFAULT '0',
  `he_flag` int(11) DEFAULT NULL,
  `he_facebook` tinyint(4) DEFAULT NULL,
  `he_twitter` tinyint(4) DEFAULT NULL,
  `he_hide` tinyint(4) DEFAULT NULL,
  `he_letter` tinyint(4) DEFAULT NULL,
  `he_solution` tinyint(4) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `us_id` int(11) NOT NULL,
  `us_username` text,
  `us_password` text,
  `us_market_link` text
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`us_id`, `us_username`, `us_password`, `us_market_link`) VALUES
(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'https://play.google.com/store/apps/details?id=com.nileworx.flagsquiz');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `coins`
--
ALTER TABLE `coins`
  ADD PRIMARY KEY (`_coid`);

--
-- Indexes for table `flags`
--
ALTER TABLE `flags`
  ADD PRIMARY KEY (`_flid`);

--
-- Indexes for table `helps`
--
ALTER TABLE `helps`
  ADD PRIMARY KEY (`_he_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`us_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `flags`
--
ALTER TABLE `flags`
  MODIFY `_flid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=196;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
