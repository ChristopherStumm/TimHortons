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

.controller('Ana2Controller', ['$scope',
    function ($scope) {
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales', 'Expenses'],
          ['2004', 1000, 400],
          ['2005', 1170, 460],
          ['2006', 660, 1120],
          ['2007', 1030, 540]
        ]);

        var options = {
            title: 'Company Performance',
            isStacked: true
        };

        var chart = new google.visualization.BarChart(document.getElementById('chartdiv'));
        chart.draw(data, options);

}])