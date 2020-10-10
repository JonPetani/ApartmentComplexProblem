angular.module('candidateDetail').
    component('candidateDetail', {
	templateUrl: 'candidate-detail/candidate-detail.template.html',
	controller: ['$http','$location','$routeParams',function CandidateDetailController($http,$location,$routeParams) {

	    this.showCandidate = false;

	    var path = $location.absUrl();
	    var index = path.indexOf("#!");
	    var url = path.slice(0,index) + "../api/candidates/"
		+ $routeParams.candidateId;

	    var config = {
		headers : {
		    'Content-Type' : 'application/json;charset=utf-8;'
		}
	    }
	
	    var self = this;
		
	    $http.get(url, config).then(function(response) {
	    	self.candidate= response.data;
		self.showCandidate = true;
		self.getResultMessage = "Success!";
	    }, function(response) {
		self.getResultMessage = "Fail!";
	    });
	}]
    });

