(function()
{
    angular
        .module("interestHub")
        .controller("LoginCtrl", LoginCtrl);
    
    function LoginCtrl($scope, $location, $rootScope, UserService)
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
            for(var i = 0; i < $scope.users.length ; i++){
                if($scope.users[i].email == user.email){
                    $rootScope.role = true;
                    $rootScope.currentUser = user.username;
                    console.log(user.email)
                                $location.path('/timeline');
                                console.log("adsfasdf");
                                break;
                }
            } 
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
