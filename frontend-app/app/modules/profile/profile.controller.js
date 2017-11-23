(function()
{
    angular
        .module("interestHub")
        .controller("ProfileCtrl", ProfileCtrl);
    
    function ProfileCtrl($scope,  $rootScope, $routeParams, $q, $location, UserService, ContentService, GroupService, $localStorage)
    {
        $scope.me = false;
        $scope.follow = false;
        $scope.removeInterest = removeInterest;
        $scope.addInterest = addInterest;
        $scope.editUser = editUser;
        $scope.followUser = followUser;
    	
        function init(){
            if($location.path() == '/profile' || $routeParams.id == $localStorage.user.id){
                $scope.me = true;
        		UserService.getLoggedInUser()
        			.then(handleSuccess, handleError);
            }else{
                UserService.getUser($routeParams.id)
                    .then(handleSuccess,handleError);
               
            }

    	
        }

        init();
	    
        $scope.interests = ["data science","robotics","artificial intelligence","science fiction", "travel","cycling"];
        
      	function handleSuccess(response) {
            
            $scope.user = response.data;
            if($scope.me == false){
                isFollow();
            }
            $scope.posts = [];
            $scope.groups = []; 
            $scope.followings = [];
            $scope.followers = [];
            posts = [];
            UserService.getGroups($scope.user.id)
                .then(function(response){
                    $scope.groups = response.data;
                });

            UserService.getContents($scope.user.id)
                .then(function(response){
                    console.log(response.data);
                     posts = response.data;
                     $q.all(posts.map(function (post) {
                        GroupService.getGroup(post.groups[0])
                            .then(
                                function(response){
                                    if(response.status != 404){
                                        post.group = response.data;
                                    };
                                },handleError);
                        ContentService.getCommentsOfContent(post.id)
                                .then(function (response) {
                                    post.comments = response.data;
                                },handleError);
                        ContentService.getVotesOfContent(post.id)
                                .then(function (response) {
                                    post.votes = response.data;
                                    console.log(post.votes);
                                    var like = 0;
                                    var dislike = 0;
                                    for (var i = 0; i < post.votes.length; i++){
                                        if (post.votes[i].isUp == true){
                                            like = like + 1;
                                        }else{
                                            dislike = dislike + 1;
                                        }
                                    }
                                    post.likes = like;
                                    post.dislikes = dislike;
                                },handleError);
                        $scope.posts.push(post);
            
                    })).then(function () {
                    });
                },handleError);  
           
            UserService.getFollowings($scope.user.id)
                .then(function(response){
                    $scope.followings = response.data;
                    if($localStorage.user.id != $scope.user.id){
                        UserService.getFollowingsOfCurrent()
                            .then(function(response){
                                followingsOfCurrent = response.data;
                                for(var i = 0 ; i < $scope.followings.length;i++){
                                    $scope.followings[i].isFollowed = true;
                                    for(var j = 0; j < followingsOfCurrent.length; j++){
                                        if($scope.followings[i].id == followingsOfCurrent.id){
                                            $scope.followings[i].isFollowed = false;
                                            break;
                                        }
                                    }

                                }
                            },handleError);

                    }
                },handleError);
            UserService.getFollowers($scope.user.id)
                .then(function(response){
                    $scope.followers = response.data;
                });     

        }
        function isFollow(userID){
            UserService.getFollowingsOfCurrent()
                .then(function(response){
                    followings = response.data;
                    for(var i = 0; followings.length;i++ ){
                        if(followings[i].id == $scope.user.id){
                            $scope.follow = true;
                            return true;
                        }
                    }
                    return false;
                },handleError);
        }

        function handleError(error) {
            $scope.error = error;
        }

        function removeInterest($index){
            $scope.interests.splice($index,1);
        }
        function addInterest(interest){
            $scope.interests.push(interest);
            $scope.interest = "";

        }
        function editUser(user){
            UserService.updateUser(user)
                .then(handleSuccess,handleError);
        }
        function followUser(){
            console.log($scope.user.id);
            UserService.followUser($scope.user.id)
                .then(function(response){
                    console.log($localStorage.user);
                    $scope.followers.push($localStorage.user);
            });
        }
	   
    }

     
})();
