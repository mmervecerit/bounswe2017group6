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
            
            return $http.post('https://crossorigin.me/http://34.209.230.231:8000/group-contents/'+groupId+'/',post);
        }

        function updatePost(groupId,postID, post){
            return $http.put('https://crossorigin.me/http://34.209.230.231:8000/group-contents/'+groupId+"/"+postID, post);
        }

        function deletePost(groupId,postID){
            return $http.delete('https://crossorigin.me/http://34.209.230.231:8000/group-contents/'+groupId+"/"+postID);
        }

        function getPost(groupId,postID){
            return $http.get('https://crossorigin.me/http://34.209.230.231:8000/group-contents/'+groupId+"/"+postID);
        }
        
        function getAllPosts(groupId){
            console.log(groupId);
            return $http.get('https://crossorigin.me/http://34.209.230.231:8000/group-contents/'+groupId);
        }

    }
})();