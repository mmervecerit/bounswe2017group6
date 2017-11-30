(function(){
     /**
     * @ngdoc service
     * @name LoginService
     * @description
     * 
     * Service to login and register users.
     */
    angular
        .module("interestHub")
        .factory("LoginService", LoginService);

    function LoginService($http, $filter) {
        var api = {
            getToken: getToken,
            registerUser: registerUser

        };
        return api;

        /**
         * @ngdoc
         * @name getToken
         * @methodOf LoginService
         *
         * @description
         * Method to login user and get token 
         * @param {object} user the user will login 
         * @returns {httpPromise} resolve with fetched data.
         */ 
        function getToken(user){
            var credentials = {"username": user.username, "password":user.password};
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/login/',credentials);
        }

        /**
         * @ngdoc
         * @name registerUser
         * @methodOf LoginService
         *
         * @description
         * Method to register user
         * @param {object} user the user will register 
         * @returns {httpPromise} resolve with fetched data.
         */ 
        function registerUser(user){
            
            var profile = {"name": user.firstname, "lastname":user.lastname, "birthdate":user.birthDateAsString, "gender":user.gender, "contacts":"contacts", 
            "about":"about","is_public":"true","facebook_account": "@test", "twitter_account": "@test","instagram_account": "@test","interests":user.interests, "photo":null};
            
            var credentials = {"username": user.username, "password":user.password, "email":user.email, "profile":profile};
            console.log(credentials);
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/register/',credentials);
        }

    }
})();