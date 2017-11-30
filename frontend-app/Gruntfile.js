module.exports = function(grunt) {
  grunt.loadNpmTasks('grunt-ngdocs');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-clean');

  grunt.initConfig({
    ngdocs: {
      options: {
        html5Mode: true,
        startPage: '/',

      },
      app : {
        src: ['app/app.js', 'app/app.routes.js', 'app/app.config.js'],
        title: 'App'
      },
	  services: {
      	src: ['app/modules/**/**services.js', 'app/modules/**/**service.js'],
	    title: 'Services'
      },
      controllers: {
        src: ['app/modules/**/**controller.js'],
        title: 'Controllers'
      }

    },
    connect: {
      options: {
        keepalive: true
      },
      server: {}
    },
    clean: ['docs']
    
  });

  grunt.registerTask('default', ['clean', 'ngdocs', 'connect']);

};
