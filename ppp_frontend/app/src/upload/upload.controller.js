angular.module('ppp')
    .controller('UploadCtrl', function ($scope, FileUploader, DataService){
        var uploader = $scope.uploader = new FileUploader({
            url: 'http://localhost:8080/rest/fileupload'
        });

        $scope.areaText = "";

        $scope.uploadText = function(areaText){

            if($scope.areaText !== ""){
                console.log(areaText);
            }

        };
    });