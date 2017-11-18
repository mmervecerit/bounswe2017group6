(function(){
    angular
        .module("interestHub")
        .factory("TagService", TagService);

    function TagService($http) {
        var api = {
            searchTag: searchTag
        };
        return api;

            
        function searchTag(input){
            
            return $http.get('https://www.wikidata.org/w/api.php?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=' + input);
        }

      
    }
})();