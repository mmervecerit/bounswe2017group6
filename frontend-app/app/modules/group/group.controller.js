
(function()
{
    angular
        .module("interestHub")
        .controller("GroupCtrl", GroupCtrl);
    
    function GroupCtrl($scope,  $rootScope, $location, GroupService, $window)
    {
        $scope.createGroup = createGroup;
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};
        function init() {
            console.log("group int");
            GroupService
                .getAllGroups()
                .then(handleSuccess, handleError);
            console.log("asdfadsf");    

        }
        init();
       
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
            console.log(group);
            console.log("add init");
			
            GroupService
                .createGroup(group)
                .then(handleSuccessGroup, handleError);    
            console.log("added");
         

           
			
        

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

        
      
 
	}
})();
