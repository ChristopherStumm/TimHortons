'use strict';

var url = "http://personalchef.ddns.net:3001/";
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
            }, {
            "Name": "Blauer See Delikatessen",
            "City": "Mannheim",
            "Country": "Germany"
            }, {
            "Name": "Blondel père et fils",
            "City": "Strasbourg",
            "Country": "France"
            }, {
            "Name": "Bólido Comidas preparadas",
            "City": "Madrid",
            "Country": "Spain"
            }, {
            "Name": "Bon app'",
            "City": "Marseille",
            "Country": "France"
            }, {
            "Name": "Bottom-Dollar Marketse",
            "City": "Tsawassen",
            "Country": "Canada"
            }, {
            "Name": "Cactus Comidas para llevar",
            "City": "Buenos Aires",
            "Country": "Argentina"
            }, {
            "Name": "Centro comercial Moctezuma",
            "City": "México D.F.",
            "Country": "Mexico"
            }, {
            "Name": "Chop-suey Chinese",
            "City": "Bern",
            "Country": "Switzerland"
            }, {
            "Name": "Comércio Mineiro",
            "City": "São Paulo",
            "Country": "Brazil"
            }]
        console.log($scope.names)

        var time = "products?time=0.5&";
        var status = "status=TOTAL&";
        var sum = "sum=false";
        var request = url + time + status + sum;

        (function tick() {
            $http.get(request).success(function (data) {

                //                for (var i = 0; i < data.length; i++) {
                //                    for (var j = 0; j < data[i].data.length; j++) {
                //                        if (data[i].data[j].itemName == "Lichtschranke 1") {
                //                            console.log(data[i].data[j].status)
                //                        }
                //                    }
                //                }
                //                $scope.data = data;

                console.log(data.length);
                $timeout(tick, 100000000000);
            });
        })();

        request = url + "heatAndSpeed";
        (function tick() {
            $http.get(request).success(function (data) {
                console.log(data);
                $scope.avDrillHeat = data.drillingHeat.avg;
                $scope.avDrillSpeed = data.drillingSpeed.avg;
                $scope.avMillHeat = data.millingHeat.avg;
                $scope.avMillSpeed = data.millingSpeed.avg;
                $timeout(tick, 100000000000);
            });
        })();

        $scope.speedLabels = ["12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"];
        $scope.heatLabels = ["12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"];
        $scope.speedSeries = ['Milling', 'Drilling'];
        $scope.heatSeries = ['Milling', 'Drilling'];
        $scope.heatData = [
    [65, 59, 80, 81, 56, 55, 40],
    [28, 48, 40, 19, 86, 27, 90]
  ];
        $scope.speedData = [
    [65, 59, 80, 81, 56, 55, 40],
    [28, 48, 40, 19, 86, 27, 90]
  ];
        $scope.onClick = function (points, evt) {
            console.log(points, evt);
        };
}]);