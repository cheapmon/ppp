angular.module('ppp').service('LoginService', function($http, backend, $cookies, $rootScope, $q) {

    var self = this;

    self.login = function(user, password) {
         return $http.get(backend.url + 'security/login?user=' + user + '&pw=' + password).then(function(promise) {
             $rootScope.isLoggedIn = true;
             return promise;
         }, function(promise) {
             $rootScope.isLoggedIn = false;
             return $q.reject(promise.data);
         });
    };

    self.logout = function() {
        $http.get(backend.url + 'security/logout?tokenId=' +  $http.defaults.headers.common['Authorization']);

        $http.defaults.headers.common['Authorization'] = null;
        $cookies.remove('authorization');
        $rootScope.isLoggedIn = false;
    };

    self.setAuthHeaderAndCookie = function(token) {
        $http.defaults.headers.common['Authorization'] = token.id;

        $cookies.put('authorization', token.id, {
            expires:  new Date(token.expirationTime)
        });
    };

    /**
     * Fragt beim Server an, ob Zugriffsrechte bestehen.
     * Falls ein Key im RAM liegt wird dieser verwendet, sonst wird ein Cookie mit einem Key gesucht.
     *
     * @returns {HttpPromise}
     */
    self.ping = function() {

        if (!$http.defaults.headers.common['Authorization']) {
            $http.defaults.headers.common['Authorization'] = $cookies.get('authorization');
        }

        return $http.get(backend.url + 'security/ping?tokenId=' + $http.defaults.headers.common['Authorization'])
            .then(function(promise) {
            $rootScope.isLoggedIn = true;
            return promise;
        }, function(promise) {
            $rootScope.isLoggedIn = false;
            return $q.reject(promise.data);
        });
    };

    return self;
});