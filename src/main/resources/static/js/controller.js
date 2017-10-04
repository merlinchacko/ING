var app = angular.module('myApp', ['ui.bootstrap']);

app.controller('GetController', function($scope, $http, $location, $modal) {
	
	$scope.resulttable = false;
	$scope.matchtable = false;
	$scope.errMessage = "";
	
	$scope.getAllPlayers = function(){
		var url = $location.absUrl() + "getAllPlayers";

		var config = {
				headers : {
					'Content-Type': 'application/json;charset=utf-8;'
				}
		}

		$http.get(url, config).then(function (response) {
			$scope.resulttable = true;
			$scope.matchtable = false;
			$scope.response = response.data;
			$scope.itemsLength = Object.keys($scope.response).length;
		}, function (response) {
			$scope.errMessage = "Invalid input or Internal server error!";
		});
	}
	
	$scope.getSuggestedMatches = function(players){
		console.log("url:"+ $location.absUrl() + "getSuggestedMatches");

		$http({
			method: 'POST',
			url: $location.absUrl() + "getSuggestedMatches",
			data : JSON.stringify(players)
		}).then(function (response) {
			$scope.resulttable = false;
			$scope.matchtable = true;
			$scope.matches = response.data;
			$scope.matchLength = Object.keys($scope.matches).length;
			$scope.itemsLength=0;
			console.log($scope.matchLength);
		}, function (response) {
			$scope.getResultMessage = "Fail!";
		});
	}
	
	$scope.getPlayedMatches = function(player){
		$modal.open({
            templateUrl: 'matches.html',
            backdrop: true, 
            windowClass: 'modal', 
            size: "sm",
            controller: function ($scope, $modalInstance, $log, data) {
                $scope.matches    = [];
                $scope.totalCount = 0;
                $scope.selplayer     = data.name;
            	/* Init */
                getData();
                function getData() {
            		angular.forEach(data.matchlist, function (match) {
            				$scope.totalCount = $scope.totalCount + 1;
            				$scope.matches.push(match);
            		});
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel'); 
                };
            },
            resolve: {
            	data: function () {
                    return player;
                }
            }
        });
	}
	
	
});

