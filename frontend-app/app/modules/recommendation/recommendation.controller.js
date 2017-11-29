(function()
{
     /**
     * @ngdoc controller
     * @name RecommendationCtrl
     * @description
     * 
     * Controller for recommendations
     */
    angular
        .module("interestHub")
        .controller("RecommendationCtrl", RecommendationCtrl);
        function RecommendationCtrl($scope,  $rootScope, $routeParams, $q, $location, $localStorage, RecommendationService)
        {
            $scope.suggestedUsers = [];
            $scope.suggestedGroups = [];
            function init() {

                RecommendationService.recommendUsers()
                    .then(getUsers, handleError);
                RecommendationService.recommendGroups()
                    .then(getGroups, handleError);

            }
            function getUsers(response) {
       

                $scope.suggestedUsers = response.data;
            }
             function getGroups(response) {
       

                $scope.suggestedGroups = response.data;
            }
        

        }
 })();