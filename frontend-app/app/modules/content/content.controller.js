
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
                .getAllPosts()
                .then(handleSuccess, handleError);

        }
        
        init();
        

        function createPost(){
            $location.path('/postcreate');
        }

        function remove(post)
        {
            ContentService
                .deletePost(post._id)
                .then(handleSuccess, handleError);
        }
        
        function update(post)
        {
       

            ContentService
                .updatePost(post._id, post)
                .then(handleSuccess, handleError);
        }
        
        function add(post)
        {
            ContentService
                .createPost(post)
                .then(handleSuccess, handleError);
            

            console.log("added");
        }      

        function handleSuccess(response) {
            $scope.posts = response.data; 
                   	            //console.log(posts);

        }

        function handleError(error) {
            $scope.error = error;
            console.log(error);
        }

        
      
 
	}
})();
