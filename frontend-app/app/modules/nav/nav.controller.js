(function()
{
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
        
        
        function logout(){

            $localStorage.$reset();
            delete $localStorage.user;
            delete $localStorage.token;
            
            $location.path('/home');   

            $localStorage.isLogged=false;   
            $rootScope.username.params="";
        }

        function isLoggedIn(){

            return $localStorage.isLogged;
        }
    }
})();
