var dataService = angular.module('dataService', ['ngResource']);
var url = "http://personalchef.ddns.net:3001/";

dataService.factory('getAverageData', ['$resource',
    function ($resource) {
        console.log(getAveragesRequest())
        var resource = $resource(getAveragesRequest(), {}, {
            query: {
                method: 'GET',
                isObject: true,
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            },
        });
        return resource;
}]);


function getAveragesRequest() {
    return url + "heatAndSpeed";
};


function getProductRequest() {
    var time = "products?time=6&";
    var status = "status=TOTAL&";
    var sum = "sum=false&";
    if (typeof timestamp === "undefined") {
        timestamp = 0;
    }
    var timestampString = "timestamp=" + timestamp
    var request = url + time + status + sum + timestampString;
    return request
};