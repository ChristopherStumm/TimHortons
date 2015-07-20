'use strict';

var url = "http://personalchef.ddns.net:3001/test/"
var app = angular.module('timHortons.Dashboard', ['ngRoute'])

.config(['$routeProvider',
    function ($routeProvider) {
        /* Routing zur Startseite mit Neuladen der Seite beim Aufrufen der Startseite */
        $routeProvider.when('/home', {
            templateUrl: 'dashboard/dashboard.html',
            controller: 'DashboardController as dashboardCtrl',
            reloadOnSearch: true
        });
}])

.controller('DashboardController', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.title = "DemoCtrl";
        $scope.d3Data = [
            {
                name: "Greg",
                score: 98
            },
            {
                name: "Ari",
                score: 96
            },
            {
                name: "Loser",
                score: 48
            }
        ];

        $scope.salesData = [
            {
                hour: 1,
                sales: 54
            },
            {
                hour: 2,
                sales: 66
            },
            {
                hour: 3,
                sales: 77
            },
            {
                hour: 4,
                sales: 70
            },
            {
                hour: 5,
                sales: 60
            },
            {
                hour: 6,
                sales: 63
            },
            {
                hour: 7,
                sales: 55
            },
            {
                hour: 8,
                sales: 47
            },
            {
                hour: 9,
                sales: 55
            },
            {
                hour: 10,
                sales: 30
            }
  ];

        (function tick() {
            $http.get(url).success(function (data) {
                $scope.data = data;
                $scope.d3Data[0].score = data.value1
                $scope.d3Data[1].score = data.value2
                $scope.d3Data[2].score = data.value3
                console.log(data)
                $timeout(tick, 100000);
            });
        })();
}])

app.directive("linearChart", function ($window, $parse) {
    return {
        restrict: "EA",
        template: "<svg width='850' height='200'></svg>",
        link: function (scope, elem, attrs) {
            var exp = $parse(attrs.chartData);

            var salesDataToPlot = exp(scope);
            var padding = 20;
            var pathClass = "path";
            var xScale, yScale, xAxisGen, yAxisGen, lineFun;

            var d3 = $window.d3;
            var rawSvg = elem.find('svg');
            var svg = d3.select(rawSvg[0]);

            scope.$watchCollection(exp, function (newVal, oldVal) {
                salesDataToPlot = newVal;
                redrawLineChart();
            });

            function setChartParameters() {

                xScale = d3.scale.linear()
                    .domain([salesDataToPlot[0].hour, salesDataToPlot[salesDataToPlot.length - 1].hour])
                    .range([padding + 5, rawSvg.attr("width") - padding]);

                yScale = d3.scale.linear()
                    .domain([0, d3.max(salesDataToPlot, function (d) {
                        return d.sales;
                    })])
                    .range([rawSvg.attr("height") - padding, 0]);

                xAxisGen = d3.svg.axis()
                    .scale(xScale)
                    .orient("bottom")
                    .ticks(salesDataToPlot.length - 1);

                yAxisGen = d3.svg.axis()
                    .scale(yScale)
                    .orient("left")
                    .ticks(5);

                lineFun = d3.svg.line()
                    .x(function (d) {
                        return xScale(d.hour);
                    })
                    .y(function (d) {
                        return yScale(d.sales);
                    })
                    .interpolate("basis");
            }

            function drawLineChart() {

                setChartParameters();

                svg.append("svg:g")
                    .attr("class", "x axis")
                    .attr("transform", "translate(0,180)")
                    .call(xAxisGen);

                svg.append("svg:g")
                    .attr("class", "y axis")
                    .attr("transform", "translate(20,0)")
                    .call(yAxisGen);

                svg.append("svg:path")
                    .attr({
                        d: lineFun(salesDataToPlot),
                        "stroke": "blue",
                        "stroke-width": 2,
                        "fill": "none",
                        "class": pathClass
                    });
            }

            function redrawLineChart() {

                setChartParameters();

                svg.selectAll("g.y.axis").call(yAxisGen);

                svg.selectAll("g.x.axis").call(xAxisGen);

                svg.selectAll("." + pathClass)
                    .attr({
                        d: lineFun(salesDataToPlot)
                    });
            }

            drawLineChart();
        }
    };
});