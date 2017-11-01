
(function()
{
    angular
        .module("interestHub")
        .controller("PostCtrl", PostCtrl);
    
    function PostCtrl($scope,  $rootScope, $location, PostService)
    {
        $scope.createPost = createPost;
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};

        function init() {
            console.log("Post int");
            PostService
                .getAllPosts()
                .then(handleSuccess, handleError);

        }
        
        init();
        

        function createPost(){
            $location.path('/postcreate');
        }

        function remove(post)
        {
            PostService
                .deletePost(post._id)
                .then(handleSuccess, handleError);
        }
        
        function update(post)
        {
       

            PostService
                .updatePost(post._id, post)
                .then(handleSuccess, handleError);
        }
        
        function add(post)
        {
            PostService
                .createPost(post)
                .then(handleSuccess, handleError);
            

            console.log("added");
        }      

        function handleSuccess(response) {
            $scope.posts = response.data; 
                   	            console.log(posts);

        }

        function handleError(error) {
            $scope.error = error;
            console.log(error);
        }

        
      
 
	}
})();
