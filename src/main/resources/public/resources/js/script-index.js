// Chargement du module "Index"
var app = angular.module("Index", []);

// Création du controller "MenuCtrl"
app.controller("MenuCtrl", function($scope) {

	$scope.connecte = true;

	// On va se connecter sur la route permettant de récupèrer le profil de l'utilisateur
	$http.get('/utilisateur/particulier/profil').success(
		function(donnees) {
			// Si on reçoit les données, c'est que l'utilisateur est connecté, on n'affiche pas les boutons de connexion et d'inscription... par contre on peut se déconnecter
			$scope.connecte = false;
		}
	);
	
});