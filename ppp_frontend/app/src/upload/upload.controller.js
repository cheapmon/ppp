angular.module('ppp')
    .controller('UploadCtrl', function ($scope, FileUploader, DataService, $rootScope){

        $scope.linkPattern = "";
        //richtige Jahr/Monat/Tag - Grenzen einbauen
        $scope.datePattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

        var uploader = $scope.uploader = new FileUploader({
            url: 'http://localhost:8080/rest/fileupload'
        });

        $scope.uploadText = function(){

            var temp = {};
            temp.text = $scope.text;
            temp.date = $scope.date;
            temp.link = $scope.link;
            temp.user = $rootScope.userName;
            console.log(temp);
            DataService.submitTextToDatabase(temp);
        };
    });