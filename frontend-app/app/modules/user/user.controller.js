
(function()
{
    angular
        .module("interestHub")
        .controller("UserCtrl", UserCtrl);
    
    function UserCtrl($scope,  $rootScope, $location, UserService)
    {

        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};

        function init() {
            UserService
                .getAllUsers()
                .then(handleSuccess, handleError);
        }
        init();

        function remove(user)
        {
            UserService
                .deleteUser(user._id)
                .then(handleSuccess, handleError);
        }
        
        function update(user)
        {
       

            UserService
                .updateUser(user._id, user)
                .then(handleSuccess, handleError);
        }
        
        function add(user)
        {
            UserService
                .createUser(user)
                .then(handleSuccess, handleError);
            

            
        }      

        function handleSuccess(response) {
            $scope.users = response.data;
        	
        }

        function handleError(error) {
            $scope.error = error;
        }

        
      
 
	}
})();
