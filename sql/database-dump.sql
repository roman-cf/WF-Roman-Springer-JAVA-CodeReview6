-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 09. Jan 2020 um 22:07
-- Server-Version: 10.4.10-MariaDB
-- PHP-Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `cr6`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `class`
--

CREATE TABLE `class` (
  `id` int(11) NOT NULL,
  `name` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `class`
--

INSERT INTO `class` (`id`, `name`) VALUES
(1, 'Klasse 1a'),
(2, 'Klasse 1b'),
(3, 'Klasse 2a'),
(4, 'Klasse 2b'),
(5, 'Klasse 3a'),
(6, 'Klasse 3b');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `surname` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `student`
--

INSERT INTO `student` (`id`, `name`, `surname`, `email`, `class_id`) VALUES
(1, 'Thomas', 'Römer', 't.r@email.welt', 2),
(2, 'Peter', 'Rauner', 'p.rauner@welt.all', 1),
(3, 'Cora', 'Seger', 'cori@fun.de', 6),
(4, 'Ulrike ', 'Kappel', 'UlrikeKappel@cuvox.de', 1),
(5, 'Florian ', 'Neustadt', 'FlorianNeustadt@einrot.com', 3),
(6, 'Marie', 'Bürger', 'MarieBuerger@einrot.com', 3),
(7, 'Andrea', 'Köhler', 'AndreaKoehler@cuvox.de', 3),
(8, 'Doreen', 'Goldschmidt', 'DoreenGoldschmidt@cuvox.de', 1),
(9, 'Steffen', 'Thalberg', 's.thalberg@thalberg.com', 4),
(10, 'Linn', 'Holmgren', 'LinnHolmgren@einrot.com', 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `teacher`
--

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `surname` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `teacher`
--

INSERT INTO `teacher` (`id`, `name`, `surname`, `email`) VALUES
(1, 'Thorsten ', 'Just', 'pluto@welt.all'),
(2, 'Marion', 'Welsh', 'mw@shool.com'),
(3, 'Sascha', 'Lustig', 'lustig@yahoo.de'),
(4, 'Marvin ', 'Baumann', 'nighty@berlin.de');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `teaching`
--

CREATE TABLE `teaching` (
  `id` int(11) NOT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `teaching`
--

INSERT INTO `teaching` (`id`, `teacher_id`, `class_id`) VALUES
(1, 1, 1),
(2, 1, 3),
(3, 2, 6),
(4, 2, 5),
(5, 4, 3),
(6, 4, 4),
(7, 3, 2),
(8, 3, 4),
(9, 1, 6),
(10, 4, 6);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`),
  ADD KEY `class_id` (`class_id`);

--
-- Indizes für die Tabelle `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `teaching`
--
ALTER TABLE `teaching`
  ADD PRIMARY KEY (`id`),
  ADD KEY `teacher_id` (`teacher_id`),
  ADD KEY `class_id` (`class_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `class`
--
ALTER TABLE `class`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT für Tabelle `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT für Tabelle `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `teaching`
--
ALTER TABLE `teaching`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`);

--
-- Constraints der Tabelle `teaching`
--
ALTER TABLE `teaching`
  ADD CONSTRAINT `teaching_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  ADD CONSTRAINT `teaching_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
