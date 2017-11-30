(function(){
    /**
     * @ngdoc service
     * @name ContentService
     * @description
     * 
     * Service to send requests and get responses about contents.
     */
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

        /**
         * @ngdoc
         * @name createContent
         * @methodOf ContentService
         *
         * @description
         * Method to create a content with given content 
         * @param {object} content the content will be created
         * @returns {httpPromise} resolve with fetched data.
         */        
        function createContent(Content){
            
            return $http.Content('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/',Content , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        /**
         * @ngdoc
         * @name updateContent
         * @methodOf ContentService
         *
         * @description
         * Method to update a content with given id and content object
         * @param {int} contentId  id of the content will be updated
         * @param {object} content the body of updated content
         * @returns {httpPromise} resolve with fetched data.
         */   
        function updateContent(ContentID, Content){
            return $http.put('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/'+ContentID, Content , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        /**
         * @ngdoc
         * @name deleteContent
         * @methodOf ContentService
         *
         * @description
         * Method to delete a content with given content id
         * @param {int} contentId  id of the content will be deleted
         * @returns {httpPromise} resolve with fetched data.
         */   
        function deleteContent(ContentID){
            return $http.delete('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/'+ContentID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }

        /**
         * @ngdoc
         * @name getContent
         * @methodOf ContentService
         *
         * @description
         * Method to get content with given content id 
         * @param {int} contentId  id of the content will be got
         * @returns {httpPromise} resolve with fetched data.
         */   
        function getContent(ContentID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/'+ContentID , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        /**
         * @ngdoc
         * @name getAllContents
         * @methodOf ContentService
         *
         * @description
         * Method to get all contents 
         * 
         * @returns {httpPromise} resolve with fetched data.
         */  
        function getAllContents(){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/contents/' , {
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        /**
         * @ngdoc
         * @name getCommentsOfContent
         * @methodOf ContentService
         *
         * @description
         * Method to get comments of content with given content id 
         * @param {int} contentId  id of the content whose comments will be got 
         * @returns {httpPromise} resolve with fetched data.
         */  
        function getCommentsOfContent(ContentID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/comments/' ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        /**
         * @ngdoc
         * @name createCommentsToContent
         * @methodOf ContentService
         *
         * @description
         * Method to create comment to content with given content id and comment object 
         * @param {int} contentId the id of content will be commented
         * @param {object} comment the comment will be created
         * @returns {httpPromise} resolve with fetched data.
         */ 
        function createCommentToContent(ContentID,Comment){
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/comments/', Comment ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
           
        }
        /**
         * @ngdoc
         * @name getVotesOfContent
         * @methodOf ContentService
         *
         * @description
         * Method to get votes of content with given content id 
         * @param {id} contentId the content whose cotes will be get
         * @returns {httpPromise} resolve with fetched data.
         */ 
        function getVotesOfContent(ContentID){
            return $http.get('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID+'/votes/' ,{
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': 'Bearer ' + $localStorage.token

                }
            });
        }
        /**
         * @ngdoc
         * @name voteToContent
         * @methodOf ContentService
         *
         * @description
         * Method to vote to content with given content id and given vote 
         * @param {int} contentId the id of content will be voted
         * @oaram {object} vote up or down
         * @returns {httpPromise} resolve with fetched data.
         */ 
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