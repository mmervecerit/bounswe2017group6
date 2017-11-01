(function(){
    angular
        .module("interestHub")
        .factory("ContentService", ContentService);

    function ContentService($http) {
        var api = {
            createPost: createPost,
            updatePost: updatePost,
            deletePost: deletePost,
            getPost: getPost,
            getAllPosts: getAllPosts
        };
        return api;

        
        function createPost(post){
            
            return $http.post('http://34.209.230.231:8000/content/',post);
        }

        function updatePost(postID, post){
            return $http.put('http://34.209.230.231:8000/content/'+postID, post);
        }

        function deletePost(postID){
            return $http.delete('http://34.209.230.231:8000/content/'+postID);
        }

        function getPost(postID){
            return $http.get('http://34.209.230.231:8000/content/'+postID);
        }
        
        function getAllPosts(){
            return $http.get('http://34.209.230.231:8000/content/');
        }

    }
})();