(function(){
    angular
        .module("interestHub")
        .factory("UserService", UserService);

    function UserService($http, $localStorage) {
        var api = {
            createUser: createUser,
            updateUser: updateUser,
            deleteUser: deleteUser,
            getUser: getUser,
            getAllUsers: getAllUsers,
            getLoggedInUser: getLoggedInUser,
            getFollowings: getFollowings,
            getFollowers: getFollowers,
            getFollowingsOfCurrent: getFollowingsOfCurrent,
            getFollowersOfCurrent: getFollowersOfCurrent,
            getContents: getContents,
            getGroups: getGroups,
            followUser: followUser
        };
        return api;
        function getLoggedInUser(){
            
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/me/', {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
            
        function createUser(user){
            
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/',user , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function updateUser(userID, user){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/'+userID, user , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function deleteUser(userID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/'+userID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getUser(userID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/'+userID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        
        function getAllUsers(){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        function followUser(userId){
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/followings/', { "id": userId} ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getFollowingsOfCurrent(){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/followings/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
         function getFollowersOfCurrent(){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/followers/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

         function getFollowings(userId){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/user/'+userId+'/followings/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
         function getFollowers(userId){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/user/'+userId+'/followers/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
         function getContents(userId){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/user/'+userId+'/contents/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        function getGroups(userId){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/user/'+userId+'/groups/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        


    }
})();