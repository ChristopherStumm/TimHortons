'use strict';
var timestamp = 0;
var url = "http://personalchef.ddns.net:3001/";
var requestedData = []
angular.module('angularCharts', []);

angular.module('TimHortons', [
    'ngRoute',
    'ngResource',
    'mobile-angular-ui',
    'timHortons.Dashboard',
    'timHortons.Ana1',
    'timHortons.Ana2',
    'dataService'
])


.controller('GeneralController', ['$scope', '$timeout', '$http', '$rootScope',
    function ($scope, $timeout, $http, $rootScope)
    {
        (function tick() {
            console.log("Requesting with timestamp: " + timestamp)
            console.log(getProductRequest());
            $http.get(getProductRequest()).success(function (data) {
                timestamp = new Date().getTime();
                requestedData = requestedData.concat(data);
                $rootScope.requestedData = requestedData;
                console.log("DATA");
                console.log($rootScope.requestedData);
                $timeout(tick, 5000);
            }).error(function (error) {
                console.log(error)
            });
        })();
}]);

function getProductRequest() {
    var time = "products?time=24&";
    var status = "status=TOTAL&";
    var sum = "sum=false&";
    if (typeof timestamp === "undefined") {
        timestamp = 0;
    }
    var timestampString = "timestamp=" + timestamp
    var request = url + time + status + sum + timestampString;
    return request
}