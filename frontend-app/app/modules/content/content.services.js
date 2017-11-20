(function(){
    angular
        .module("interestHub")
        .factory("ContentService", ContentService);

    function ContentService($http, $localStorage) {
        var api = {
            createContent: createContent,
            updateContent: updateContent,
            deleteContent: deleteContent,
            getContent: getContent,
            getAllContents: getAllContents,
            getCommentsOfContent: getCommentsOfContent,
            getVotesOfContent: getVotesOfContent,
            createCommentToContent: createCommentToContent,
            voteToContent: voteToContent
        };
        return api;

        
        function createContent(Content){
            
            return $http.Content('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/',Content , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function updateContent(ContentID, Content){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/'+ContentID, Content , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function deleteContent(ContentID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/'+ContentID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getContent(ContentID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/'+ContentID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        
        function getAllContents(){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        function getCommentsOfContent(ContentID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/comments/' ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        function createCommentToContent(ContentID,Comment){
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/comments/', Comment ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
           
        }
        function getVotesOfContent(ContentID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/votes/' ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        function voteToContent(ContentID,vote){
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/votes/', vote,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
           
        }

    }
})();