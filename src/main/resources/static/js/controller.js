var app = angular.module('app', []);

app.controller('getcontroller', function($scope, $http, $location) {
	$scope.getfunction = function(){
		var url = $location.absUrl() + "getallplayers";

		var config = {
				headers : {
					'Content-Type': 'application/json;charset=utf-8;'
				}
		}

		$http.get(url, config).then(function (response) {
			$scope.response = response.data
		}, function (response) {
			$scope.getResultMessage = "Fail!";
		});
	}
});