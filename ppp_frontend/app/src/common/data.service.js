angular.module('ppp').service('DataService', ['$http', 'backend', function ($http, backend) {

    var data = this;

    data.loadDatesForCompany = function(company){
        return $http.get(backend.url + "date?company=" + company);
    };

    data.loadTextForDateAndCompany = function(company, date){
        return $http.get(backend.url + "text/" + company.toLowerCase() + "?date=" + date);
    };

    data.submitTextToDatabase = function(temp){
        return $http.get(backend.url + temp.user.toLowerCase() + "/upload/?date=" + temp.date + '&link=' + temp.link +
        '&user=' + temp.user.toLowerCase() + '&text=' + temp.text);
    };

    data.loadTextsfromUDB = function(user){
        return $http.get(backend.url + user.toLowerCase() + '/load?user=' + user.toLowerCase())
    };

    data.removeText = function(user, date, link){
        return $http.get(backend.url + user + '/remove?date=' + date + '&link=' + link);
    };
    return data;
}]);