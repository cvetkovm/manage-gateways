myApp.controller('devicesController', ['$scope', '$log', '$routeParams', '$http', 'dataService', '$location', 'deviceService',
	function($scope, $log, $routeParams, $http, dataService, $location, deviceService) {
	$scope.statusOptions = [
		{ name: 'Online', value: 'online' }, 
		{ name: 'Offline', value: 'offline' }
		];
	
	$scope.dateCreated = new Date();
	
	var getDevices = function(serialNumber){
		var promise = deviceService.getDevices(serialNumber);
		promise.then(function(result) {
			$scope.gateway = result.gateway;
		}, function(result) {
			if(result.response.status === 404) {
				$location.url('/404');
			}
		});
	}

	if(angular.isUndefined(dataService.gateway)) {
		getDevices($routeParams.gatewaySerialNumber);
	} else if (dataService.gateway.serialNumber != $routeParams.gatewaySerialNumber) {
		getDevices($routeParams.gatewaySerialNumber);
	} else {
		$scope.gateway = dataService.gateway;
	}
	
	$scope.deleteDevice = function(device){
		var promise = deviceService.deleteDevice(device);
		promise.then(function(result) {
			getDevices($scope.gateway.serialNumber);
		}, function(result) {
			getDevices($scope.gateway.serialNumber);
		});
	}
	
	$scope.addDevice = function(uid, vendor, dateCreated, status, serialNumber){
		var promise = deviceService.addDevice(uid, vendor, dateCreated, status, serialNumber);
		promise.then(function(result) {
			$scope.uid = '';
			$scope.vendor = '';
			$scope.dateCreated = new Date();
			$scope.status = 'online';
			getDevices($scope.gateway.serialNumber);
		}, function(result) {
			alert('Insertion failed. Server message:' + result.response.data.message);
			getDevices($scope.gateway.serialNumber);
		});
	}


}]);