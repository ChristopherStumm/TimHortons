'use strict';

angular.module('timHortons.Ana1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	/* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
	$routeProvider.when('/ana1', {
		templateUrl: 'ana1/ana1.html',
		controller: 'Ana1Controller as ana1Ctrl',
		reloadOnSearch: true});
}])

.controller('Ana1Controller', ['$scope', function($scope){
	
}])