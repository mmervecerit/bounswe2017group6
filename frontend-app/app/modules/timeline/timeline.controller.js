(function()
{
    angular
        .module("interestHub")
        .controller("TimelineCtrl", TimelineCtrl);
    
    function TimelineCtrl($scope, $uibModal) {
	$scope.open = function () {
	$scope.groupCreate= ["a","b","c"];
	
		var modalInstance = $uibModal.open({
		  templateUrl: 'groupcreatemodal.html',
		  controller: 'GroupModalCtrl',
		  resolve: {
			items: function () {
			  return $scope.items;
			}
		  }
		});

			
					
	 
		}
	};
	
	


})();
