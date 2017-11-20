
(function()
{
    angular
        .module("interestHub")
        .controller("ContentCtrl", ContentCtrl);
    
    function ContentCtrl($scope,  $rootScope, $location, ContentService, GroupService, $q)
    {
        $scope.remove = remove;
        $scope.update = update;
        $scope.add    = add;
        $scope.upVote = upVote;
        $scope.downVote = downVote;
      	$scope.tab = {};
        $scope.posts = [];
        $scope.postComment = postComment;
        function init() {

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
            //$scope.posts = {};
            posts = response.data;
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
             $q.all(posts.map(function (post) {
                    ContentService.getCommentsOfContent(post.id)
                            .then(function (response) {
                                post.comments = response.data;
                            });
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
                            });
                    $scope.posts.push(post);
        
                })).then(function () {
                });
              
           
        }
        function postComment(text,index, ContentID){
            console.log(index);
            console.log(text);
            var comment = {
                "text" : text
            };
            ContentService.createCommentToContent(ContentID,comment)
                .then(
                    function(response){
                        $scope.posts[index].comments.push(response.data);
                    },
                    handleError);
                console.log($scope.posts);
        }
        function upVote(index, contentId){
            var vote = {
                "isUp" : true,
                "content_id": contentId
            }
            console.log("up");
            ContentService.voteToContent(contentId,vote)
                .then(function(response){
                    console.log(response.data);
                    $scope.posts[index].votes.push(response.data);
                },handleError);

        }
         function downVote(index, contentId){
             var vote = {
                "isUp" : false,
                "content_id": contentId
            }
            console.log("down");
            ContentService.voteToContent(contentId,vote)
                .then(function(response){
                    console.log(response.data);

                    $scope.posts[index].votes.push(response.data);
                },handleError);

        }
        function handleError(error) {
            $scope.error = error;
            console.log(error);
        }

        
      
 
	}
})();
