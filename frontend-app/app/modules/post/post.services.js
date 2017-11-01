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

        
        function createPost(post){
            
            return $http.post('http://34.209.230.231:8000/group-contents/',post);
        }

        function updatePost(postID, post){
            return $http.put('http://34.209.230.231:8000/group-contents/'+postID, post);
        }

        function deletePost(postID){
            return $http.delete('http://34.209.230.231:8000/group-contents/'+postID);
        }

        function getPost(postID){
            return $http.get('http://34.209.230.231:8000/group-contents/'+postID);
        }
        
        function getAllPosts(){
            return $http.get('http://34.209.230.231:8000/group-contents/1/');
        }

    }
})();