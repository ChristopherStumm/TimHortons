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
}])