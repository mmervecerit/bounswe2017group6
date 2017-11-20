(function(){
    angular
        .module("interestHub")
        .factory("PostService", PostService);

    function PostService($http, $localStorage) {
        var api = {
            createPost: createPost,
            updatePost: updatePost,
            deletePost: deletePost,
            getPost: getPost,
            getAllPosts: getAllPosts
        };
        return api;

        
        function createPost(groupId,post){
            
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/contents/',post,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function updatePost(groupId,postID, post){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/contents/'+postID, post,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function deletePost(groupId,postID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/contents/'+postID,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getPost(groupId,postID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/contents/'+postID,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        
        function getAllPosts(groupId){
            console.log(groupId);
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/contents/',{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

    }
})();