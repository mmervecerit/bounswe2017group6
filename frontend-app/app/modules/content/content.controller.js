
(function()
{
    angular
        .module("interestHub")
        .controller("ContentCtrl", ContentCtrl);
    
    function ContentCtrl($scope,  $rootScope, $location, ContentService, GroupService)
    {
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};

        function init() {
            console.log("Post int");
            ContentService
                .getAllContents()
                .then(handleSuccess, handleError);

        }
        
        init();
     
        function remove(post)
        {
            ContentService
                .deleteContent(post._id)
                .then(handleSuccess, handleError);
        }
        
        function update(post)
        {
       

            ContentService
                .updateContent(post._id, post)
                .then(handleSuccess, handleError);
        }
        
        function add(post)
        {
            ContentService
                .createContent(post)
                .then(handleSuccess, handleError);
            

            console.log("added");
        }      

        function handleSuccess(response) {
            $scope.posts = response.data;
            //Get group name
            /*var arrayLength = $scope.posts.length;
            for (var i = 0; i < arrayLength; i++) {
                GroupService.getGroup($scope.posts[i].groups[0])
                    .then(
                    function(response){
                        $scope.posts.groupname = response.data.name; 
                    },
                    handleError);
            }*/

        }

        function handleError(error) {
            $scope.error = error;
            console.log(error);
        }

        
      
 
	}
})();
