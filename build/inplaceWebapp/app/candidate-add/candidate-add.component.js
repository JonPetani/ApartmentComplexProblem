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
		    fname: self.firstname,
		    lname: self.lastname,
		    socialSecurity: self.ssid,
     //       phoneNum: self.phonenumber,
		    address: self.address,
		    salary: self.income
		};

		$http.post(url, data, config).then(function(response) {
		    self.getResultMessage = "Success!";
		    self.firstname = "";
		    self.lastname= "";
		    self.address = "";
     //       self.phonenumber = "";
		    self.ssid = "";
		    self.income = "";	    
		}, function(response) {
		    self.getResultMessage = "Fail!";
		});
	    };

	    this.firstname = "";
	    this.lastname = "";
	    this.ssid = "";
     //   this.phonenumber = "";
	    this.address = "";
	    this.income = "";	    
	}]
    });

