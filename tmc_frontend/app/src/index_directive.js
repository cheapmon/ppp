/**$(document).ready(function () {
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
}); */

angular.module('tmc', []).controller('pageCtrl', function ($scope, $http) {

    //sidebar variables
    $scope.trigger = $('.hamburger');
    $scope.overlay = $('.overlay');
    $scope.option = $('.nav a');
    $scope.isClosed = false;

    //text fields
    $scope.text = ""
    $scope.text2 = ""
    $scope.third = "";

    //provides List of crawled sites
    $scope.siteNames = [{
        name: "Alternate"
    },{
        name: "Amorelie"
    },{
        name: "Apple"
    },{
        name: "Burgerking"
    },{
        name: "Edeka"
    },{
        name: "Google"
    },{
        name: "Microsoft"
    },{
        name: "Payback"
    },{
        name: "Paypal"
    },{
        name: "Rocketbeans"
    },{
        name: "Steam"
    },{
        name: "Subway"
    },{
        name: "Sueddeutsche"
    },{
        name: "Trivago"
    },{
        name: "Twitter"
    },{
        name: "Uni Leipzig"
    },{
        name: "Vine"
    },{
        name: "Whatsapp"
    },{
        name: "Wikimedia"
    },{
        name: "Zalando"
    }];

    /**$scope.trigger.click(function () {
     console.log(isClosed);
     $scope.hamburger_cross();
     console.log(isClosed);
     });*/

    //opens or closes the sidebar
    $scope.hamburger_cross = function() {
        if ($scope.isClosed == true) {
            $scope.overlay.hide();
            console.log("test");
            $scope.trigger.removeClass('is-open');
            $scope.trigger.addClass('is-closed');
            $scope.isClosed = false;
        } else {
            $scope.overlay.show();
            $scope.trigger.removeClass('is-closed');
            $scope.trigger.addClass('is-open');
            $scope.isClosed = true;
        }
    }

    //toggle
    $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
    });


    //gets the clicked company from the sidebar and receives dates of stored policies
    $scope.fillDates = function (company, init) {
        $scope.company = company;

        if(!init){
            $scope.hamburger_cross();
        }
        $scope.siteName = "http://localhost:8080/rest/datum?company=" + $scope.company;
        $http.get($scope.siteName).then(function (response) {
            $scope.dates = response.data;
        });
    }

    //gets the clicked date and receives the policy
    $scope.fillText = function (date) {

        $scope.siteNameDate = "http://localhost:8080/rest/text/" + $scope.company + "?date=" + date;
        $http.get($scope.siteNameDate).then(function(response){
            $scope.text = response.data[0].text;
        })
    }

    $scope.fillDates("google", true);

    /**
    //reads company and fetches dates of privacy policies
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

    //reads date and fetches the policy
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

    //reads date and fetches the policy
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
    });*/

    //function for the imported diff tool
    $scope.diff =function(){
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