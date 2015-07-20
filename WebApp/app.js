'use strict';


angular.module('d3', []);
angular.module('timHortons.directives', ['d3']);
angular.module('angularCharts', []);

angular.module('TimHortons', [
 'ngRoute',
 'mobile-angular-ui',
 'timHortons.Dashboard',
 'timHortons.directives',
 'timHortons.Ana1',
 'timHortons.Ana2',
 ])


.controller('GeneralController', ['$scope',
    function ($scope)
    {

}]);