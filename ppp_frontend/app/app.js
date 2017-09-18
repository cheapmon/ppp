'use strict';
//TODO resolve: zuerst laden dann anzeigen
angular.module('ppp',['ngRoute'])
.config(function($routeProvider){
    $routeProvider
        .when('/compare', {
            templateUrl: 'src/compare/compare.template.html',
            controller: 'CompareCtrl'
        })

        .when('/edit', {
            templateUrl: 'src/edit/edit.template.html',
            controller: 'EditCtrl'
        })

        .otherwise({
            redirectTo: '/compare'
        });
})
    .constant('backend', {
        url: 'http://localhost:8080/rest/'
    });