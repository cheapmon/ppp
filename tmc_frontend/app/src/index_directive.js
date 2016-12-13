angular.module('tmc',[]).controller('pageCtrl', function($scope, $http){

    $scope.siteName = "http://localhost:8080/rest/datum?company=google";
    //Set class "active" to clicked item on navbar
    $(".nav a").on("click", function(){
        $(".nav").find(".active").removeClass("active");
        $(this).parent().addClass("active");
        $scope.siteName = "http://localhost:8080/rest/datum?company=" + $(".active").children().html().toLowerCase();
        console.log($scope.siteName);
        $http.get($scope.siteName).then(function(response){
            $scope.dates = response.data;
            console.log($scope.dates);
        });
    });
    console.log(typeof($scope.siteName));




});