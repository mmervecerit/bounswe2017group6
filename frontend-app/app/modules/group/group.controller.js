
(function()
{
    angular
        .module("interestHub")
        .controller("GroupCtrl", GroupCtrl);
    
    function GroupCtrl($scope,  $rootScope, $location, GroupService)
    {

        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};

        function init() {
            GroupService
                .getAllGroups()
                .then(handleSuccess, handleError);
        }
        init();

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
            

            
        }      

        function handleSuccess(response) {
            $scope.groups = response.data;
        	
        }

        function handleError(error) {
            $scope.error = error;
        }

        
      
 
	}
})();
