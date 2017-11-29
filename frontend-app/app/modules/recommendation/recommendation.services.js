(function(){
    /**
     * @ngdoc service
     * @name RecommondationService
     * @description
     * 
     * Service to get recommendations from backend
     */
    angular
        .module("interestHub")
        .factory("RecommendationService", RecommendationService);

    function RecommendationService($http, $localStorage) {
        var api = {
           recommendUser: recommendUser,
           recommendGroup: recommendGroup

        };
        return api;

        function recommendUser(){
             return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/recommendation/users/', {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function recommendGroup(){
             return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/recommendation/groups/', {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });

        }
    }
 })();