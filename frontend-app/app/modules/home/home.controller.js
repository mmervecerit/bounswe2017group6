(function()
{
    angular
        .module("interestHub")
        .controller("HomeCtrl", HomeCtrl);
    
    function HomeCtrl($scope,  $rootScope, $location)
    {
      $rootScope.apiAddress = "http://34.209.230.231:8000/";
      
        /*
        FB.login(function(response) {
              if (response.status === 'connected') {
                console.log("asjkdfjkasdf");
                // Logged into your app and Facebook.
              } else {
                // The person is not logged into this app or we are unable to tell. 
              }
        });*/
 
	}
})();
