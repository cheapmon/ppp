angular.module('ppp').service('DataService', ['$http', 'backend', function ($http, backend) {

    var data = this;

    data.loadDatesForCompany = function(company){
      return $http.get(backend.url + "date?company=" + company);
    };

    data.loadTextForDateAndCompany = function(company, date){
        return $http.get(backend.url + "text/" + company.toLowerCase() + "?date=" + date);
    };

    return data;
}]);