'use strict';

var url = "http://personalchef.ddns.net:3001/";
var timestamp;
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

.controller('DashboardController', ['$scope', '$http', '$timeout', 'getAverageData', '$rootScope',
    function ($scope, $http, $timeout, getAverageData, $rootScope) {
        $scope.avMillSpeed = 20;
        $scope.avDrillSpeed = 30;
        $scope.avMillHeat = 40;
        $scope.avDrillHeat = 50;
        $scope.avRuntime = 60;
        $scope.names = [{
            "Name": "Alfreds Futterkiste",
            "City": "Berlin",
            "Country": "Germany"
            }, {
            "Name": "Ana Trujillo Emparedados y helados",
            "City": "México D.F.",
            "Country": "Mexico"
            }, {
            "Name": "Antonio Moreno Taquería",
            "City": "México D.F.",
            "Country": "Mexico"
            }, {
            "Name": "Around the Horn",
            "City": "London",
            "Country": "UK"
            }, {
            "Name": "B's Beverages",
            "City": "London",
            "Country": "UK"
            }, {
            "Name": "Berglunds snabbköp",
            "City": "Luleå",
            "Country": "Sweden"
            }];


        var allData = getAverageData.query();
        (function tickAverageData() {
            allData.$promise.then(function (data) {
                console.log("Average-Data is now available.");
                $scope.avDrillHeat = data.drillingHeat.avg.toFixed(2);
                $scope.avDrillSpeed = data.drillingSpeed.avg.toFixed(2);
                $scope.avMillHeat = data.millingHeat.avg.toFixed(2);
                $scope.avMillSpeed = data.millingSpeed.avg.toFixed(2);
                $timeout(tickAverageData, 10000);
            })
        })()

        var labels = [];
        for (var i = 0; i < 30; i++) {
            labels = labels.concat([i]);
        }
        $scope.speedLabels = labels;
        $scope.heatLabels = labels;
        $scope.speedSeries = ['Milling', 'Drilling'];
        $scope.heatSeries = ['Milling', 'Drilling'];

        (function tick() {
            setData()
            $timeout(tick, 5000);
        })();

        function setData() {
            if (typeof $rootScope.requestedData === "undefined") {
                $rootScope.requestedData = []
            }
            var heatData, speedData = [[], []];
            for (var i = $rootScope.requestedData.length - 30; i < $rootScope.requestedData.length; i++) {
                for (var j = 0; j < $rootScope.requestedData[i].data.length; j++) {
                    var item = $rootScope.requestedData[i].data[j]
                    if (item.itemName == "Milling Speed") {
                        speedData[0].push(item.value);
                    } else if (item.itemName == "Milling Heat") {
                        heatData[0].push(item.value);
                    } else if (item.itemName == "Drilling Speed") {
                        speedData[1].push(item.value);
                    } else if (item.itemName == "Drilling Heat") {
                        heatData[1].push(item.value);
                    }
                }
            }
            $scope.heatData = [[65, 59, 80, 81, 56, 55, 40], [28, 48, 40, 19, 86, 27, 90]];
            $scope.speedData = [[65, 59, 80, 81, 56, 55, 40], [28, 48, 40, 19, 86, 27, 90]];
        }
}]);