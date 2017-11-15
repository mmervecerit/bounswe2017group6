(function()
{
    angular
        .module("interestHub")
        .controller("TemplateCreateCtrl", TemplateCreateCtrl);
    
    function TemplateCreateCtrl($scope,  $rootScope, $location, TemplateService, $routeParams)
    {
		$scope.choices = [{id: '1', selection:[]}];
		$scope.req = {name:'',components:[],component_names:[]};
		$scope.addNewChoice = function() {
			var newItemNo = $scope.choices.length+1;
			$scope.choices.push({'id':newItemNo,selection:[]});
		};
    
  $scope.removeChoice = function(item) {
	var index = $scope.choices.indexOf(item);
	 $scope.choices.splice(index, 1);
	 for(i=index;i<$scope.choices.length;i++) { 
		$scope.choices[i].id=$scope.choices[i].id-1;
	}
	 
  };
  
  $scope.addNewSelection = function(item) {
    var index = $scope.choices.indexOf(item);
    console.log(index);
    var newSelectNo=$scope.choices[index].selection.length+1;
    var newSelectNo = item.selection.length+1;
    $scope.choices[index].selection.push({'id':newSelectNo});
  };
  $scope.removeSelection = function(choice,item) {
	var index = $scope.choices.indexOf(choice);
	var ind=$scope.choices[index].selection.indexOf(item);
	 $scope.choices[index].selection.splice(ind, 1);
	 
  };
  
  $scope.createTemplate = function(){
	  console.log($scope.tempname);
	for(i=0;i<$scope.choices.length;i++) { 
		$scope.req.components.push($scope.choices[i].type);
		$scope.req.component_names.push($scope.choices[i].name);
	}
	console.log($scope.req);

	TemplateService
                .createTemplate($routeParams.id, $scope.req)
                .then(handleSuccess, handleError);
				
       
	
  };
		function handleSuccess(response) {
            $scope.temps = response.data; 
            //console.log($scope.posts);

        }

        function handleError(error) {
            $scope.error = error;
            //console.log(error);
        }

        
      
 
	   }

     
})();
