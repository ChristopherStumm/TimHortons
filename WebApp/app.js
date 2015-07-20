'use strict';

angular.module('d3', []);
angular.module('timHortons.directives', ['d3']);
angular.module('TimHortons', [
	'ngRoute',
	'mobile-angular-ui',
	'timHortons.Dashboard',
	'timHortons.Ana1',
	'timHortons.directives',
	'd3'
	])


.controller('GeneralController',['$scope',function($scope)
{

}]);