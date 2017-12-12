angular.module('ppp')
    .controller('CompareCtrl', function ($scope, backend, DataService) {

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

        $scope.stepTwoCounter = 0;
        $scope.datesPicked = false;

        var height = $(window).height() - 50;

        $(".view").height(height);

        //items for the timeline
        var items = [];

        // Configuration for the Timeline
        var options = {
            height: '300px',
            min: new Date(1975, 0, 1),            // lower limit of visible range
            max: new Date(2025, 0, 1)             // upper limit of visible range
        };

        //boolean for timeline item toggle
        $scope.isTextOne = true;

        //boolean for diff button
        $scope.diffed = false;

        //mode of the diff tool. Default is "words"
        $scope.diffBy = "words";

        //TODO Rest-Schnittstelle Seiten
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

        $scope.shut= function(panelNumber){

            switch(panelNumber){
                case 1:
                    document.getElementById("collapse1").classList.remove('in');
                    break;
                case 2:
                    document.getElementById("collapse2").classList.remove('in');
                    break;
                case 3:
                    document.getElementById("collapse3").classList.remove('in');
                    break;
            }

        };
        //gets the clicked company from the sidebar and receives dates of stored policies
        $scope.fillDates = function (company) {
            $scope.company = company.toLowerCase();

            DataService.loadDatesForCompany($scope.company).then(function (response) {
                $scope.dates = response.data;
                items = [];
                $('.vis-timeline').remove();
                container = document.getElementById('visualization');

                for (var i = 0; i < $scope.dates.length; i++) {
                    items.push({
                        id: (i + 1),
                        content: $scope.dates[i].displayDateValue,
                        start: $scope.dates[i].systemDateValue
                    })
                }
                timeline = new vis.Timeline(container);
                timeline.setOptions(options);
                timeline.setItems(items);

                timeline.on('select', function (properties) {
                    var itemID = properties.items[0];

                    //creates array of filtered elements by the id of the clicked item on the timeline
                    var dateObject = items.filter(function (x) {
                        return x.id === itemID;
                    });

                    if (dateObject[0] && dateObject[0].start) {
                        var textSystemDate = dateObject[0].start;
                        $scope.textDate = dateObject[0].content;

                        if ($scope.isTextOne) {
                            $scope.textDateOne = $scope.textDate;
                        } else {
                            $scope.textDateTwo = $scope.textDate;
                            $scope.datesPicked = true;
                        }

                        var success = $scope.fillText($scope.isTextOne, textSystemDate);
                        if (success) {
                            $scope.isTextOne = !$scope.isTextOne;
                        }
                    }
                });
            });

        };

        //gets the clicked date and receives the policy
        $scope.fillText = function (isTextOne, textSystemDate) {

            DataService.loadTextForDateAndCompany($scope.company, textSystemDate).then(function (response) {
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
            if ($scope.isTextTwoDisplayed) {

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

                function diffText(text, color) {
                    this.text = text;
                    this.color = color;
                }

                var diffIndex = 0;
                diff.forEach(function (part) {
                    // blue for additions, red for deletions
                    // grey for common parts
                    color = part.added ? 'blue' :
                        part.removed ? 'red' : 'grey';

                    $scope.diffArray[diffIndex] = new diffText(part.value, color);
                    diffIndex++;
                });

                $scope.isTextTwoDisplayed = false;
            } else {
                $scope.isTextTwoDisplayed = true;
            }
        };
        $scope.fillDates("google", true);
    });
