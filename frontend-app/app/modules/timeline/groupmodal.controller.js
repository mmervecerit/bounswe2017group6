(function()
{
    angular
        .module("interestHub")
        .controller("GroupModalCtrl", GroupModalCtrl);
    
    function GroupModalCtrl($scope, $uibModalInstance, items) {
		$scope.items=items;
		$scope.selected = {
		item: $scope.items[0]
		};

		$scope.ok = function () {
			$uibModalInstance.close($scope.selected.item); 
		};

		$scope.cancel = function () {
			$uibModalInstance.dismiss('cancel');
		};
	};
	


})();
