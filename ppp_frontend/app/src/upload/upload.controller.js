angular.module('ppp')
    .controller('UploadCtrl', function ($scope, FileUploader, DataService, $rootScope, localStorageService){

        $scope.linkPattern = "";
        //richtige Jahr/Monat/Tag - Grenzen einbauen
        $scope.datePattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

        var uploader = $scope.uploader = new FileUploader({
            url: 'http://localhost:8080/rest/fileupload'
        });

        $scope.loadTexts = function(){
            DataService.loadTextsfromUDB(localStorageService.get('userName')).then(function (response) {
                $scope.userTexts = response.data;
            });
        };
        $scope.uploadText = function(){

            var temp = {};
            temp.text = $scope.text;
            temp.date = $scope.date;
            temp.link = $scope.link;
            temp.user = localStorageService.get('userName');
            console.log(temp);
            DataService.submitTextToDatabase(temp);
            $scope.loadTexts();
        };

        $scope.uploadTextFromFile = function(){
        };

        $scope.removeText = function(date,link){
            DataService.removeText(localStorageService.get('userName'), date ,link);
            $scope.loadTexts();
        }

    });