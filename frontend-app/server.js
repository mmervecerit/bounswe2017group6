// modules =================================================
var express        = require('express');
var app            = express();
var bodyParser     = require('body-parser');
var methodOverride = require('method-override');
var multer        = require('multer'); 

var cookieParser  = require('cookie-parser');
var session       = require('express-session');
// configuration ===========================================
	
// config files


var port = process.env.PORT || 8080; // set our port

//var cors 			= require('cors');
/*var httpProxy = require('http-proxy');

var apiForwardingUrl = 'http://34.209.230.231:8000/group/';



var apiProxy = httpProxy.createProxyServer();



*/
//app.use(cors());
/*
app.all('/*', function (request, response, next) {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Headers", "X-Requested-With");
        response.header("Access-Control-Allow-Methods", "GET, POST", "PUT", "DELETE");
        next();
    });*/
// get all data/stuff of the body (POST) parameters

app.use(bodyParser.json({limit: '50mb'}));

//app.use(bodyParser.json({ type: 'application/vnd.api+json' })); // parse application/vnd.api+json as json
app.use(bodyParser.urlencoded({ parameterLimit: 100000,
    limit: '50mb',
    extended: true })); // parse application/x-www-form-urlencoded

app.use(methodOverride('X-HTTP-Method-Override')); // override with the X-HTTP-Method-Override header in the request. simulate DELETE/PUT
//app.use(express.static(__dirname + '/public')); // set the static files location /public/img will be /img for users


multer();
app.use(session({
    secret: 'this is the secret',
    resave: true,
    saveUninitialized: true
}));
app.use(cookieParser());
//app.use(cors());
//app.options('*', cors());
 

app.use(express.static(__dirname + '/app'));
/*app.all("http://34.209.230.231:8000/group/*", function(req, res) {
    apiProxy.web(req, res, {target: apiForwardingUrl});
});*/
// routes ==================================================

app.get('*', function(req, res) {
		
		res.sendfile('./app/index.html');
	});

// start app ===============================================
app.listen(port);	
console.log('Magic happens on port ' + port); 			// shoutout to the user
exports = module.exports = app; 						// expose app