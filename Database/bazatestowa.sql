-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 11 Kwi 2019, 13:59
-- Wersja serwera: 10.1.37-MariaDB
-- Wersja PHP: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `bazatestowa`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(127),
(127);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `scores`
--

CREATE TABLE `scores` (
  `id` int(11) NOT NULL,
  `level` enum('Beginner','Average','Expert','Master') DEFAULT NULL,
  `pack` varchar(255) DEFAULT NULL,
  `score` time DEFAULT NULL,
  `userid` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `scores`
--

INSERT INTO `scores` (`id`, `level`, `pack`, `score`, `userid`) VALUES
(6, 'Beginner', 'Hackathon', '00:00:37', 1),
(7, 'Beginner', 'Hackathon', '00:00:51', 1),
(8, 'Beginner', 'Hackathon', '00:00:27', 1),
(9, 'Average', 'Hackathon', '00:00:42', 5),
(10, 'Beginner', 'Hackathon', '00:00:57', 5),
(11, 'Average', 'Hackathon', '00:00:34', 1),
(30, 'Beginner', 'Hackathon', '00:00:37', 1),
(31, 'Beginner', 'NationalFlags', '00:00:25', 1),
(35, 'Beginner', 'NationalFlags', '00:00:28', 34),
(36, 'Beginner', 'NationalFlags', '00:00:29', 34),
(37, 'Expert', 'NationalFlags', '00:02:14', 34),
(38, 'Master', 'NationalFlags', '00:03:23', 34),
(39, 'Average', 'NationalFlags', '00:01:12', 34),
(40, 'Beginner', 'NationalFlags', '00:00:25', 34),
(41, 'Beginner', 'NationalFlags', '00:00:27', 34),
(42, 'Beginner', 'NationalFlags', '00:00:28', 34),
(45, 'Beginner', 'NationalFlags', '00:00:21', 34),
(46, 'Beginner', 'NationalFlags', '00:00:24', 34),
(47, 'Beginner', 'NationalFlags', '00:00:22', 34),
(126, 'Beginner', 'NationalFlags', '00:00:27', 34),
(125, 'Beginner', 'NationalFlags', '00:00:19', 34),
(124, 'Beginner', 'NationalFlags', '00:00:28', 34),
(123, 'Beginner', 'NationalFlags', '00:00:19', 34),
(92, 'Beginner', 'Hackathon', '00:00:33', 1),
(90, 'Beginner', 'Hackathon', '00:00:33', 1),
(88, 'Beginner', 'Hackathon', '00:00:33', 1),
(86, 'Beginner', 'Hackathon', '00:00:33', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `validation_key` varchar(255) DEFAULT NULL,
  `activated` tinyint(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`id`, `name`, `nickname`, `login`, `validation_key`, `activated`) VALUES
(1, 'Jack', 'Jackie', 'Lebowski', '$2a$10$RwBlDdyxBTu2Uz/WP0bL8.idfij4gNg7rhHJFkoY7.9hbzLukFcFW', 1),
(5, 'John', 'Wrapperi', 'Stone', '$2a$10$6xRJdvDbuIrQqqYHxjPwy.9aTWF1tc9Fh8TNfRHlwHJf6x4kEpiOG', 1),
(43, 'Mia', 'Miami', 'Price', '$2a$10$is92OHz/6QxPgE.7SYel.eANb0NrWkZiFnF9N7sYV04W48TD1cRoO', 1),
(34, 'Jennifer', 'JennyColl', 'Jenny12', '$2a$10$yNLqynEi60GsvQ5xP4UuF.8Z8P2vzuDZ98QiGUoIOfPlolGKnoywC', 1),
(3, 'Tester', 'ValidatioNTester', 'Tester', '$2a$10$ohLd3kb6y3oiM2feNoZPEOE5UNVA4gQV7rfE26.2eZO13V1YPG3hO', 0),
(2, 'ValidationTester', 'NicknameShowingInHighscoreList', 'ValidationTester', '$2a$10$4Lck5Juh1Eh0bpH7Pq2WS.WlsFY2CYla/tPuS4yM.59Btlj1z.N6C', 0);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `scores`
--
ALTER TABLE `scores`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
