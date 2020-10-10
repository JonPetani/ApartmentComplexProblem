
angular.module('candidateAdd').
    component('candidateAdd', {
        templateUrl: 'candidate-add/candidate-add.template.html',
	controller: ['$http','$location','$routeParams',function CandidateAddController($http,$location,$routeParams) {

	    this.debug = "Made it here almost";
	    var self = this;
	
	    this.submitForm = function() {
		var path = $location.absUrl();
		var index = path.indexOf("#!");
		var url = path.slice(0,index) + "../api/candidates/";

		var config = {
		    headers : {
			'Content-Type' : 'application/json;charset=utf-8;'
		    }
		}
	
		var data = {
		    firstName: self.firstname,
		    lastName: self.lastname,
		    ssid: self.ssid,
		    Address: self.address,
		    Income: self.income
		};

		$http.post(url, data, config).then(function(response) {
		    self.getResultMessage = "Success!";
		    self.firstname = "";
		    self.lastname = "";
		    self.ssid = "";
		    self.address = "";
		    self.income = "";	    
		}, function(response) {
		    self.getResultMessage = "Fail!";
		});
	    };

	    this.firstname = "";
	    this.lastname = "";
	    this.ssid = "";
	    this.address = "";
	    this.income = "";	    
	}]
    });

