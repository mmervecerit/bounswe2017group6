(function()
{
    angular
        .module("interestHub")
        .controller("LoginCtrl", LoginCtrl);
    
    function LoginCtrl($scope, $location, $rootScope, UserService, LoginService, $localStorage)
    {

        $scope.login = login;
        $scope.signup = signup;
        $scope.tabName="signin";
        $scope.continueWithoutLogin = continueWithoutLogin;
        function init(){
             UserService
                .getAllUsers()
                .then(handleSuccess, handleError);
        }
        console.log("init");
        init();

        function login(user)
        {

            LoginService
            .getToken(user)
            .then(function(res){
                $scope.userToken = res.data.token; 
                console.log($scope.userToken);
                $location.path('/timeline');
                $localStorage.token=res.data.token;

                UserService.getLoggedInUser()
                    .then(function(response){
                        $localStorage.user=response.data;
                    },handleError);    
           

                
                $localStorage.isLogged=true;

                $rootScope.username={};
                $rootScope.username.params=$localStorage.user.username;
                //$rootScope.userID = $localStorage.user.id;
            },function(error){
                $scope.error = error;
            });

            //.then(handleSuccess, handleError);




            /*
            if(user)
            UserService
                .login(user)
                .then(
                    function(response)ghbn          
                    7+
                    {
						$rootScope.role = true;
                        $rootScope.currentUser = response.data;
                    },
                    function(err) {
                        $scope.error = err;
                    }
                );*/
        }


        function continueWithoutLogin(){
             $rootScope.role = false;

        }

        function signup(user){
            UserService
                .createUser(user)
                .then(handleSuccess,handleError);
            $scope.tabName="signin";

        }


        function loginWithFacebook(){
            console.log("facebook");
        }

        function loginWithGoogle(){
            console.log("google");            
        }

        function handleSuccess(response) {
            $scope.users = response.data;
            
        }

        function handleError(error) {
            $scope.error = error;
        }


    }
  
})();
