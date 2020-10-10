
angular.module('candidateList').
    component('candidateList', {
	templateUrl: 'candidate-list/candidate-list.template.html',
	controller: ['$http','$location','$routeParams',function CandidateListController($http,$location,$routeParams) {
	    
	    var path = $location.absUrl();
	    var index = path.indexOf("#!");
	    var url = path.slice(0,index) + "../api/candidates";

	    var self = this;

	    var config = {
		headers : {
		    'Content-Type' : 'application/json;charset=utf-8;'
		}
	    }
	

	    $http.get(url, config).then(function(response) {
		if (response != [] ) {
		    self.candidates = response.data;
		    self.debug = self.candidates;
		    self.getResultMessage = "Success!";
		} else {
		    self.getResultMessage = "No owners can be found";
		}
	    }, function(response) {
		self.getResultMessage = "Fail!";
	    });
	}]
    });


