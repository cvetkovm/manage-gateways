myApp.service('gatewayService', ['$http', '$log', '$q', function($http, $log, $q) {

	this.getGateways = function() {
		var deferred = $q.defer();
		$http({
			method: 'GET',
			url: '/gateways'
		}).then(function successCallback(response) {
			deferred.resolve({gateways:response.data});
		}, function errorCallback(response) {
			deferred.reject({response});
		});
		return deferred.promise;
	}


	this.deleteGateway = function(gateway){
		var deferred = $q.defer();
		var path = '/gateways/' + gateway.serialNumber;
		$http({
			method: 'delete',
			url: path
		}).then(function successCallback(response) {
			deferred.resolve();
		}, function errorCallback(response) {
			deferred.reject();
		});
		return deferred.promise;
	};


	this.addGateway = function(gatewaySerialNumber, gatewayName, gatewayIpAddress) {
		var deferred = $q.defer();
		$http({
			method: 'POST',
			data:{serialNumber: gatewaySerialNumber,
				name: gatewayName,
				ipAddress: gatewayIpAddress,
				devices: []},
				headers: {'Content-Type': 'application/json'},
				url: '/gateways'
		}).then(function successCallback(response) {
			deferred.resolve();
		}, function errorCallback(response) {
			deferred.reject({response});
		});
		return deferred.promise;
	};

}]);