(function()
 {
 	/**
     * @ngdoc controller
     * @name SearchCtrl
     * @description
     * 
     * Controller for search page
     */
     angular
         .module("interestHub")
         .controller("SearchCtrl", SearchCtrl);
     
     function SearchCtrl($scope,  $rootScope, $location,  $window, SearchService)
     {
         $scope.isSearched=false;
         $scope.searchedUsers=[];
                /**
         * @ngdoc
         * @name searchUser
         * @methodOf SearchCtrl
         *
         * @description
         * Method for searching users
         * @param {string} input the string will be searched in wikidata
         * @returns {Array} tags the search results from wikidata
         */
        $scope.searchUser=function (val) {
            return SearchService.searchUser(val)
               .then(function(response){

                   //$scope.noResults=false;
                   $scope.isSearched=true;
                  $scope.searchedUsers=[];
                  $scope.searchedUsers=response.data;
                  console.log($scope.searchedUsers.length);
                return $scope.searchedUsers;
               }
               ,handleError);
               
                       
        };

        $scope.goToProfile=function (selected) {
            if($scope.isSearched==true){  

            angular.forEach($scope.searchedUsers, function(item, index) {
                console.log(item, index);
                console.log(selected);
                if(item.username==selected){
                    $location.path('/user/'+item.id);
                   }
              });
               
               
            } 
               
                       
        };

        function handleError(error) {
            $scope.error = error;
            $scope.isSearched=false;
        }
       
  
 	}
 })();