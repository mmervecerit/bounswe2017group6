(function(){
    angular
        .module("interestHub")
        .factory("GroupService", GroupService);

    function GroupService($http, $localStorage) {
        var api = {
            createGroup: createGroup,
            updateGroup: updateGroup,
            deleteGroup: deleteGroup,
            getGroup: getGroup,
            getAllGroups: getAllGroups,
            getMembers: getMembers,
            joinGroup: joinGroup
        };
        return api;

        
        function createGroup(group){
            console.log(group);
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/groups/',group , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function updateGroup(groupID, group){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/groups/'+groupID, group , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function deleteGroup(groupID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/groups/'+groupID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getGroup(groupID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/groups/'+groupID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        
        function getAllGroups(){
            console.log('Bearer '+ $localStorage.token);
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/groups/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }


        function joinGroup(groupID){
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupID+'/members/', {},{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer '+$localStorage.token
                }
            });
        }

        function getMembers(groupID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupID+'/members/', {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }


    }
})();