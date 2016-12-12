angular.module('tmc',[]).controller('pageCtrl', function($scope, $http){
    $scope.siteName = "google";
    $http.get('http://localhost:8080/rest/datum?company=' + $scope.siteName).then(function(response){
        $scope.dates = response.data;
    });
});