
(function() {

    angular.module("interestHub")
        .config(['$httpProvider', function($httpProvider) {
        console.log("config");
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common["X-Requested-With"];
        


  }]);
/**
  * 
  * @ngdoc directive
  * @name interestHub.cmdate
  * @description 
  *  
  *  Filter for dates
  *
  */
        angular.module("interestHub")
            .filter('cmdate', [
                '$filter', function($filter) {
                    return function(input, format) {
                        return $filter('date')(new Date(input), format);
                    };
                }
        ]);


    /*
        .run( function( $rootScope ) {
  // Load the facebook SDK asynchronously
  (function(){
     // If we've already installed the SDK, we're done
     if (document.getElementById('facebook-jssdk')) {return;}

     // Get the first script element, which we'll use to find the parent node
     var firstScriptElement = document.getElementsByTagName('script')[0];

     // Create a new script element and set its id
     var facebookJS = document.createElement('script'); 
     facebookJS.id = 'facebook-jssdk';

     // Set the new script's source to the source of the Facebook JS SDK
     facebookJS.src = '//connect.facebook.net/en_US/all.js';

     // Insert the Facebook JS SDK into the DOM
     firstScriptElement.parentNode.insertBefore(facebookJS, firstScriptElement);
   }());
});
*/

})();
