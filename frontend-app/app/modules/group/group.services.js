(function(){
    angular
        .module("interestHub")
        .factory("GroupService", GroupService);

    function GroupService($http) {
        var api = {
            createGroup: createGroup,
            updateGroup: updateGroup,
            deleteGroup: deleteGroup,
            getGroup: getGroup,
            getAllGroups: getAllGroups
        };
        return api;

        
        function createGroup(group){
            
            return $http.post('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/group/',group);
        }

        function updateGroup(groupID, group){
            return $http.put('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/group/'+groupID, group);
        }

        function deleteGroup(groupID){
            return $http.delete('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/group/'+groupID);
        }

        function getGroup(groupID){
            return $http.get('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/group/'+groupID);
        }
        
        function getAllGroups(){
            return $http.get('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/group/');
        }

    }
})();