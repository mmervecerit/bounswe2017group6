(function(){
    /**
     * @ngdoc service
     * @name RecommendationService
     * @description
     * 
     * Service to get recommendations from backend
     */
    angular
        .module("interestHub")
        .factory("RecommendationService", RecommendationService);

    function RecommendationService($http, $localStorage) {
        var api = {
           recommendUsers: recommendUsers,
           recommendGroups: recommendGroups

        };
        return api;
         /**
         * @ngdoc
         * @name recommendUsers
         * @methodOf RecommendationService
         *
         * @description
         * Method to get recommended users
         * 
         * @returns {httpPromise} resolve with fetched data.
         */
        function recommendUsers(){
             return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/recommendation/users/', {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
         /**
         * @ngdoc
         * @name recommendGroups
         * @methodOf RecommendationService
         *
         * @description
         * Method to get recommended groups
         * 
         * @returns {httpPromise} resolve with fetched data.
         */
        function recommendGroups(){
             return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/recommendation/groups/', {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });

        }
    }
 })();