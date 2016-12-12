angular.module('demo',[]).controller('Hello', function($scope, $http){
 $http.get('http://localhost:8080/rest/datum?company=google').then(function(response){
        $scope.greeting = response.data;
     console.log($scope.greeting);
    });
});