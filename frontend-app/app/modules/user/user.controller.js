
(function()
{
    /**
     * @ngdoc controller
     * @name UserCtrl
     * @description
     * 
     * Controller for user CRUD operations
     */    
    angular
        .module("interestHub")
        .controller("UserCtrl", UserCtrl);
    
    function UserCtrl($scope,  $rootScope, $location, UserService)
    {

        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};
         /**
         * @ngdoc
         * @name init
         * @methodOf UserCtrl
         *
         * @description
         * Method for initialization of users by getting all of them
         * 
         */ 
        function init() {
            UserService
                .getAllUsers()
                .then(handleSuccess, handleError);
        }
        init();
        /**
         * @ngdoc
         * @name remove
         * @methodOf UserCtrl
         *
         * @description
         * Method for removing user
         * @param {object} user the user will be removed
         */ 
        function remove(user)
        {
            UserService
                .deleteUser(user._id)
                .then(handleSuccess, handleError);
        }
         /**
         * @ngdoc
         * @name update
         * @methodOf UserCtrl
         *
         * @description
         * Method for updating user
         * @param {object} user the user will be updated
         */ 
        function update(user)
        {
       

            UserService
                .updateUser(user._id, user)
                .then(handleSuccess, handleError);
        }
         /**
         * @ngdoc
         * @name add
         * @methodOf UserCtrl
         *
         * @description
         * Method for adding user
         * @param {object} user the user will be added
         */ 
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
