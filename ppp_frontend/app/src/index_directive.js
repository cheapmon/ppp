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

    $scope.isDateOne = false;
    $scope.isDateTwo = false;
    $scope.isTextOneDisplayed = false;
    $scope.isTextTwoDisplayed = false;

    // date of the displayed text
    $scope.textDate = "";
    $scope.textDateOne = "";
    $scope.textDateTwo = "";

    //Source link
    $scope.textLink = "";
    $scope.textLinkOne = "";
    $scope.textLinkTwo = "";

    //arrays for table view of the policies
    $scope.textArrayRight = [];
    $scope.textArrayLeft = [];
    $scope.diffArray = [];

    //items for the timeline
    var items = [];

    // Configuration for the Timeline
    var options = {
        height: '300px',
        min: new Date(1975, 0, 1),            // lower limit of visible range
        max: new Date(2025, 0, 1)             // upper limit of visible range
    };

    // Create a Timeline
    var timeline;

    //creates the vis js timeline
    var container;

    //boolean for timeline item toggle
    $scope.isTextOne = true;

    //boolean for diff button
    $scope.diffed = false;

    //saves the second policy
    var temp;

    //mode of the diff tool. Default is "words"
    $scope.diffBy = "words";

    //provides List of crawled sites
    $scope.siteNames = [{
        name: "Alternate"
    }, {
        name: "Amorelie"
    }, {
        name: "Apple"
    }, {
        name: "Burgerking"
    }, {
        name: "Edeka"
    }, {
        name: "Google"
    }, {
        name: "Microsoft"
    }, {
        name: "Payback"
    }, {
        name: "Paypal"
    }, {
        name: "Rocketbeans"
    }, {
        name: "Steam"
    }, {
        name: "Subway"
    }, {
        name: "Sueddeutsche"
    }, {
        name: "Trivago"
    }, {
        name: "Twitter"
    }, {
        name: "UniLeipzig"
    }, {
        name: "Vine"
    }, {
        name: "Whatsapp"
    }, {
        name: "Wikimedia"
    }, {
        name: "Zalando"
    }];

    //opens or closes the sidebar
    $scope.hamburger_cross = function () {
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
    };

    //toggle
    $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
    });

    $scope.toggleBar = function () {
        $('#wrapper').toggleClass('toggled');
    };

    //gets the clicked company from the sidebar and receives dates of stored policies
    $scope.fillDates = function (company, init) {
        $scope.company = company.toLowerCase();

        if (!init) {
            $scope.hamburger_cross();
            $('#wrapper').toggleClass('toggled');

        }
        $scope.siteName = "http://139.18.2.12:8080/rest/date?company=" + $scope.company;
        $http.get($scope.siteName).then(function (response) {
            $scope.dates = response.data;
            items = [];
            $('.vis-timeline').remove();

            container = document.getElementById('visualization');

            for(var i = 0; i < $scope.dates.length; i++){
                items.push({id: (i+1), content: $scope.dates[i].displayDateValue , start: $scope.dates[i].systemDateValue })
            }
            timeline = new vis.Timeline(container);
            timeline.setOptions(options);
            timeline.setItems(items);

            timeline.on('select', function (properties) {
                var itemID = properties.items[0];

                //creates array of filtered elements by the id of the clicked item on the timeline
                var dateObject = items.filter(function(x){
                    return x.id === itemID;
                });

                if(dateObject[0] && dateObject[0].start) {
                    var textSystemDate = dateObject[0].start;
                    $scope.textDate = dateObject[0].content;

                    if($scope.isTextOne){
                        $scope.textDateOne = $scope.textDate;
                    } else {
                        $scope.textDateTwo = $scope.textDate;
                    }

                    var success = $scope.fillText($scope.isTextOne, textSystemDate);
                    if(success){
                        $scope.isTextOne = !$scope.isTextOne;
                    }
                }
            });
        });

    };

    //gets the clicked date and receives the policy
    $scope.fillText = function (isTextOne, textSystemDate) {

        $scope.siteNameDate = "http://139.18.2.12:8080/rest/text/" + $scope.company.toLowerCase() + "?date=" + textSystemDate;
        $http.get($scope.siteNameDate).then(function (response) {
            $scope.text = response.data[0].text;
            $scope.textLink = response.data[0].link;
            if (isTextOne) {
                $scope.textArrayRight = $scope.text.split('\n');
                $scope.textLinkOne = $scope.textLink;
                $scope.text1 = $scope.text;
                $scope.isTextOneDisplayed = true;
                $scope.isDateOne = true;
            } else {
                $scope.textArrayLeft = $scope.text.split('\n');
                $scope.textLinkTwo = $scope.textLink;
                $scope.text2 = $scope.text;
                $scope.isTextTwoDisplayed = true;
                $scope.isDateTwo = true;
            }
        });
        return true;
    };

    //function for the imported diff tool
    $scope.diff = function () {
        /*if($scope.diffed){
            $("#text3").empty();
            $scope.text2 = temp;
            $scope.diffed = !$scope.diffed;
        }else if($scope.text1 && $scope.text2){*/
            if($scope.isTextTwoDisplayed) {

                temp = $scope.text2;
                $("#text3").empty();

                var first = $scope.text1;
                var second = $scope.text2;

                var color = '';
                var span = null;
                var diff;

                // switch between the different options
                switch ($scope.diffBy) {

                    case "words":
                        diff = JsDiff.diffWords(first, second);
                        break;
                    case "sentences":
                        diff = JsDiff.diffSentences(first, second);
                        break;
                }

                var third = document.getElementById('text3');
                var fragment = document.createDocumentFragment();
                diff.forEach(function (part) {
                    // blue for additions, red for deletions
                    // grey for common parts
                    color = part.added ? 'blue' :
                        part.removed ? 'red' : 'grey';
                    span = document.createElement('span');
                    span.style.color = color;
                    span.appendChild(document
                        .createTextNode(part.value));
                    fragment.appendChild(span);
                });

                third.appendChild(fragment);

                $scope.isTextTwoDisplayed = false;
            }else{
                $scope.isTextTwoDisplayed = true;
            }
        /*  $scope.diffed = !$scope.diffed;
       }else{
            return false;
        }*/
    };
    $scope.fillDates("google", true);
});
