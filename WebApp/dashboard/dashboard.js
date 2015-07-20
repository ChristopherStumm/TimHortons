'use strict';

angular.module('timHortons.Dashboard', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	/* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
	$routeProvider.when('/home', {
		templateUrl: 'dashboard/dashboard.html',
		controller: 'DashboardController as dashboardCtrl',
		reloadOnSearch: true});
}])

.controller('DashboardController', ['$scope', function($scope){

}])