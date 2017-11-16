
(function()
{
    angular
        .module("interestHub")
        .controller("PostCtrl", PostCtrl);
    
    function PostCtrl($scope,  $rootScope, $location, PostService, $routeParams, TemplateService)

    {    

        //$scope.createPost = createPost;
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
      	$scope.tab = {};
		$scope.typeSelect=typeSelect;
        function init() {
       

            PostService
                .getAllPosts($routeParams.id)
                .then(handleSuccess, handleError);
			TemplateService
				.getAllTemplates($routeParams.id)
				.then(tempSuccess, tempError);
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
		$scope.content={};
		function add(content)
        {
			$scope.req={"content_type":{},"owner":{
            "url": "http://34.209.230.231:8000/users/1/",
            "id": 1,
            "username": "admin",
            "email": "admin@interesthub.com"
			},"owner_id": 1,"content_type_id": $scope.content.content_type_id,"components":[]}
			console.log($scope.content);
			for(i=0;i<$scope.templates.length;i++){
				if($scope.templates[i].id==$scope.content.content_type_id){
					$scope.con_type=angular.copy($scope.templates[i]);
				}
			}
			for(i=0;i<$scope.con_type.components.length;i++){
				temp= {
                "component_type": $scope.con_type.components[i],
                "order": i+1,
                "type_data": {
                    "data": content.comps[$scope.con_type.component_names[i]]
                }
				
            }
			$scope.req.components.push(temp);
			}
			$scope.req.content_type=$scope.con_type;
			
			console.log($scope.req)
			
	
			
            PostService
                .createPost($routeParams.id, $scope.req)
                .then(handleSuccess, handleError);
				
            

            
        }

        function handleSuccess(response) {
            $scope.posts = response.data; 
            //console.log($scope.posts);

        }

        function handleError(error) {
            $scope.error = error;
            //console.log(error);
        }
		function tempSuccess(response) {
			$scope.templates=response.data;
			console.log($scope.templates);
			
			

        }
		$scope.content={owner_id:'1',content_type_id:'',comps:[]};
		function typeSelect(type_id){
			$scope.content={owner_id:'1',content_type_id:'',comps:[]};
			$scope.content.content_type_id=type_id;
			//console.log($scope.content);
			
		}

        function tempError(error) {
            $scope.temperror = error;
            //console.log(temperror);
        }
		
		$scope.templates = [ ];


		  $scope.model = {
		    name: 'Tabs'
		};
 
	}
})();
