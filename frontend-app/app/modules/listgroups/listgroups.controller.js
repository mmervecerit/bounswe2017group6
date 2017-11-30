
(function()
{
    /**
     * @ngdoc controller
     * @name ListGroupsCtrl
     * @description
     * 
     * Controller for creating/getting/updating/deleting and showing groups
     */
    angular
        .module("interestHub")
        .controller("ListGroupsCtrl", ListGroupsCtrl);
    
    function ListGroupsCtrl($scope,  $rootScope, $location, GroupService, $window, SearchService)
    {
        $scope.isSearched=false;
        /**
         * @ngdoc
         * @name init
         * @methodOf GroupCtrl
         *
         * @description
         * Method for initialization of groups by getting all of them
         * 
         */ 
        function init() {
            if($scope.isSearched==false){
            $scope.groups={};
                GroupService
                .getAllGroups()
                .then(handleSuccess, handleError);
            }

        }
        init();



        /**
         * @ngdoc
         * @name handleSuccessGroup
         * @methodOf GroupCtrl
         *
         * @description
         * Method for pushing a new group to $scope
         * @param {object} group the group will be added
         */      
        function handleSuccessGroup(response) {
            $scope.groups.push(response.data);

        }

        /**
         * @ngdoc
         * @name handleSuccess
         * @methodOf GroupCtrl
         *
         * @description
         * Method for assigning the groups to $scope.groups
         * @param {Array} groups the groups will be assigned and shown  
         */ 
        function handleSuccess(response) {
            $scope.groups = response.data;
        }

        function handleError(error) {
            $scope.error = error;
            $scope.noResults=true;
            $scope.isSearched=false;
        }

        $scope.ngModelOptionsSelected = function(value) {
            if (arguments.length) {
              _selected = value;
            } else {
              return _selected;
            }
          };
    
          $scope.modelOptions = {
            debounce: {
              default: 500,
              blur: 250
            },
            getterSetter: true
          };
        
        /**
         * @ngdoc
         * @name searchGroup
         * @methodOf ListGroupsCtrl
         *
         * @description
         * Method for searching groups
         * @param {string} input the string will be searched in wikidata
         * @returns {Array} tags the search results from wikidata
         */

       $scope.searchGroup=function (val) {
			 return SearchService.searchGroup(val)
                .then(function(response){

                    $scope.noResults=false;
                    $scope.isSearched=true;
                   $scope.groups=[];
                   $scope.groups=response.data;

                }
                ,handleError);
				
            			
         };



         $scope.checkContent = function(){
            if($scope.selected.length === 0 || typeof $scope.selected === 'undefined'){
                GroupService
                .getAllGroups()
                .then(handleSuccess, handleError);
            }else{
               
             }
           } 

          
                         
 
    }
})();
