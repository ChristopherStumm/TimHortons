'use strict';

google.load('visualization', '1', {
    packages: ['corechart']
});
google.setOnLoadCallback(function () {
    angular.bootstrap(document.body, ['myApp']);
});

angular.module('timHortons.Ana2', ['ngRoute'])

.config(['$routeProvider',
    function ($routeProvider) {
        /* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
        $routeProvider.when('/ana2', {
            templateUrl: 'ana2/ana2.html',
            controller: 'Ana2Controller as ana2Ctrl',
            reloadOnSearch: true
        });
}])

.controller('Ana2Controller', ['$scope', '$rootScope', '$timeout',
    function ($scope, $rootScope, $timeout) {
        if (typeof $rootScope.requestedData === "undefined") {
            $rootScope.requestedData = [];
        }

        (function tick() {
            udpateData();
            udpateData2();
            $timeout(tick, 5000);
        })()

        function udpateData() {
            var data = google.visualization.arrayToDataTable([
              ['Year', 'Status OK', 'Status NOK'],
              ['12:00', getTotalOkProducts(), getTotalNokProducts()],
              ['13:00', 2, 4],
              ['14:00', 3, 5],
              ['15:00', 4, 6]
            ]);

            var options = {
                title: 'Total Production',
                isStacked: true
            };

            var chart = new google.visualization.BarChart(document.getElementById('chartdiv'));
            chart.draw(data, options);
        }

        function udpateData2() {
            var allMaterials = getAllMaterials();
            console.log(allMaterials);
            var data = google.visualization.arrayToDataTable(allMaterials);

            var options = {
                title: 'Production view - Material',
                isStacked: true
            };

            var chart = new google.visualization.BarChart(document.getElementById('chartdiv2'));
            chart.draw(data, options);
        }

        function getAllMaterials() {
            if (typeof $rootScope.requestedData === "undefined") {
                return
            }

            var allMaterials = [];
            var goodProducts = [];
            var badProducts = [];

            for (var i = 0; i < $rootScope.requestedData.length; i++) {
                var dataset = $rootScope.requestedData[i];
                var materialNumber = Number(dataset.materialNumber);
                var overallStatus = dataset.overallStatus;
                if (allMaterials.indexOf(materialNumber) == -1) {
                    allMaterials.push(materialNumber);
                    if (overallStatus == "OK") {
                        goodProducts.push(1);
                        badProducts.push(0);
                    } else {
                        goodProducts.push(0);
                        badProducts.push(1);
                    }
                } else {
                    if (overallStatus == "OK") {
                        goodProducts[allMaterials.indexOf(materialNumber)] = goodProducts[allMaterials.indexOf(materialNumber)] + 1;
                    } else {
                        badProducts[allMaterials.indexOf(materialNumber)]++;
                    }
                }
            }

            var data = [['Material', 'Status OK', 'Status NOK']];
            for (var i = 0; i < allMaterials.length; i++) {
                var arrayToAdd = [];
                arrayToAdd[0] = 'Matetial ' + allMaterials[i];
                arrayToAdd[1] = goodProducts[i];
                arrayToAdd[2] = badProducts[i];
                data.push(arrayToAdd);
            }
            console.log(data);
            if (data.length > 1) return data
            else {
                return [
                  ['Material', 'Status OK', 'Status NOK'],
                  ['Material 1', 1, 2],
                  ['Material 2', 2, 4],
                  ['Material 3', 3, 5],
                  ['Material 4', 4, 6]
                ];
            }
            return data;
        }

        function getTotalOkProducts() {
            var ctr = 0;
            for (var i = 0; i < $rootScope.requestedData.length; i++) {
                if ($rootScope.requestedData[i].overallStatus == 'OK') {
                    ctr++;
                }
            }
            return ctr;
        }

        function getTotalNokProducts() {
            return $rootScope.requestedData.length - getTotalOkProducts();
        }
}]);