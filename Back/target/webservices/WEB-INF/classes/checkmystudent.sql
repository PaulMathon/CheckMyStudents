-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le :  ven. 15 fév. 2019 à 11:41
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
-- Base de données :  `checkmystudent`
--

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

CREATE TABLE `classe` (
  `id_classe` int(11) NOT NULL,
  `domaine` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`id_classe`, `domaine`) VALUES
(1, 'ITI'),
(2, 'SMART_CITI'),
(3, 'BTP'),
(4, 'BAA'),
(5, 'ESEA'),
(6, 'IMS');

-- --------------------------------------------------------

--
-- Structure de la table `eleve`
--

CREATE TABLE `eleve` (
  `id_eleve` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `id_classe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `eleve`
--

INSERT INTO `eleve` (`id_eleve`, `nom`, `prenom`, `id_classe`) VALUES
(1, 'pierre', 'coue', 1),
(2, 'lencou', 'xavier', 1),
(3, 'campion', 'albert', 1),
(4, 'bocca', 'etienne', 1),
(5, 'mathon', 'paul', 1),
(6, 'lecoeur', 'thibault', 1),
(7, 'brancaglione', 'gustavo', 1),
(8, 'levoir', 'eleonore', 1),
(9, 'duhem', 'colline', 1),
(10, 'lemarec', 'antoine', 1),
(11, 'giesler', 'flore', 2),
(12, 'thibaut', 'gaelle', 2),
(13, 'gelle', 'guillaume', 2),
(14, 'navarro', 'clement', 2),
(15, 'de villeneuve', 'gregoire', 2),
(16, 'helias', 'helene', 2),
(18, 'mzerd', 'amyne', 2),
(19, 'mouvaux', 'alexandre', 2),
(20, 'lefebuvre', 'maxence', 2),
(21, 'grezaux', 'luc', 2),
(22, 'fievet', 'hortense', 3),
(23, 'lebailly', 'abdelaide', 3),
(24, 'guillot', 'baptiste', 3),
(25, 'soyez', 'margaux', 3),
(26, 'aubry', 'sebastien', 3),
(27, 'desgrousillier', 'yann', 3),
(28, 'galichon', 'gregoire', 3),
(29, 'navellou', 'sarah', 4),
(30, 'dubois', 'quentin', 4),
(31, 'droz', 'capucine', 4),
(32, 'hansske', 'lucas', 4),
(33, 'marcy', 'leo', 4),
(34, 'jolain', 'camille', 4),
(35, 'costeau', 'martin', 4),
(36, 'demarquest', 'clement', 4),
(37, 'richard', 'louis-maxence', 4),
(38, 'desreumaux', 'marius', 4),
(39, 'crossette', 'matthieu', 5),
(40, 'gozard', 'benoit', 5),
(41, 'laine', 'jean-baptiste', 5),
(42, 'requillart', 'louis', 5),
(43, 'saison', 'adrien', 5),
(44, 'jobard', 'cedric', 5),
(45, 'millet', 'charles', 5),
(46, 'quere', 'mellie', 6),
(47, 'lene', 'charles-antoine', 6),
(48, 'de courtilles', 'ines', 6),
(49, 'hamon', 'dovilia', 6),
(50, 'michaud', 'antoine', 6),
(51, 'honneger', 'cedric', 6),
(52, 'paris', 'claire', 6),
(53, 'dhe', 'antoine', 6),
(54, 'brodin', 'aubrée', 6);

-- --------------------------------------------------------

--
-- Structure de la table `enseigne`
--

CREATE TABLE `enseigne` (
  `id_prof` int(11) NOT NULL,
  `id_classe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `enseigne`
--

INSERT INTO `enseigne` (`id_prof`, `id_classe`) VALUES
(1, 1),
(1, 2),
(1, 4),
(1, 6),
(2, 3),
(2, 4),
(3, 1),
(3, 5),
(3, 6),
(4, 1),
(4, 2);

-- --------------------------------------------------------

--
-- Structure de la table `professeur`
--

CREATE TABLE `professeur` (
  `id_prof` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `mdp` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `professeur`
--

INSERT INTO `professeur` (`id_prof`, `login`, `mdp`) VALUES
(1, 'vincent', 'vincent'),
(2, 'pierre', 'pierre'),
(3, 'jose', 'jose'),
(4, 'jules', 'jules');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `classe`
--
ALTER TABLE `classe`
  ADD PRIMARY KEY (`id_classe`);

--
-- Index pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD PRIMARY KEY (`id_eleve`),
  ADD KEY `id_classe` (`id_classe`);

--
-- Index pour la table `enseigne`
--
ALTER TABLE `enseigne`
  ADD PRIMARY KEY (`id_prof`,`id_classe`),
  ADD KEY `id_classe` (`id_classe`);

--
-- Index pour la table `professeur`
--
ALTER TABLE `professeur`
  ADD PRIMARY KEY (`id_prof`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD CONSTRAINT `eleve_ibfk_1` FOREIGN KEY (`id_classe`) REFERENCES `classe` (`id_classe`);

--
-- Contraintes pour la table `enseigne`
--
ALTER TABLE `enseigne`
  ADD CONSTRAINT `enseigne_ibfk_1` FOREIGN KEY (`id_prof`) REFERENCES `professeur` (`id_prof`),
  ADD CONSTRAINT `enseigne_ibfk_2` FOREIGN KEY (`id_classe`) REFERENCES `classe` (`id_classe`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
