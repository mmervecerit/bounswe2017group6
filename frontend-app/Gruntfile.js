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
      profile: {
        src: ['app/modules/profile/**.js'],
        title: 'Profile'
      },
      timeline: {
        src: ['app/modules/timeline/**.js'],
        title: 'Timeline'
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