$(document).ready(function () {
    var trigger = $('.hamburger'),
        overlay = $('.overlay'),
        option = $('.nav a'),
        isClosed = false;


    trigger.click(function () {
        console.log(isClosed);
        hamburger_cross();
        console.log(isClosed);
    });

    function hamburger_cross() {

        if (isClosed == true) {
            overlay.hide();
            trigger.removeClass('is-open');
            trigger.addClass('is-closed');
            isClosed = false;
        } else {
            overlay.show();
            trigger.removeClass('is-closed');
            trigger.addClass('is-open');
            isClosed = true;
        }
    }

    $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
    });
});

angular.module('tmc', []).controller('pageCtrl', function ($scope, $http) {
    $scope.text = ""
    $scope.text2 = ""
    $scope.third = "";
    //Default link is google
    $scope.siteName = "http://localhost:8080/rest/datum?company=google";
    //Set class "active" to clicked item on navbar
    $(".nav a").on("click", function () {
        $(".nav").find(".active").removeClass("active");
        $(this).parent().addClass("active");
        $scope.activeSite = $(".active").children().html().toLowerCase();
        $scope.siteName = "http://localhost:8080/rest/datum?company=" + $(".active").children().html().toLowerCase();
        $http.get($scope.siteName).then(function (response) {
            $scope.dates = response.data;
        });
    });
    console.log(typeof($scope.siteName));

    $(document).on("click", ".test3", function() {
        $(".test5").html("");
        $(".test2").parent().find(".test2").removeClass("test2");
        $(this).addClass("test2");
        $scope.dateOne = $(".test2").html();
        $scope.siteNameDate = "http://localhost:8080/rest/text/" + $scope.activeSite + "?date="
            + $scope.dateOne;
        $http.get($scope.siteNameDate).then(function(response){
            $scope.text = response.data[0].text;
        })
    });

    $(document).on("click", ".test4", function() {
        $(".test5").html("");
        $(".test2").parent().find(".test2").removeClass("test2");
        $(this).addClass("test2");
        $scope.dateOne = $(".test2").html();
        $scope.siteNameDate = "http://localhost:8080/rest/text/" + $scope.activeSite + "?date="
            + $scope.dateOne;
        $http.get($scope.siteNameDate).then(function(response){
            $scope.text2 = response.data[0].text;
        })
    });

    $(".test6").on("click", function () {
        diffen();
    });

    function diffen(){
        $scope.third = "";
        $scope.fragment = "";
        var first = $scope.text;
        var second = $scope.text2;

        var color = '';
        var span = null;
        var diff = JsDiff.diffSentences(first, second);
        $scope.third = document.getElementById('text3');
        $scope.fragment = document.createDocumentFragment();
        diff.forEach(function (part) {
            // green for additions, red for deletions
            // grey for common parts
            color = part.added ? 'green' :
                part.removed ? 'red' : 'grey';
            span = document.createElement('span');
            span.style.color = color;
            span.appendChild(document
                .createTextNode(part.value));
            $scope.fragment.appendChild(span);
        });

        $scope.third.appendChild($scope.fragment);
    }

});