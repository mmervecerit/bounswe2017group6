describe('UserService', function() {
	
	var UserService, httpBackend,localStorage,LoginService;
	var registeredUser = {};
	var users = [{"username":"gokce","password":"testing12","email":"gokceuludogan@gmail.com" ,"profile":{"contacts":"","about":"about","is_public":"true","facebook_account":"@test","twitter_account":"@test","instagram_account":"@test","photo":null}},{"username":"tester","email":"gokceuludogan@gmail.com", "password":"testing2","profile":{}}];


	beforeEach(angular.mock.module('interestHub'));

	beforeEach(inject(function($httpBackend, _UserService_, _$localStorage_, _LoginService_) {
	    UserService = _UserService_;
	    httpBackend = $httpBackend;
	    localStorage = _$localStorage_;
	    LoginService = _LoginService_;

	  }));

	  it('getAllUsers', function() {
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

	 
	  /*
	  it('createUser',function(){
	  	
	  	
	  	var userData = {};
	  	httpBackend.expectPOST("https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/",users[0]).respond(registeredUser);
	  	var returnedPromise = UserService.createUser(users[0]);

	  	var result;
	  	returnedPromise.then(function(response){
	  		result = response.data;
	  	});
	  	httpBackend.flush();

	  	expect(result.username).toEqual(registeredUser.username);
	  	expect(result.email).toEqual(registeredUser.email);

	  	

	  });*/
	
	  it('register', function(){

	  	var credentials = users[0];
	  	httpBackend.expectPOST('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/register/',credentials).respond(registeredUser);
	  	
	  	var returnedPromise = LoginService.registerUser(users[0]);
	  	returnedPromise.then(function(response){
	  		result = response.data;
	  	})
	  	httpBackend.flush();

	  	expect(result).toEqual(registeredUser);
	  });
      it('login', function(){
      	var credentials = {"username": registeredUser.username, "password":registeredUser.password};
      	var token = {};
        httpBackend.expectPOST('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/login/',credentials).respond(token);

        var returnedPromise = LoginService.getToken(registeredUser);
	  	returnedPromise.then(function(response){
	  		result = response.data;
	  	})
	  	httpBackend.flush();

	  	expect(result).toEqual(token);
      });



	  it('getUser',function(){
	  	var userData = {};
	  	httpBackend.expectGET("https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/users/"+registeredUser.id).respond(userData);

	  	var returnedPromise = UserService.getUser(registeredUser.id);

	  	returnedPromise.then(function(response){
	  		result = response.data;
	  	});
	  	httpBackend.flush();

	  	expect(result).toEqual(userData);
	  	
	  });

	  it('getLoggedInUser',function(){
	  	var userData = {};
	  	 LoginService.getToken(registeredUser).then(function(resp){
	  	 	console.log(resp.data);
		  	httpBackend.expectGET("https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/me/").respond(userData);

		  	var returnedPromise = UserService.getLoggedInUser();

		  	returnedPromise.then(function(response){
		  		result = response.data;
		  		console.log(response);
		  	},function(response){
		  		console.log(response);
		  	});
		  	httpBackend.flush();

		  	expect(result).toEqual(userData);
	  	},function(resp){
	  		console.log(resp);
	  	});

	  	
	  });


 
});