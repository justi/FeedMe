// Chargement du module "Profil"
var app = angular.module("Profil",  ['ngAnimate','ngMaterial', 'ngFileUpload', 'ngMessages', 'appFilters']);



app.controller("LogoutCtrl", function($scope, $http, $window) {
    
	// Fonction permettant une déconnexion :
	$scope.logout = function () {
		$http.get('/logout').success(
			function(donnees) {
				$scope.authenticated = false;
				$window.location.href = "/";
			}
		);
	};
});

// Création du controller "ProfilCtrl"
app.controller("ProfilCtrl", function($scope, $http) {
	
	//affichage des input d'edition du profil
	$scope.editBio=false;
	
	$scope.editPic=false;
	$scope.picEtdited=false;
	 
    $scope.editAdr =false;
	// On va se connecter sur la route permettant de récupèrer le profil de l'utilisateur
	$http.get('/utilisateur/particulier/profil').success(
		function(donnees) {
			// Quand on reçoit les données, on les envoie à la vue (stockage dans la variable profil)
			$scope.profil = donnees;
		}
	);
	
	// On va rechercher toutes les pays en se connectant à la route consacrée
	$http.get('/settings/pays').success(
			function(donnees) {
				$scope.count = donnees;
				$scope.saveCountry = $scope.count;
			}
	);

	
	  $scope.hoverInPic = function(){
	        this.hoverEditPic = true;
	     };

	     
	    $scope.hoverOutPic = function(){
	        this.hoverEditPic = false;
	    };
	    
	    $scope.hoverInBio = function(){
	        this.hoverEditBio = true;
	     };

	     
	    $scope.hoverOutBio = function(){
	        this.hoverEditBio = false;
	    };
	    
	    $scope.hoverInAdr = function(){
	        this.hoverEditAdr = true;
	     };

	     
	    $scope.hoverOutAdr = function(){
	        this.hoverEditAdr = false;
	    };
	    
	    //on affiche l'édition de la bio
	    $scope.setEditBio = function(){

	        $scope.editBio = true 
	    };
	    
	    //on affiche l'édition de l'addresse
	    $scope.setEditAdr = function(){
	        $scope.editAdr = true ;
	    	   $scope.homeAction();
	    
	        
	    };
	    
	    //on affiche l'édition de la photo
	    $scope.setEditPic = function(){
	    	$scope.editPic=true;
		    
	    
	        
	    };
	    
	    
			
	    // decoupe l'adresse si exsistante
	    $scope.homeAction = function() {
	    	
			if($scope.editAdr){

				var rue = $scope.profil.adresse.voie;
				var num = rue.split(" ")[0];
				
				$scope.numero = parseInt(num);
				$scope.rue = rue.substring(num.length+1,rue.length);
				$scope.ville = $scope.profil.adresse.ville.nom;
				$scope.cp = $scope.profil.adresse.ville.cp;
				$scope.count= {0: $scope.profil.adresse.ville.pays};

			}else{
				$scope.numero = '';
				$scope.rue = '';
				$scope.ville ='';
				$scope.cp = '';
				$scope.count = $scope.saveCountry;
			}


		};
		
		// propose tout les pays
		$scope.allCountry = function(){
			
			$scope.count = $scope.saveCountry;
		};
		
		//on surveille 
		$scope.$watch('photo', function () {
		
			if ($scope.photo) {
		        $scope.upload($scope.photo);
		        	        
	
					$scope.profil.image = $scope.photo;
					$scope.picEdited=true;
			
				
			}
	    });
		
		$scope.historique = new Array();
		
		//chargment de l'image
		$scope.upload = function (files) {
	        if (files) {
	            for (var i = 0; i < files.length; i++) {
	              var file = files[i];

	              if (!file.$error) {
	                Upload.upload({
	                    url: '/image',
	                    data: {file: file}
	                }).success(function (data, status, headers, config) {
	                	$scope.historique.push(data);
	                });
	              }
	            }
	        }
	    };
		
		
		 $scope.submitEdition = function(){
			 if ($scope.ProfilForm.$valid){
				 
				 if(!$scope.editAdr){
					 $scope.homeAction();
				 }
				 
				// On créé on objet pays
					var pays = {
							id : $scope.pays,
					};

					// On créé un objet ville
					var ville = {
							nom : $scope.ville,
							cp : $scope.cp,
							pays : pays.id,
					};
					//build de l'adresse
					
					if(angular.isUndefined($scope.complement)){
						$scope.complement=' ';
					}
					
					
					var adresse = {
							voie : $scope.numero + " " + $scope.rue + " " + $scope.complement,
							ville : ville,
					};
					console.log(adresse);
					$scope.profil.adresse = adresse;
					//on fabrique les données  envoyer
					var donnees = {
							idUtilisateur : $scope.profil.idUtilisateur,
							adresse : adresse,
							description : $scope.profil.description,
							image : $scope.profil.image
							
							
					};
			
				 
				 console.log('valid');
					
					// On envoie les données
		            $http({
		        		method: 'PUT',
		        		url: '/utilisateur/particulier/profil',
		        		contentType: "application/json",
		        		data: donnees
		     		}).success(function(response, status, headers, config){
		     			//$mdToast.show($mdToast.simple().content('Votre offre a bien été enregistrée.').hideDelay(2000));
		     			//$window.location.href = "/profil.html";
		     			$scope.editBio=false;
		     			$scope.editPic=false;
		     		    $scope.editAdr =false;
		     		}).error(function(err, status, headers, config){
		      			//$mdToast.show($mdToast.simple().content('Notre service est indisponible pour le moment, veuillez réessayer plus tard.').hideDelay(2000));
		     			$scope.editBio=false;
		     			$scope.editPic=false;
		     		    $scope.editAdr =false;
		     		});
					
				 
				 
				 
			 }
		 };
	    
	
});