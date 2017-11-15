
(function()
{
    angular
        .module("interestHub")
        .controller("PostCtrl", PostCtrl);
    
    function PostCtrl($scope,  $rootScope, $location, PostService, $routeParams)

    {    

        //$scope.createPost = createPost;
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};
        function init() {
       

            PostService
                .getAllPosts($routeParams.id)
                .then(handleSuccess, handleError);

        }
        
        init();
    
/*  
        function createPost(){
            $location.path('/postcreate');
        }
*/
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
        /*
        function add(post)
        {
            PostService
                .createPost(post)
                .then(handleSuccess, handleError);
            

            console.log("added");
        } */     
		var componentList;
		function add(content)
        {
			
			if(content.long_text!=null){
				componentList={"owner_id":1,"content_type_id":1, "components":[{ "component_type": "text", "order": 1, "small_text": null, "long_text":content.long_text , "url": "" }]};
  
			/*var component = {component_type:"text", order:1, long_text:content.text};
				ContentService
					.createComponent(component)
					.then(handleSuccess, handleError);
			*/
			//console.log(componentList);
			
            PostService
                .createPost($rootScope.group.id, componentList)
                .then(handleSuccess, handleError);
				
            }

            
        }

        function handleSuccess(response) {
            $scope.posts = response.data; 
            //console.log($scope.posts);

        }

        function handleError(error) {
            $scope.error = error;
            //console.log(error);
        }
		
		$scope.tabs = [
		    { title:'Dynamic Title 1', content:'Dynamic content 1' },
		    { title:'Dynamic Title 2', content:'Dynamic content 2' }
		  ];


		  $scope.model = {
		    name: 'Tabs'
		};
 
	}
})();
