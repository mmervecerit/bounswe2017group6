(function()
{
    /**
     * @ngdoc controller
     * @name LoginCtrl
     * @description
     * 
     * Controller for login/sign up modal in the home page
     */
    angular
        .module("interestHub")
        .controller("LoginCtrl", LoginCtrl);
    
    function LoginCtrl($scope, $location, $rootScope, UserService, LoginService, $localStorage, $window, $filter,TagService)
    {

        $scope.login = login;
        $scope.signup = signup;
        $scope.tabName="signin";
        $scope.continueWithoutLogin = continueWithoutLogin;


        $scope.gender = ["Male", "Female", "Other"];


        function init(){
            var _selected;
            $scope.newUser = {};
            $scope.newUser.tags = [];
            $scope.selected = undefined;
            tags={};
            $scope.newUser.interests=[];
        }
        init();
         /**
         * @ngdoc
         * @name login
         * @methodOf LoginCtrl
         *
         * @description
         * Method for user to login in
         * @param {object} user the user will login
         */ 
        function login(user)
        {

            LoginService
            .getToken(user)
            .then(function(res){
                $scope.userToken = res.data.token; 
                console.log($scope.userToken);
                $location.path('/timeline');
 
                $localStorage.token=res.data.token;
                $localStorage.user=user;
                $localStorage.isLogged=true;

                $rootScope.username={};
                $rootScope.username.params=$localStorage.user.username;
                
            },function(error){
                $scope.error = error;
            });

            //.then(handleSuccess, handleError);




            /*
            if(user)
            UserService
                .login(user)
                .then(
                    function(response)ghbn          
                    7+
                    {
						$rootScope.role = true;
                        $rootScope.currentUser = response.data;
                    },
                    function(err) {
                        $scope.error = err;
                    }
                );*/
        }

          /**
         * @ngdoc
         * @name signup
         * @methodOf LoginCtrl
         *
         * @description
         * Method for user to sign up
         * @param {object} user the user will sign up
         */ 
        function signup(user){
            user.gender=findGender(user.gender);
            user.interests=$scope.newUser.interests;
             user.birthDateAsString=$filter('date')(user.birthDate, "yyyy-MM-dd");

             LoginService
            .registerUser(user)
            .then(function(res) {
                user.id = res.data.id;
                $scope.userID=user.id;
                console.log($scope.userID);
                $window.location.reload();            
            },function(error){
                $scope.error = error;
            });
        }

        function continueWithoutLogin(){
             $rootScope.role = false;

        }



     function findGender(gender){
        if(gender=="Male"){
          return "man";
        }else if(gender=="Female"){
          return "woman";
        }
      }
        // function signup(user){
        //     UserService
        //         .createUser(user)
        //         .then(handleSuccess,handleError);
        //     $scope.tabName="signin";

        // }


        function loginWithFacebook(){
            console.log("facebook");
        }

        function loginWithGoogle(){
            console.log("google");            
        }

        function handleSuccess(response) {
            $scope.users = response.data;
            
        }

        function handleError(error) {
            $scope.error = error;
        }


        function handleTag(response){
            console.log(response.data);
    
            $scope.tags = response.data;
        }

    
          $scope.ngModelOptionsSelected = function(value) {
            if (arguments.length) {
              _selected = value;
            } else {
              return _selected;
            }
          };
    
          $scope.modelOptions = {
            debounce: {
              default: 500,
              blur: 250
            },
            getterSetter: true
          };
        
        /**
         * @ngdoc
         * @name searchTag
         * @methodOf LoginCtrl
         *
         * @description
         * Method for searching tags
         * @param {string} input the string will be searched in wikidata
         * @returns {Array} tags the search results from wikidata
         */
          $scope.searchTag = function(val) {
                TagService.searchTag(val)
                            .then(function(response){
                                console.log(response.data.search);
                                tags = response.data.search;
                            }
                            ,handleError);
            
            return tags;            
          };
          /**
         * @ngdoc
         * @name addTag
         * @methodOf LoginCtrl
         *
         * @description
         * Method for adding tag
         * @param {object} tag the tag will be added to view
         */ 
           $scope.addTag=function(tag) {
            if (tag != ""){
                $scope.newUser.tags.push(tag);
                $scope.selected = undefined;
                                
                $scope.newUser.interests.push({"label":tag.label, "description":tag.description, "url":"https:"+tag.url});
                console.log($scope.newUser.interests);
            }
            
          };
    
         /**
         * @ngdoc
         * @name removeTag
         * @methodOf LoginCtrl
         *
         * @description
         * Method for removing tag
         * @param {object} tag the tag will be removed from view
         */ 
        $scope.removeTag = function($index){
            $scope.newUser.tags.splice($index,1);
            $scope.newUser.interests.splice($index,1);
        };
    }

})();
