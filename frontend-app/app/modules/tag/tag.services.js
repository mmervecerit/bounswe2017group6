(function(){
    /**
     * @ngdoc service
     * @name TagService
     * @description
     * 
     * Service to get tags from wikidata.
     */
    angular
        .module("interestHub")
        .factory("TagService", TagService);

    function TagService($http) {
        var api = {
            searchTag: searchTag
        };
        return api;

        /**
         * @ngdoc
         * @name searchTag
         * @methodOf TagService
         *
         * @description
         * Method to get tags from wikidata 
         * @param {string} input the input will be searched
         * @returns {httpPromise} resolve with fetched data.
         */     
        function searchTag(input){
            
            return $http.get('https://limitless-sands-55256.herokuapp.com/https://www.wikidata.org/w/api.php?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=' + input);
        }

      
    }
})();