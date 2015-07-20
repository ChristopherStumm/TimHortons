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

.controller('Ana1Controller', ['$scope',
    function ($scope) {
        $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
        $scope.series = ['Series A', 'Series B'];
        $scope.data = [
    [65, 59, 80, 81, 56, 55, 40],
    [28, 48, 40, 19, 86, 27, 90]
  ];
        $scope.onClick = function (points, evt) {
            console.log(points, evt);
        };
}])