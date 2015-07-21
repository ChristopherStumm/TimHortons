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
        $scope.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
        $scope.series = ['Series A'];

        $scope.data = [[65, 59, 80, 81, 56, 55, 40]];
}])