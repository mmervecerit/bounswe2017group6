(function()
{
    /**
     * @ngdoc controller
     * @name NavCtrl
     * @description
     * 
     * Controller for navigational bar
     */
    angular
        .module("interestHub")
        .controller("NavCtrl", NavCtrl);
    
    function NavCtrl($scope, $location, $rootScope, $localStorage)
    {
        console.log($location.path());
       

        $scope.logout = logout;
        $scope.isLoggedIn = isLoggedIn;

        if($localStorage.isLogged){

            $rootScope.username={};
            $rootScope.username.params=$localStorage.user.username;
            console.log($localStorage.user.username);        
        }
        console.log("isLogged: "+isLoggedIn());
        
        /**
         * @ngdoc
         * @name logout
         * @methodOf NavCtrl
         *
         * @description
         * Method for user to logout
         * 
         */
        
        function logout(){

            $localStorage.$reset();
            delete $localStorage.user;
            delete $localStorage.token;
            
            $location.path('/home');   

            $localStorage.isLogged=false;   
            $rootScope.username.params="";
        }
         /**
         * @ngdoc
         * @name isLoggedIn
         * @methodOf NavCtrl
         *
         * @description
         * Method for checking whether user is logged in 
         * 
         */
        function isLoggedIn(){

            return $localStorage.isLogged;
        }
    }
})();
