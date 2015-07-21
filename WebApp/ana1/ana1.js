'use strict';

angular.module('timHortons.Ana1', ['ngRoute', 'chart.js'])

.config(['$routeProvider',
    function ($routeProvider) {
        /* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
        $routeProvider.when('/ana1', {
            templateUrl: 'ana1/ana1.html',
            controller: 'Ana1Controller as ana1Ctrl',
            reloadOnSearch: true
        });
}])

.controller('Ana1Controller', ['$scope', '$rootScope', '$timeout',
    function ($scope, $rootScope, $timeout) {
        $scope.labels = ['1234', '2345', '3456', '4567', '5678', '6789', '7890'];
        $scope.series = ['Series A'];
        var customers_numberOfOrders = {};

        (function tick() {
            setData();
            $timeout(tick, 5000);
        })()

        function setData() {
            if (typeof $rootScope.requestedData === "undefined") {
                $rootScope.requestedData = [];
            }
            for (var i = 0; i < $rootScope.requestedData.length; i++) {
                var customerNumber = $rootScope.requestedData[i].customerNumber;

                if (customerNumber.toString() in customers_numberOfOrders) {
                    customers_numberOfOrders[String(customerNumber)]++;
                } else {
                    customers_numberOfOrders[String(customerNumber)] = 1;
                }
            }
            console.log(customers_numberOfOrders);

            $scope.data = [[65, 59, 80, 81, 56, 55, 40]];
        }
}])