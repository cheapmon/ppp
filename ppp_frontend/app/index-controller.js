angular.module('ppp').controller('ModalCtrl', function ($scope, $uibModalInstance, LoginService, localStorageService) {

    $scope.user = localStorageService.get('userName');
    $scope.showAdminPanel = false;
    $scope.userNames = {};
    $scope.pw = "";
    $scope.targetUser = "";
    $scope.targetPw = "";
    $scope.newUser= false;

    if($scope.user === 'admin'){
        $scope.showAdminPanel = true;
    }

    $scope.toggleNewUser = function(){
      $scope.newUser = !$scope.newUser;
    };
    $scope.getUsers = function(pw){
        LoginService.getUsers(pw).then(function(respone){
            $scope.userNames = respone.data;
        });
        console.log($scope.userNames);
        $scope.adminpw = "";
    };

    $scope.login = function(name,pw){
        LoginService.login(name,pw);
        $uibModalInstance.close();
    };

    $scope.editUser= function(operation){
        console.log($scope.pw);
        console.log($scope.user);
        console.log($scope.targetPw);
        if(operation === 'add'){
            LoginService.editUser($scope.user,targetNewUser,$scope.pw,$scope.targetPw,operation);
        } else {
            LoginService.editUser($scope.user,$scope.targetUser,$scope.pw,$scope.targetPw,operation);
        }


    };
    /**
     * close modal
     */
    $scope.cancel = function () {
        $uibModalInstance.dismiss();
    };
});

angular.module('ppp').controller('IndexCtrl', function ($scope, $uibModal, LoginService) {

    $scope.showModal = function(modal){
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: modal +'-modal.html',
            controller: 'ModalCtrl',
            size: 'md'
        });
    };

    $scope.logout = function(){
        LoginService.logout();
    };
});
