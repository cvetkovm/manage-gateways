myApp.service('deviceService', ['$http', '$log', '$q', function($http, $log, $q) {


	this.getDevices = function(gatewaySerialNumber) {
		var deferred = $q.defer();
		$http({
			method: 'GET',
			url: '/gateways/' + gatewaySerialNumber
		}).then(function successCallback(response) {
			deferred.resolve({
				gateway: response.data
			});
		}, function errorCallback(response) {
			console.log(response);
			deferred.reject({response});
		});
		return deferred.promise;
	}


	this.deleteDevice = function(device){
		var deferred = $q.defer();
		var path = '/gateways/' + device.gatewaySerialNumber + '/devices/' + device.uid;
		$http({
			method: 'delete',
			url: path
		}).then(function successCallback(response) {
			deferred.resolve();
		}, function errorCallback(response) {
			deferred.reject();
		});
		return deferred.promise;
	}


	this.addDevice = function(deviceUid, deviceVendor, deviceDate, deviceStatus, serialNumber) {
		var deferred = $q.defer();
		$http({
			method: 'POST',
			data:{uid: deviceUid,
				vendor: deviceVendor,
				dateCreated: deviceDate,
				status: deviceStatus},
				headers: {'Content-Type': 'application/json'},
				url: '/gateways/' + serialNumber + '/devices'
		}).then(function successCallback(response) {
			deferred.resolve();
		}, function errorCallback(response) {
			deferred.reject({response});
		});
		return deferred.promise;
	}

}]);