
(function()
{
    angular
        .module("interestHub")
        .controller("GroupCtrl", GroupCtrl);
    
    function GroupCtrl($scope,  $rootScope, $location, GroupService)
    {
        $scope.createGroup = createGroup;
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};
        $scope.groupTimeline = groupTimeline;
        function init() {
            console.log("group int");
            GroupService
                .getAllGroups()
                .then(handleSuccess, handleError);
        }
        init();
        function groupTimeline(group){
          
            console.log(group.name);
            $location.path('/group-timeline/'+group.name);
              $rootScope.group = group;
        }
        function createGroup(){
            $location.path('/groupcreate');
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
            GroupService
                .createGroup(group)
                .then(handleSuccess, handleError);
            

            console.log("added");
        }      

        function handleSuccess(response) {
            $scope.groups = response.data;
        	
        }

        function handleError(error) {
            $scope.error = error;
        }

        
      
 
	}
})();
