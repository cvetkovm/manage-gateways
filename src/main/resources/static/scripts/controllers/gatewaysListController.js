myApp.controller('gatewaysListController', ['$scope', '$log', '$http', '$location', 'dataService', 'gatewayService',
	                                function($scope, $log, $http, $location, dataService, gatewayService) {

	var getGateways = function(){
		var promise = gatewayService.getGateways();
		promise.then(function(result) {
			$scope.gateways = result.gateways;
		}, function(result) {
		});
	}

	getGateways();

	$scope.goTo = function(gateway){
		dataService.gateway = gateway;
		$location.url('/' + gateway.serialNumber + '/devices');
	};

	
	$scope.deleteGateway = function(gateway){
		var promise = gatewayService.deleteGateway(gateway);
		promise.then(function(result) {
			getGateways();
		}, function(result) {
			getGateways();
		});
	}
	
	$scope.addGateway = function(){
		var promise = gatewayService.addGateway($scope.serialNumber, $scope.name, $scope.ipAddress);
		promise.then(function(result) {
			$scope.serialNumber = '';
			$scope.name = '';
			$scope.ipAddress = '';
			getGateways();
		}, function(result) {
			alert('Insertion failed. Server message:' + result.response.data.message);
			getGateways();
		});
	}

}]);