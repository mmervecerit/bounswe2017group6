(function()
{
    angular
        .module("interestHub")
        .controller("NavCtrl", NavCtrl);
    
    function NavCtrl($scope, $location, $rootScope)
    {
        $scope.logout = logout;

        function logout()
        {
           
        }
    }
})();
