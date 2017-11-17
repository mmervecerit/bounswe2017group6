(function()
{
    angular
        .module("interestHub")
        .controller("ProfileCtrl", ProfileCtrl);
    
    function ProfileCtrl($scope,  $rootScope, $routeParams, $location, UserService)
    {
    	function init(){
    		UserService.getUser($routeParams.id)
    			.then(handleSuccess, handleError);
    	}

        init();
		console.log($scope.user);      	
      	function handleSuccess(response) {
            $scope.user = response.data;
        	
        }

        function handleError(error) {
            $scope.error = error;
        }
 
	   }

     
})();
