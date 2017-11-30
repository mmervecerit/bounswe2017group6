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
                console.log("adfklasdf");
                RecommendationService.recommendUsers()
                    .then(getUsers, handleError);
                RecommendationService.recommendGroups()
                    .then(getGroups, handleError);

            }
            init();
             /**
             * @ngdoc
             * @name getUsers
             * @methodOf RecommendationCtrl
             *
             * @description
             * Method to assign recommended users
             * 
             * @param {object} response returned users
             */
            function getUsers(response) {
       
           
                $scope.suggestedUsers = response.data;
            }
            /**
             * @ngdoc
             * @name getGroups
             * @methodOf RecommendationCtrl
             *
             * @description
             * Method to assign recommended groups 
             * 
             * @param {object} response returned groups
             */
             function getGroups(response) {
       
              
                $scope.suggestedGroups = response.data;
            }
             
            function handleError(error) {
                $scope.error = error;

            }   

        

        }
 })();