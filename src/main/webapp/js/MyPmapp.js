var myApp = angular.module('pmapp', []);

app.controller('postCandidatesController', function($scope, $http, $location) {
    $scope.submitForm = function(){
	var url = $location.absUrl() + "api/candidates";
	
	var config = {
            headers : {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        }
	
	var data = {
            firstName: $scope.firstname,
            lastName: $scope.lastname,
            ssid: $scope.ssid,
	    Address: $scope.address,
	    Income: $scope.income
        };
	
	
	$http.post(url, data, config).then(function (response) {
	    $scope.postResultMessage = "Sucessful!";
	}, function (response) {
	    $scope.postResultMessage = "Fail!";
	});
	
	$scope.firstname = url;
	$scope.lastname = "";
	$scope.ssid = "";
	$scope.address = "";
	$scope.income = "";	
	
    }
});

app.controller('getallCandidatesController', function($scope, $http, $location) {
    
    $scope.showAllCandidates = false;
    
    $scope.getAllCandidates = function() {
	var url = $location.absUrl() + "api/candidates";
	
	var config = {
	    headers : {
		'Content-Type' : 'application/json;charset=utf-8;'
	    }
	}
	$http.get(url, config).then(function(response) {
	    
	    if (response != []) {
		$scope.allcandidates = response.data;
		$scope.showAllCandidates = true;
		
	    } else {
		$scope.getResultMessage = "No Candidates";
	    }
	    
	}, function(response) {
	    $scope.getResultMessage = "Fail!";
	});
	
    }
});

app.controller('getCandidateController', function($scope, $http, $location) {
    
    $scope.showCandidate = false;
    
    $scope.getCandidate = function() {
	var url = $location.absUrl() + "api/candidates/" + $scope.candidateId;
	
	var config = {
	    headers : {
		'Content-Type' : 'application/json;charset=utf-8;'
	    }
	}
	
	$http.get(url, config).then(function(response) {
	    $scope.candidate = response.data;
	    $scope.showCandidate = true;
	}, function(response) {
	    $scope.getResultMessage = "Fail!";
	});
	
    }
});

app.controller('searchCandidatesController', function($scope, $http, $location) {
    
    $scope.showSearchCandidates = false;
    
    $scope.searchCandidates = function() {
	var url = $location.absUrl() + "api/candidates/query";
	
	var config = {
	    headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
	    params: { 'searchany' : $scope.candidateSearch }
	}
	
	$http.get(url, config).then(function(response) {
	    if (response != []) {
		$scope.allCandidatesFromSearch = response.data;
		$scope.showSearchCandidates = true;
	    } else {
		$scope.getResultMessage = "No array for search!";
	    }
	}, function(response) {
	    $scope.getResultMessage = "Fail!";
	});
	
    }
});
