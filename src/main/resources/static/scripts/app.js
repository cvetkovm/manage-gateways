var myApp = angular.module('myApp', ['ngRoute']);

myApp.config(function ($routeProvider) {

	$routeProvider
	.when('/', {
		templateUrl: 'pages/gateways-list.html',
		controller: 'gatewaysListController'
	})

	.when('/:gatewaySerialNumber/devices', {
		templateUrl: 'pages/devices-list.html',
		controller: 'devicesController'
	})

	.when('/404', {
		templateUrl: 'pages/not_found.html',
	})

});


