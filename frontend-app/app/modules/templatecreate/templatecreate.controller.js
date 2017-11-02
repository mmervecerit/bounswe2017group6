(function()
{
    angular
        .module("interestHub")
        .controller("TemplateCreateCtrl", TemplateCreateCtrl);
    
    function TemplateCreateCtrl($scope,  $rootScope, $location)
    {
		$scope.choices = [{id: '1'}];
  
  $scope.addNewChoice = function() {
    var newItemNo = $scope.choices.length+1;
    $scope.choices.push({'id':newItemNo});
  };
    
  $scope.removeChoice = function(item) {
	var index = $scope.choices.indexOf(item);
	 $scope.choices.splice(index, 1);
	 for(i=index;i<$scope.choices.length;i++) { 
		$scope.choices[i].id=$scope.choices[i].id-1;
	}
	 
  };

        
      
 
	   }

     
})();
