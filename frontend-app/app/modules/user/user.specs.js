describe('Users factory', function() {
	
	var UserService, httpBackend;

	beforeEach(angular.mock.module('interestHub'));

	beforeEach(inject(function($httpBackend, _UserService_) {
	    UserService = _UserService_;
	    httpBackend = $httpBackend;
	  }));

	  // A simple test to verify the Users factory exists
	  it('User Service get all users', function() {
	  	var returnData = {};
	  	httpBackend.expectGET("https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/").respond(returnData);
        // make the call
        var returnedPromise = UserService.getAllUsers();
 
        // set up a handler for the response, that will put the result
        // into a variable in this scope for you to test.
        var result;
        returnedPromise.then(function (response) {
            result = response.data;

        });
 
        // flush the backend to "execute" the request to do the expectedGET assertion.
        httpBackend.flush();
 
        // check the result. 

        expect(result).toEqual(returnData);
	    
	  });

      

 
});