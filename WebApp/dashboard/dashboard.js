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
        var running = true;
        $scope.avMillSpeed = 20;
        $scope.avDrillSpeed = 30;
        $scope.avMillHeat = 40;
        $scope.avDrillHeat = 50;
        $scope.avRuntime = 60;


        var allData = getAverageData.query();
        (function tickAverageData() {
            if (!running) {
                return;
            }
            allData.$promise.then(function (data) {
                console.log("Average-Data is now available.");
                $scope.avDrillHeat = data.drillingHeat.avg.toFixed(2);
                $scope.avDrillSpeed = data.drillingSpeed.avg.toFixed(2);
                $scope.avMillHeat = data.millingHeat.avg.toFixed(2);
                $scope.avMillSpeed = data.millingSpeed.avg.toFixed(2);
                $timeout(tickAverageData, 25000);
            })
        })()

        var labels = [];
        for (var i = 0; i < 30; i++) {
            labels = labels.concat([i]);
        }
        $scope.speedLabels = labels;
        $scope.heatLabels = labels;
        $scope.speedSeries = ['abc', 'def'];
        $scope.heatSeries = ['Milling', 'Drilling'];

        (function tick() {
            setData()
            $timeout(tick, 5000);
        })();

        function setData() {
            if (typeof $rootScope.requestedData === "undefined") {
                return;
            } else if ($rootScope.requestedData.length < 30) {
                return;
            }

            var heatData = [[0], [0]];
            var speedData = [[0], [0]];
            var notOkProducts = [];

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

            for (var i = ($rootScope.requestedData.length - 1); i >= 0; i--) {
                if ($rootScope.requestedData[i].overallStatus != "OK") {
                    notOkProducts.push({
                        "OrderNumber": $rootScope.requestedData[i].orderNumber,
                        "Customer": $rootScope.requestedData[i].customerNumber,
                        "Material": $rootScope.requestedData[i].materialNumber
                    });
                    if (notOkProducts.length >= 10) {
                        break;
                    }
                }
            }
            console.log("Failures");
            console.log(notOkProducts);

            $scope.heatData = heatData;
            $scope.speedData = speedData;
            $scope.names = notOkProducts;
        }

        $scope.$on('$routeChangeStart', function (next, current) {
            running = false;
        });
            }]);