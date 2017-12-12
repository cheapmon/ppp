'use strict';
//TODO resolve: zuerst laden dann anzeigen
angular.module('ppp',['ngRoute', 'ngCookies', 'ui.bootstrap', 'angularFileUpload','LocalStorageModule'])
.config(function($routeProvider){
    $routeProvider
        .when('/compare', {
            templateUrl: 'src/compare/compare.template.html',
            controller: 'CompareCtrl'
        })

        .when('/upload', {
            templateUrl: 'src/upload/upload.template.html',
            controller: 'UploadCtrl',
            resolve: {
                authorize: function (LoginService) {
                    return LoginService.ping();
                }
            }
        })

        .otherwise({
            redirectTo: '/compare'

        });
})
    .constant('backend', {
        url: 'http://localhost:8080/rest/'
    });