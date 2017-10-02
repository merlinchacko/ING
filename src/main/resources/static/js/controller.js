var app = angular.module('app', ['ui.bootstrap']);

app.controller('GetController', function($scope, $http, $location, $modal) {
	
	$scope.getfunction = function(){
		var url = $location.absUrl() + "getAllPlayers";

		var config = {
				headers : {
					'Content-Type': 'application/json;charset=utf-8;'
				}
		}

		$http.get(url, config).then(function (response) {
			$scope.response = response.data;
			$scope.itemsLength = Object.keys($scope.response).length;
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

