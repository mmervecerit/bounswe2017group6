(function()
{
    angular
        .module("interestHub")
        .controller("NavCtrl", NavCtrl);
    
    function NavCtrl($scope, $location, $rootScope)
    {
        console.log($location.path());
       

        $scope.logout = logout;

        function logout()
        {
           
        }
    }
})();
