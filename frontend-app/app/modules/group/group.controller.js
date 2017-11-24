
(function()
{
    angular
        .module("interestHub")
        .controller("GroupCtrl", GroupCtrl);
    
    function GroupCtrl($scope,  $rootScope, $location, GroupService, $window,TagService, $http)
    {
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
        $scope.addTag = addTag;
        $scope.removeTag = removeTag;
        $scope.tab = {};

        function init() {
            console.log("group int");
            GroupService
                .getAllGroups()
                .then(handleSuccess, handleError);
            console.log("asdfadsf");    

        }
        init();
       
        function handleTag(response){
            console.log(response.data);

            $scope.tags = response.data;
        }
       
       

        function remove(group)
        {
            GroupService
                .deleteGroup(group._id)
                .then(handleSuccess, handleError);
        }
        
        function update(group)
        {
       

            GroupService
                .updateGroup(group._id, group)
                .then(handleSuccess, handleError);
        }
        
        function add(group)
        {
            tags = JSON.parse(angular.toJson(group.tags));
            //group.tags = [];
            console.log(group);
            if(group.is_public == "public"){
                group.is_public = true;
            }else{
                group.is_public = false;
            }
            GroupService
                .createGroup(group)
                .then(handleSuccessGroup, handleError);    
            console.log("added");

            $scope.newgroup.tags = [];


        }      
        function handleSuccessGroup(response) {
            $scope.groups.push(response.data);

        }


        function handleSuccess(response) {
            $scope.groups = response.data;
            
        }

        function handleError(error) {
            $scope.error = error;
        }

        
                 
          var _selected;
          $scope.newgroup = {};
          $scope.newgroup.tags = [];
          $scope.selected = undefined;

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



          $scope.searchTag = function(val) {
            
                TagService.searchTag(val)
                            .then(function(response){
                                console.log(response.data.search);
                                tags = response.data.search;
                            }
                            ,handleError);
            
            return tags;            
          };
          function addTag(tag) {
            if (tag != ""){

                var tagStored = {
                    "label" : tag.label ,
                    "description" : tag.description,
                    "url" : tag.url
                }
              
                $scope.newgroup.tags.push(tagStored);
                $scope.selected = undefined;
            }
          }


        function removeTag($index){
            $scope.newgroup.tags.splice($index,1);
        }
          
                         
 
    }
})();
