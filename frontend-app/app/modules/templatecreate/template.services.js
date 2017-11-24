(function(){
    angular
        .module("interestHub")
        .factory("TemplateService", TemplateService);

    function TemplateService($http, $localStorage) {
        var api = {
            createTemplate: createTemplate,
            updateTemplate: updateTemplate,
            deleteTemplate: deleteTemplate,
            getTemplate: getTemplate,
            getAllTemplates: getAllTemplates
        };
        return api;

        
        function createTemplate(groupId,template){
            
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/content-types/',template,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function updateTemplate(groupId,templateID, template){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/content-types/'+templateID, template,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function deleteTemplate(groupId,templateID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/content-types/'+templateID,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getTemplate(groupId,templateID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/content-types/'+templateID,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        
        function getAllTemplates(groupId){
            console.log(groupId);
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/group/'+groupId+'/content-types/',{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

    }
})();