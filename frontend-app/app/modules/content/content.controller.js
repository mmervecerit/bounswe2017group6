
(function()
{
    angular
        .module("interestHub")
        .controller("ContentCtrl", ContentCtrl);
    
    function ContentCtrl($scope,  $rootScope, $location, ContentService)
    {
        $scope.createPost = createPost;
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
        

        function createPost(){
            $location.path('/postcreate');
        }

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
            var arrayLength = $scope.posts.length;
            for (var i = 0; i < arrayLength; i++) {
                console.log($scope.posts[i].content_type);
                var components = $scope.posts[i].components;
                for(var j = 0 ; j < components.length; j++ ){
                    console.log(components[j].component_type);
                }
    //Do something
            }
            

                   	            //console.log(posts);

        }

        function handleError(error) {
            $scope.error = error;
            console.log(error);
        }

        
      
 
	}
})();
