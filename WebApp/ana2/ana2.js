'use strict';

angular.module('timHortons.Ana2', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	/* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
	$routeProvider.when('/ana2', {
		templateUrl: 'ana2/ana2.html',
		controller: 'Ana2Controller as ana2Ctrl',
		reloadOnSearch: true});
}])

.controller('Ana2Controller', ['$scope', function($scope){
	
}])