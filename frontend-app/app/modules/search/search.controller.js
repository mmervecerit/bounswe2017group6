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
     
     function SearchCtrl($q, $scope,  $rootScope, $location,  $window, SearchService, GroupService, UserService,$routeParams)
     {
         $scope.isSearched=false;
         $scope.searchedUsers=[];
         $scope.search = search;
         $scope.showGroups = showGroups;
         $scope.showPeople = showPeople;
         $scope.searchUser = searchUser;
         function search(query){
            console.log(query);

            $location.path("/search/"+query);
            console.log(query);
            console.log($routeParams);
            searchUser($routeParams.q);

            searchGroup(query);
         }
         function showGroups(){
            GroupService.getAllGroups().then(function(response){
                $scope.groups = response.data;
            });
         }
         function showPeople(){

            UserService.getAllUsers().then(function(response){
                console.log(response.data);
                $scope.users = response.data;
            });
         }
         function init(){
            console.log("init");
            console.log($routeParams);
            searchUser($routeParams.q);
            searchGroup($routeParams.q);
            /*GroupService.getAllGroups().then(function(response){
                $scope.groups = response.data;
            });*/
            /*UserService.getAllUsers().then(function(response){
                $scope.users = response.data;
            });*/
         }
         $scope.groupSec= true;
         $scope.peopleSec=true; 
        $scope.users=[];
         init();
        
        /**
         }
         * @ngdoc
         * @name searchUser
         * @methodOf SearchCtrl
         *
         * @description
         * Method for searching users
         * @param {string} input the string will be searched 
         * 
         */
        function searchUser(val) {
            console.log(val);
            console.log($routeParams.q);
            SearchService.searchUser(val)
               .then(function(response){
                console.log(response.data);
                users = response.data;
                  $q.all(users.map(function (user) {
                   
                    UserService.getUser(user.id)
                        .then(function (response) {
                                console.log(response.data);
                                user = response.data;
                                $scope.users.push(user);
                            },handleError);
                   
                   
                             
                    })).then(function () {
                    
                    }); 
               }
               ,handleError);
               
                       
        };

        function searchGroup(query){
            console.log("group"+query);
            SearchService.searchGroup(query)
                .then(function(response){
                    console.log(response.data);
                    $scope.groups = response.data;
                },handleError);
        }

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