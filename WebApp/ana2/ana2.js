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
        var running = true;
        if (typeof $rootScope.requestedData === "undefined") {
            $rootScope.requestedData = [];
        }

        (function tick() {
            if (!running) {
                return;
            }
            udpateData();
            console.log("updated graphic 1");
            udpateData2();
            console.log("updated graphic 2");
            $timeout(tick, 5000);
        })()

        $scope.hours = 12;
        $scope.machines = 6;

        function udpateData() {
            var allHourData = getTotalOkProducts()
            var data = google.visualization.arrayToDataTable(allHourData);

            var options = {
                title: 'Total Production',
                isStacked: true
            };

            var chart = new google.visualization.BarChart(document.getElementById('chartdiv'));
            chart.draw(data, options);
        }

        function udpateData2() {
            var allMaterials = getAllMaterials();
            calculateMaterialPredictive();
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
            var totalProducts = [];

            for (var i = 0; i < $rootScope.requestedData.length; i++) {
                var dataset = $rootScope.requestedData[i];
                var materialNumber = Number(dataset.materialNumber);
                var overallStatus = dataset.overallStatus;
                if (allMaterials.indexOf(materialNumber) == -1) {
                    allMaterials.push(materialNumber);
                    if (overallStatus == "OK") {
                        goodProducts.push(1);
                        badProducts.push(0);
                        totalProducts.push(1);
                    } else {
                        goodProducts.push(0);
                        badProducts.push(1);
                        totalProducts.push(1);
                    }
                } else {
                    if (overallStatus == "OK") {
                        goodProducts[allMaterials.indexOf(materialNumber)] = goodProducts[allMaterials.indexOf(materialNumber)] + 1;
                    } else {
                        badProducts[allMaterials.indexOf(materialNumber)]++;
                    }
                    totalProducts[allMaterials.indexOf(materialNumber)]++;
                }
            }
            $scope.allMaterials = allMaterials;
            $scope.totalProducts = totalProducts;

            var data = [['Material', 'Status OK', 'Status NOK']];
            for (var i = 0; i < allMaterials.length; i++) {
                var arrayToAdd = [];
                arrayToAdd[0] = 'Matetial ' + allMaterials[i];
                arrayToAdd[1] = goodProducts[i];
                arrayToAdd[2] = badProducts[i];
                data.push(arrayToAdd);
            }
            if (data.length > 1) return data
            else {
                return [
                  ['Material', 'Status OK', 'Status NOK'],
                  ['Material 1', 0, 0],
                  ['Material 2', 0, 0],
                  ['Material 3', 0, 0],
                  ['Material 4', 0, 0]
                ];
            }
        }

        function calculateMaterialPredictive() {
            var obj = {};
            if ($scope.allMaterials.length > 0) {
                for (var i = 0; i < $scope.allMaterials.length; i++) {
                    obj[$scope.allMaterials[i]] = ($scope.totalProducts[i] / 12 / 5) * ($scope.machines * $scope.hours);
                }
            }
            $scope.predictiveData = obj;
        }

        function getTotalOkProducts() {
            var ctr = 0;
            var currentHour = new Date().getHours();
            var data = [
              ['Year', 'Status OK', 'Status NOK'],
              ['-1 hour', 0, 0],
              ['-2 hours', 0, 0],
              ['-3 hours', 0, 0],
              ['-4 hours', 0, 0],
              ['-5 hours', 0, 0],
              ['-6 hours', 0, 0]
            ]
            for (var i = 0; i < $rootScope.requestedData.length; i++) {
                var hourOfProduct = new Date(Number($rootScope.requestedData[i].endTime)).getHours();
                var difference = currentHour - hourOfProduct;
                //CHANGE!!!!!! difference < 6 AND data [difference + 1]
                //if (difference < 12 && difference > 6) {
                if (difference < 6) {
                    if ($rootScope.requestedData[i].overallStatus == 'OK') {
                        //data[difference - 5][1]++;
                        data[difference + 1][1]++;
                    } else {
                        //data[difference - 5][2]++;
                        data[difference + 1][2]++;
                    }
                }
            }
            return data;
        }

        function getTotalNokProducts() {
            return $rootScope.requestedData.length - getTotalOkProducts();
        }

        $scope.$on('$routeChangeStart', function (next, current) {
            running = false;
        });
}]);