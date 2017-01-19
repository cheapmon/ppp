angular.module('tmc', []).controller('pageCtrl', function ($scope, $http) {

    //sidebar variables
    $scope.trigger = $('.hamburger');
    $scope.overlay = $('.overlay');
    $scope.option = $('.nav a');
    $scope.isClosed = false;

    //text fields
    $scope.text = "";
    $scope.text2 = "";
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

    //opens or closes the sidebar
    $scope.hamburger_cross = function() {
        if ($scope.isClosed == true) {
            $scope.overlay.hide();
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
        $scope.siteName = "http://localhost:8080/rest/datum?company=" + $scope.company.toLowerCase();
        $http.get($scope.siteName).then(function (response) {
            $scope.dates = response.data;
        });
    }

    //gets the clicked date and receives the policy
    $scope.fillText = function (isTextOne) {
        if(isTextOne){
            console.log("test");
            date = $scope.selectedDateOne;
        }else{
            console.log("test2");
            date = $scope.selectedDateTwo;
        }
        $scope.siteNameDate = "http://localhost:8080/rest/text/" + $scope.company.toLowerCase() + "?date=" + date;
        $http.get($scope.siteNameDate).then(function(response){
            $scope.text = response.data[0].text;
            console.log(response);
        })
        if(isTextOne){
            console.log("test3");
            $scope.text1 = $scope.text;
        }else{
            console.log("test4");
            $scope.text2 = $scope.text;
        }
    }

    $scope.fillDates("google", true);
    //$scope.fillText(true);
    //$scope.fillText(false);

    //function for the imported diff tool
    $scope.diff =function(){
        $("text3").empty();
        var first = $scope.text1;
        var second = $scope.text2;

        var color = '';
        var span = null;
        var diff = JsDiff.diffWords(first, second);
        var third = document.getElementById('text3');
        var fragment = document.createDocumentFragment();
        diff.forEach(function (part) {
            // green for additions, red for deletions
            // grey for common parts
            color = part.added ? 'green' :
                part.removed ? 'red' : 'grey';
            span = document.createElement('span');
            span.style.color = color;
            span.appendChild(document
                .createTextNode(part.value));
            fragment.appendChild(span);
        });

        third.appendChild(fragment);
        console.log(third);
    }

});