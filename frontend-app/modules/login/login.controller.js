(function()
{
    angular
        .module("interestHub")
        .controller("LoginCtrl", LoginCtrl);
    
    function LoginCtrl($scope, $location, $rootScope)
    {
        $scope.login = login;
        $scope.loginWithFacebook = loginWithFacebook;
        $scope.loginWithGoogle = loginWithGoogle;

        function login(user)
        {
            /*
            if(user)
            UserService
                .login(user)
                .then(
                    function(response)
                    {
						$rootScope.role = true;
                        $rootScope.currentUser = response.data;
                    },
                    function(err) {
                        $scope.error = err;
                    }
                );*/
        }

        function loginWithFacebook(){
            console.log("facebook");
        }

        function loginWithGoogle(){
            console.log("google");            
        }

    }
  
})();
