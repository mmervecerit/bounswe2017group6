(function(){
    angular
        .module("interestHub")
        .factory("PostService", PostService);

    function PostService($http) {
        var api = {
            createPost: createPost,
            updatePost: updatePost,
            deletePost: deletePost,
            getPost: getPost,
            getAllPosts: getAllPosts
        };
        return api;

        
        function createPost(groupId,post){
            
            return $http.post('http://34.209.230.231:8000/group-contents/'+groupId+'/',post);
        }

        function updatePost(groupId,postID, post){
            return $http.put('http://34.209.230.231:8000/group-contents/'+groupId+"/"+postID, post);
        }

        function deletePost(groupId,postID){
            return $http.delete('http://34.209.230.231:8000/group-contents/'+groupId+"/"+postID);
        }

        function getPost(groupId,postID){
            return $http.get('http://34.209.230.231:8000/group-contents/'+groupId+"/"+postID);
        }
        
        function getAllPosts(groupId){
            console.log(groupId);
            return $http.get('http://34.209.230.231:8000/group-contents/'+groupId);
        }

    }
})();