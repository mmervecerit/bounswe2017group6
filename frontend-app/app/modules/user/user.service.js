(function(){
    angular
        .module("interestHub")
        .factory("UserService", UserService);

    function UserService($http) {
        var api = {
            createUser: createUser,
            updateUser: updateUser,
            deleteUser: deleteUser,
            getUser: getUser,
            getAllUsers: getAllUsers
        };
        return api;

            
        function createUser(user){
            
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/',user);
        }

        function updateUser(userID, user){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/'+userID, user);
        }

        function deleteUser(userID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/'+userID);
        }

        function getUser(userID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/'+userID);
        }
        
        function getAllUsers(){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/');
        }

    }
})();