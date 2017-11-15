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
            
            return $http.post('https://crossorigin.me/http://34.209.230.231:8000/content/',post);
        }

        function updatePost(postID, post){
            return $http.put('https://crossorigin.me/http://34.209.230.231:8000/content/'+postID, post);
        }

        function deletePost(postID){
            return $http.delete('https://crossorigin.me/http://34.209.230.231:8000/content/'+postID);
        }

        function getPost(postID){
            return $http.get('https://crossorigin.me/http://34.209.230.231:8000/content/'+postID);
        }
        
        function getAllPosts(){
            return $http.get('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/content/');
        }

    }
})();