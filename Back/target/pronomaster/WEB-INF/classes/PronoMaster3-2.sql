-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le :  Dim 09 déc. 2018 à 23:44
-- Version du serveur :  10.1.36-MariaDB
-- Version de PHP :  7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `pronomaster3`
--

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE `equipe` (
  `id_equipe` int(11) NOT NULL,
  `nom_equipe` varchar(100) NOT NULL,
  `logo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `equipe`
--

INSERT INTO `equipe` (`id_equipe`, `nom_equipe`, `logo`) VALUES
(1, 'France', NULL),
(2, 'Allemagne', NULL),
(3, 'Belgique', NULL),
(5, 'Espagne', NULL),
(6, 'Italie', NULL),
(7, 'Angleterre', NULL),
(8, 'Croatie', NULL),
(9, 'Tunisie', NULL),
(10, 'Maroc', NULL),
(11, 'Bresil', NULL),
(12, 'Perou', NULL),
(13, 'Russie', NULL),
(14, 'Inde', NULL),
(15, 'Nouvelle-Zelande', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `match`
--

CREATE TABLE `match` (
  `id_match` int(11) NOT NULL,
  `equipe_in` int(11) NOT NULL,
  `equipe_out` int(11) NOT NULL,
  `but_in` int(11) DEFAULT NULL,
  `but_out` int(11) DEFAULT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `match`
--

INSERT INTO `match` (`id_match`, `equipe_in`, `equipe_out`, `but_in`, `but_out`, `date`) VALUES
(1, 2, 3, NULL, NULL, '2020-11-22'),
(4, 5, 2, NULL, NULL, '2020-12-07'),
(5, 3, 1, 1, 2, '2018-11-18'),
(6, 1, 2, 2, 1, '2018-11-08'),
(7, 1, 5, 2, 2, '2018-11-13'),
(9, 11, 1, NULL, NULL, '2018-12-29'),
(11, 15, 13, NULL, NULL, '2019-02-20'),
(12, 7, 6, NULL, NULL, '2019-08-17'),
(13, 8, 14, NULL, NULL, '2019-04-06'),
(14, 8, 9, 2, 1, '2018-11-24'),
(15, 13, 2, NULL, NULL, '2018-12-01'),
(16, 9, 8, NULL, NULL, '2018-11-24'),
(17, 11, 8, 0, 2, '2018-11-16'),
(18, 7, 12, 2, 0, '2018-10-05'),
(19, 15, 5, 2, 1, '2018-11-12'),
(20, 6, 14, 3, 2, '2018-10-07'),
(21, 13, 5, 1, 1, '2018-10-29');

-- --------------------------------------------------------

--
-- Structure de la table `parie`
--

CREATE TABLE `parie` (
  `id_parie` int(11) NOT NULL,
  `id_match` int(11) NOT NULL,
  `but_in` int(11) NOT NULL,
  `but_out` int(11) NOT NULL,
  `id_utilisateur` varchar(100) NOT NULL,
  `resultat` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `parie`
--

INSERT INTO `parie` (`id_parie`, `id_match`, `but_in`, `but_out`, `id_utilisateur`, `resultat`) VALUES
(1, 6, 2, 1, 'Lucie', 0),
(2, 5, 3, 1, 'Lucie', 1),
(3, 7, 3, 4, 'Lucie', 0),
(4, 14, 1, 0, 'Lucie', 1),
(5, 9, 1, 2, 'Lucie', NULL),
(6, 11, 2, 4, 'Lucie', NULL),
(7, 12, 1, 0, 'Lucie', NULL),
(8, 17, 0, 2, 'Lucie', 1),
(9, 18, 1, 3, 'Lucie', 0),
(10, 19, 2, 1, 'Lucie', 1),
(11, 20, 0, 0, 'Lucie', 0),
(12, 21, 1, 1, 'Lucie', 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `nom_utilisateur` varchar(100) NOT NULL,
  `mot_de_passe` varchar(100) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `role` int(11) NOT NULL DEFAULT '0',
  `score` int(11) NOT NULL,
  `photo_profil` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`nom_utilisateur`, `mot_de_passe`, `mail`, `role`, `score`, `photo_profil`) VALUES
('Basile', '$argon2i$v=19$m=65536,t=2,p=1$vrpB4cTWaTmcoZTy4Rr8xw$QsB9DtgZjdQyOG/uqbquOayu72/kVnNNR8brmz101JI', 'basile@gmail.com', 0, 6, NULL),
('César', '$argon2i$v=19$m=65536,t=2,p=1$sybjHJSgStmLhhP+sMFNOA$hottcU94Ph1lJ6nNsoIJsOHwn4nFWNBb5hLfTj13y6Q', 'cesar@gmail.com', 0, 0, NULL),
('Coline', '$argon2i$v=19$m=65536,t=2,p=1$wedoHW23fhC8rzivN+o3jw$DND9N5TfrsdRzo9AlGJojd6DD4eK6ztjSSnGj++jGy8', 'coline@gmail.com', 0, 21, NULL),
('Emma', '$argon2i$v=19$m=65536,t=2,p=1$vl0ETbdwnZ1FSYcX0Zr3IA$icEH71wTeBNyBKTe6CSwnUDorqa6jTMQxk8XXPfHjVE', 'emma', 0, 8, NULL),
('Gus', '$argon2i$v=19$m=65536,t=2,p=1$PdglfHnFC8hu0ND23lZA5Q$9zPcxOWHV7chbolAfGz2B1ynYknKgJ1/1c2lpSp2dus', 'gus@gmail.com', 0, 5, NULL),
('Hugo', '$argon2i$v=19$m=65536,t=2,p=1$rGKwv8gUesEpxoUxJQaKiQ$HUX/k6vebCqiFH5j8yCbcdMk9NG9m/9YDnOQ/1/zx/o', 'hugo', 0, 14, NULL),
('Jacques', '$argon2i$v=19$m=65536,t=2,p=1$74zuk/d/adINI2Pw5T/Kvw$sXm1E7W56rIHjjjkd/zDgO62QbA0VoMHDKlK5qrl4Vo', 'jacques', 0, 10, NULL),
('James', '$argon2i$v=19$m=65536,t=2,p=1$W6NyfQY+rTbe7JZE+ELNCg$HW1lYdTUJG4gE1x1HNbwQ4tL2adrnzPjvYu9czxLgu0', 'james@gmail.com', 0, 0, NULL),
('Jules', '$argon2i$v=19$m=65536,t=2,p=1$kafnpGOePP9LbsWidr5XLQ$mGwRybt3vOmOUhAJgVqISa2oiDrInwzM8/TjwUzZbQU', 'jules', 1, 0, NULL),
('Kanesha', '$argon2i$v=19$m=65536,t=2,p=1$RS+UOeDqVeMYssQwJg5zeA$aJY/q/HRZqAVSZUG7jGVliMOQnPN9OYIuQvbdbQSLIM', 'kanesha', 1, 0, NULL),
('Karl', '$argon2i$v=19$m=65536,t=2,p=1$IJKU+drfk/HUxsXtyb+O8w$g9f2aHJNuAyIiJcDRsEV7lzNmA27uWc+k2z8M3BGnrU', 'karl@gmail.com', 0, 15, NULL),
('Léo', '$argon2i$v=19$m=65536,t=2,p=1$9Ed3K7RRJGod68iquFFV4A$oi4G0vl6U4tjl7fFwLqLfrOkh0kwkaJp5SKWZ389dCw', 'leo@gmail.com', 0, 0, NULL),
('Lucie', '$argon2i$v=19$m=65536,t=2,p=1$nfb2xsAsbk31CIoYq1/mHQ$fJ5hl4Bsd1Rm4dLi8qKkAJyioQbYYNg7Q2jg+A8Llxw', 'lucie', 0, 20, '/Users/paulmathon/Documents/HEI/HEI%204/Projet/pronomaster/pronomaster/data/images/photo-profil/Lucie.jpg'),
('Marie', '$argon2i$v=19$m=65536,t=2,p=1$tlkH7uVSmYCKkV0baW/PDQ$5HFU9kHMk5JFF0i680KFWonc9lUFXAAqmups+YAD+Ow', 'marie', 0, 12, NULL),
('Maxence', '$argon2i$v=19$m=65536,t=2,p=1$mpEUmOOiED+y30DyvUbx5g$M8Zd5WVnY1cK1t+BBD/aBn9mwwfQFiHrXpQJ35ld59U', 'maxence', 2, 0, NULL),
('Paul', '$argon2i$v=19$m=65536,t=2,p=1$zTN4ebFCgI5zj8plTzst7w$UCOrTDEY6zImfgV/Z0LatxtFliqWrQKuFd8o2N1+ZFc', 'paul', 0, 0, NULL),
('Philippe', '$argon2i$v=19$m=65536,t=2,p=1$fI492MG/KV8Sby7F9gL/Pg$PiqhBjHjFbRnem700jJyWSNgUQ51UIXjp2PiLlX5uHk', 'philippe', 0, 3, NULL),
('Pierre', '$argon2i$v=19$m=65536,t=2,p=1$y6oIY3W25VqZCBBhgJc8TQ$gJzPrffPLeaEKhZgz0n2yPDRztHsDbLJVl7DvOaAMTE', 'pierre', 1, 0, NULL),
('Thomas', '$argon2i$v=19$m=65536,t=2,p=1$RNULhF3na/5pSlrEP6uPyw$USXhGyAltYnPvi02x6q/C/rXQD17oXZIq27ejcz4G4I', 'thomas', 0, 7, NULL),
('Titi', '$argon2i$v=19$m=65536,t=2,p=1$qu+u4/Ei9KEVq5Zks0MIJA$2HvTXUCuj/o1OwMkf4m9sighJoQwn7KfBAkwshW0Hdk', 'titi@gmail.com', 0, 9, NULL),
('Victor', '$argon2i$v=19$m=65536,t=2,p=1$WIzRYAQSFvEoJAZxPyNnjA$bt+LspMp0C3tgD3ZUH4x3SKBFn9Ed+Qu2w3/MvHs/EE', 'victor@gmail.com', 0, 0, NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD PRIMARY KEY (`id_equipe`);

--
-- Index pour la table `match`
--
ALTER TABLE `match`
  ADD PRIMARY KEY (`id_match`),
  ADD KEY `match_equipe_fk` (`equipe_in`),
  ADD KEY `match_equipe_fk_1` (`equipe_out`);

--
-- Index pour la table `parie`
--
ALTER TABLE `parie`
  ADD PRIMARY KEY (`id_parie`),
  ADD KEY `parie_match_fk` (`id_match`),
  ADD KEY `parie_utilisateur_fk` (`id_utilisateur`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`nom_utilisateur`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `equipe`
--
ALTER TABLE `equipe`
  MODIFY `id_equipe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `match`
--
ALTER TABLE `match`
  MODIFY `id_match` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `parie`
--
ALTER TABLE `parie`
  MODIFY `id_parie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `match`
--
ALTER TABLE `match`
  ADD CONSTRAINT `match_equipe_fk` FOREIGN KEY (`equipe_in`) REFERENCES `equipe` (`id_equipe`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `match_equipe_fk_1` FOREIGN KEY (`equipe_out`) REFERENCES `equipe` (`id_equipe`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `parie`
--
ALTER TABLE `parie`
  ADD CONSTRAINT `foreign key` FOREIGN KEY (`id_match`) REFERENCES `match` (`id_match`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `foreign key2` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`nom_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;