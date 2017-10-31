(function() {
    angular.module("interestHub")
        .config(function($routeProvider, $locationProvider) {
          console.log("router");
            $locationProvider
              .html5Mode(true);
            $locationProvider
              .hashPrefix("!"); 


            $routeProvider
              .when('/', {
                  templateUrl: 'modules/home/home.view.html',
                  controller: 'HomeCtrl'
                                  
              })
              .when('/home', {
                  templateUrl: 'modules/home/home.view.html',
                  controller: 'HomeCtrl'
                  
              })
              .when('/profile', {
                  templateUrl: 'modules/profile/profile.view.html',
                  controller: 'ProfileCtrl'
                  /*resolve: {
                      loggedin: checkLoggedin
                  }*/
              })
          
              .when('/login', {
                  templateUrl: 'modules/login/login.view.html',
                  controller: 'LoginCtrl',
                  controllerAs: 'model'
              })
              .when('/register', {
                  templateUrl: 'modules/register/register.view.html',
                  controller: 'RegisterCtrl',
                  controllerAs: 'model'
              })
              .when('/timeline', {
                  templateUrl: 'modules/timeline/timeline.view.html',
                  controller: 'TimelineCtrl'
              })
			  .when('/groupcreate', {
                  templateUrl: 'modules/groupcreate/groupcreate.view.html',
                  controller: 'GroupCtrl'
              })
			  .when('/templatecreate', {
                  templateUrl: 'modules/templatecreate/templatecreate.view.html',
                  controller: 'TemplateCreateCtrl'
              })
			  .when('/postcreate', {
                  templateUrl: 'modules/postcreate/postcreate.view.html',
                  controller: 'PostCreateCtrl'
              });
			  
/*
              .otherwise({
                  redirectTo: '/'
              });
*/
            
          
        });
    /*
    var checkLoggedin = function($q, $timeout, $http, $location, $rootScope)
    {
        var deferred = $q.defer();
    
        $http.get('/api/loggedin').success(function(user)
        {
            $rootScope.errorMessage = null;
            // User is Authenticated
            if (user !== '0')
            {
                $rootScope.currentUser = user;
                deferred.resolve();
            }
            // User is Not Authenticated
            else
            {
				$rootScope.role=false;
                $rootScope.errorMessage = 'You need to log in.';
                deferred.reject();
                $location.url('/login');
            }
        });
        
        return deferred.promise;
    };

	var checkRole = function($q, $timeout, $http, $location, $rootScope)
	{
		 var deferred = $q.defer();
    
        $http.get('/api/loggedin').success(function(user)
        {
			if(user !== '0' && user.role=="manager"){
				$rootScope.role = true;		
				deferred.resolve();
			}
		});
		return deferred.promise;	
	};	

    
    var checkCurrentUser = function($q, $timeout, $http, $location, $rootScope)
    {
		console.log("check current user");
        var deferred = $q.defer();
    
        $http.get('/api/loggedin').success(function(user)
        {
			
            $rootScope.errorMessage = null;
            // User is Authenticated
            if (user !== '0')
            {
				console.log("authorized");
                $rootScope.currentUser = user;
                if(user.role== "manager"){
                    $rootScope.role = true;   

                }else{
                    $rootScope.role = false;
                }
            }else{
				$rootScope.role = false;			
			}
        console.log($rootScope.role);
            deferred.resolve();
        });
        
        return deferred.promise;
    };

  */
})();
