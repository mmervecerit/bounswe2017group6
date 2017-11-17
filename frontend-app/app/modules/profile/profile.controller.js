(function()
{
    angular
        .module("interestHub")
        .controller("ProfileCtrl", ProfileCtrl);
    
    function ProfileCtrl($scope,  $rootScope, $routeParams, $location, UserService)
    {
        $scope.removeInterest = removeInterest;
        $scope.addInterest = addInterest;
        $scope.editUser = editUser;
    	function init(){

    		UserService.getUser($routeParams.id)
    			.then(handleSuccess, handleError);
    	}

        init();
		$scope.user = new Object();
        $scope.user.firstname="Gökçe";
        $scope.user.lastname="Uludoğan";
        $scope.user.gender="Female";
        $scope.user.birthdate="1995-06-28";
        $scope.interests = ["data science","robotics","artificial intelligence","science fiction", "travel","cycling"];
        console.log($scope.user);     
        
      	function handleSuccess(response) {
            $scope.user = response.data;
        	
        }

        function handleError(error) {
            $scope.error = error;
        }

        function removeInterest($index){
            $scope.interests.splice($index,1);
        }
        function addInterest(interest){
            $scope.interests.push(interest);
            $scope.interest = "";

        }
        function editUser(user){
            UserService.updateUser(user)
                .then(handleSuccess,handleError);
        }
 
	   }

     
})();
