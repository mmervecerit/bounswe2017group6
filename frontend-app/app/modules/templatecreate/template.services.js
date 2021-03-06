(function(){
    angular
        .module("interestHub")
        .factory("TemplateService", TemplateService);

    function TemplateService($http) {
        var api = {
            createTemplate: createTemplate,
            updateTemplate: updateTemplate,
            deleteTemplate: deleteTemplate,
            getTemplate: getTemplate,
            getAllTemplates: getAllTemplates
        };
        return api;

        
        function createTemplate(groupId,template){
            
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group-ctypes/'+groupId+'/',template);
        }

        function updateTemplate(groupId,templateID, template){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group-ctypes/'+groupId+"/"+templateID, template);
        }

        function deleteTemplate(groupId,templateID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group-ctypes/'+groupId+"/"+templateID);
        }

        function getTemplate(groupId,templateID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group-ctypes/'+groupId+"/"+templateID);
        }
        
        function getAllTemplates(groupId){
            console.log(groupId);
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group-ctypes/'+groupId);
        }

    }
})();