## 1. Introduction
L'application est conçue pour gérer des créneaux d'activités, permettant aux utilisateurs de se connecter et de visualiser, modifier ou supprimer des créneaux. Elle utilise JavaFX pour l'interface utilisateur, offrant une expérience graphique agréable.

## 2. Prérequis Avant Démarrage
Avant de démarrer l'application, il est essentiel de préparer la base de données. Voici les étapes à suivre :

1. *Création de la Base de Données* :
   - Un script SQL pour la création de la base de données de camping se trouve à la racine du projet.
   - Exécutez ce script dans votre système de gestion de base de données (SGBD) pour créer les tables nécessaires.

2. *Modification des Informations de Connexion* :
   - Ouvrez le fichier ConnexionBDD.java dans votre projet.
   - Modifiez les informations de connexion (URL, nom d'utilisateur, mot de passe) pour qu'elles correspondent à votre configuration de base de données.

## 3. Fonctionnalités Principales
- *Connexion Utilisateur*: 
  - L'utilisateur entre un identifiant et un mot de passe via des champs de texte. 
  - Un bouton de connexion permet de soumettre ces informations.
  - *Identifiant par défaut* : admin
  - *Mot de passe par défaut* : admin123

- *Dashboard*:
  - *Accueil*: 
    - Affiche un tableau de bord avec les créneaux de la semaine, incluant des colonnes pour la date, l'heure, la durée, le nombre de places, l'animation, et le lieu.
  - *Gestion des Créneaux*: 
    - Permet à l'utilisateur de visualiser, actualiser, modifier et supprimer des créneaux via une liste et des boutons d'action.
    - Des champs de texte permettent de saisir de nouvelles informations sur les créneaux, telles que l'heure, la date, la durée, le nombre de places, et le libellé de l'animation.
  - *Gestion des Lieux*:
    - Permet à l'utilisateur d'ajouter, modifier et supprimer des lieux.
    - Affiche une liste des lieux disponibles avec des options pour la gestion des informations associées.
  - *Gestion des Animateurs*:
    - Permet à l'utilisateur d'ajouter, modifier et supprimer des animateurs.
    - Affiche une liste des animateurs, avec la possibilité de consulter et de gérer leurs informations.
  - *Profil*:
    - Permet à l'utilisateur de gérer son profil, y compris la modification de son mot de passe.
    - Inclut des options pour afficher et mettre à jour les informations de l'utilisateur connecté.

## 4. Consignes d'Utilisation
- *Format de Date*: 
  - Les utilisateurs doivent entrer la date au format dd/MM/yyyy (jour/mois/année).
  
- *Champs de Saisie*:
  - Les champs doivent être remplis avec des informations valides avant de soumettre les données.
  - Pour la durée, le format attendu est en minutes.
  - Le champ "Nombre de places" doit contenir un nombre entier.

- *Navigation*:
  - L'utilisateur peut naviguer entre les différents onglets du dashboard pour accéder aux informations et fonctionnalités nécessaires.
  - Les boutons d'action permettent d'exécuter des fonctions spécifiques, telles que l'actualisation, la modification ou la suppression de créneaux.

## 5. Gestion des Données
- *Suppression de Créneaux et Lieux*:
  - La méthode delete() permet de supprimer un créneau ainsi que son lieu associé de la base de données. 
  - La méthode utilise des requêtes SQL préparées pour éviter les injections SQL et garantir la sécurité des opérations.
  - En cas d'erreur lors de l'exécution des requêtes, une alerte est affichée avec un message d'erreur, informant l'utilisateur du problème survenu.

## 6. Interaction avec l'Interface Utilisateur
- *Contrôleur de Connexion*:
  - Le contrôleur HelloController gère les événements de l'interface utilisateur liés aux champs de connexion (identifiant et mot de passe).
  - En utilisant JavaFX, les interactions utilisateur déclenchent des actions définies, comme la validation des informations de connexion.

## 7. Gestion des Animations et Lieux
- *Ajout d'Animations*:
  - La méthode buttonAjouterAnimation() permet d'ajouter une nouvelle animation à l'application. 
  - Si l'enregistrement de l'animation est réussi, une alerte de succès est affichée, et la liste des animations est mise à jour. En cas d'échec, une alerte d'erreur informe l'utilisateur.

- *Modification de Lieux*:
  - La méthode buttonModifierLieu() permet à l'utilisateur de modifier les informations d'un lieu sélectionné. 
  - Si aucun élément n'est sélectionné, une alerte d'avertissement est présentée.

- *Annulation des Modifications*:
  - La méthode buttonAnnulerLieu() offre la possibilité d'annuler les modifications en cours. 
  - Une confirmation est demandée à l'utilisateur avant de réinitialiser les champs de saisie.

- *Suppression de Lieux*:
  - La méthode buttonSupprimerLieu() permet de supprimer un lieu sélectionné. 
  - Avant la suppression, une boîte de dialogue de confirmation est affichée pour éviter des suppressions accidentelles. 
  - En cas de succès, une alerte de confirmation est affichée ; sinon, une alerte d'erreur est présentée.

## 8. Actualisation des Listes
- *Actualisation des Animateurs*:
  - La méthode actualisationListeListeAnimateur() met à jour la liste des animateurs affichée dans l'interface. 
  - Si aucun animateur n'est trouvé, un message est affiché dans la console.

- *Mise à Jour des Animateurs*:
  - La méthode buttonActualiserAnimateur() appelle la méthode d'actualisation pour mettre à jour l'affichage des animateurs dans l'interface utilisateur.

## 9. Gestion des Créneaux
- *Modification de Créneaux*:
  - La méthode clicBoutonModifierCreneau() permet à l'utilisateur de modifier un créneau sélectionné. 
  - Elle pré-remplit les champs de texte avec les informations du créneau et sélectionne l'animation et le lieu associés. 
  - Si une animation ou un lieu n'est pas trouvé, une alerte d'avertissement est affichée.

- *Suppression de Créneaux*:
  - La méthode clicBoutonSupprimerCreneau() permet à l'utilisateur de supprimer un créneau après confirmation. 
  - En cas de succès, une alerte de succès est affichée. Sinon, une alerte d'erreur informe l'utilisateur.

- *Enregistrement de Créneaux*:
  - La méthode clicBoutonEnregistrerCreneau() gère l'enregistrement d'un créneau, que ce soit pour une nouvelle entrée ou une modification d'un créneau existant.
  - Elle effectue une validation des champs pour s'assurer que toutes les informations nécessaires sont fournies.
  - Si l'enregistrement réussit, une alerte de succès est affichée et la liste des créneaux est mise à jour. Si l'enregistrement échoue, une alerte d'erreur est présentée.

## 10. Gestion des Animateurs
- *Enregistrement d'Animateurs*:
  - La méthode saveAnimateur() permet d'ajouter ou de mettre à jour un animateur dans la base de données. 
  - Si l'animateur n'existe pas, il est créé avec les informations fournies. Sinon, les informations existantes sont mises à jour.
  - En cas d'erreur lors de l'opération, une alerte d'erreur est affichée, et la méthode retourne false pour indiquer un échec.

- *Suppression d'Animateurs*:
  - La méthode delete() permet de supprimer un animateur de la base de données. 
  - Elle supprime d'abord toutes les associations de l'animateur avec d'autres entités, puis l'animateur lui-même. 
  - En cas d'échec, une alerte d'erreur est affichée, et la méthode retourne false.

## 11. Méthodes Utilitaires
- *toString()*:
  - La méthode toString() est redéfinie pour fournir une représentation lisible d'un animateur, affichant son nom, prénom, email et numéro de téléphone.

- *equals() et hashCode()*:
  - Les méthodes equals() et hashCode() sont redéfinies pour permettre des comparaisons d'égalité et une utilisation correcte des collections.
