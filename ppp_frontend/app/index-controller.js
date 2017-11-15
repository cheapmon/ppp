angular.module('ppp').controller('LoginModalCtrl', function ($scope, $uibModalInstance, LoginService) {

    $scope.login = function(name,pw){
        LoginService.login(name,pw);
        $uibModalInstance.close();
    };

    /**
     * close modal
     */
    $scope.cancel = function () {
        $uibModalInstance.dismiss();
    };
});

angular.module('ppp').controller('IndexCtrl', function ($scope, $uibModal, LoginService) {

    $scope.showLogin = function(){
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'login-modal.html',
            controller: 'LoginModalCtrl',
            size: 'md'
        });
    };

    $scope.logout = function(){
        LoginService.logout();
    };
});
