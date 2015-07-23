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
            console.log("Requesting");
            $http.get(getProductRequest()).success(function (data) {
                console.log("GOT DATA WITH " + getProductRequest());
                timestamp = new Date().getTime();
                var addToRootScope = [];

                if (data.length > 5) {
                    $rootScope.requestedData = data;
                } else {
                    for (var j = 0; j < data.length; j++) {
                        var alreadyFetched = false;
                        for (var i = 0; i < $rootScope.requestedData.length; i++) {
                            if ($rootScope.requestedData[i].orderNumber == data[j].orderNumber) {
                                alreadyFetched = true;
                            }
                        }
                        if (!alreadyFetched) {
                            addToRootScope.push(data[j]);
                        }
                    }
                    for (var l = 0; l < addToRootScope.length; l++) {
                        $rootScope.requestedData.push(addToRootScope[l]);
                    }
                }
                console.log("new Data in rootscope, new length: " + $rootScope.requestedData.length);
                $timeout(tick, 20000);
            }).error(function (error) {
                console.log(error)
            });
        })();
}]);

function getProductRequest() {
    var time = "products?time=6&";
    var status = "status=TOTAL&";
    var sum = "sum=false&";
    if (typeof timestamp === "undefined") {
        timestamp = 0;
    }
    //var timestampString = "timestamp=" + timestamp
    var timestampString = "timestamp=0";
    var request = url + time + status + sum + timestampString;
    return request
}