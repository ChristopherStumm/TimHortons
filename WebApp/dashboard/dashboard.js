'use strict';

//var url = "http://personalchef.ddns.net:3001/test/"
var url = "data.json"
var app = angular.module('timHortons.Dashboard', ['ngRoute', 'angularCharts'])

.config(['$routeProvider',
    function ($routeProvider) {
        /* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
        $routeProvider.when('/home', {
            templateUrl: 'dashboard/dashboard.html',
            controller: 'DashboardController as dashboardCtrl',
            reloadOnSearch: true
        });
}])

.controller('DashboardController', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.title = "DemoCtrl";
        $scope.d3Data = [
            {
                name: "Greg",
                score: 98
            },
            {
                name: "Ari",
                score: 96
            },
            {
                name: "Loser",
                score: 48
            }
        ];

        (function tick() {
            $http.get(url).success(function (data) {
                $scope.data = data;
                $scope.d3Data[0].score = data.value1
                $scope.d3Data[1].score = data.value2
                $scope.d3Data[2].score = data.value3
                console.log(data)
                $timeout(tick, 10000);
            });
        })();
}])