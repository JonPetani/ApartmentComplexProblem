angular.module('propertyOwnerAdd').component('propertyOwnerAdd', {
templateUrl: 'property-owner-add/property-owner-add.template.html',
controller: ['$http','$location','$routeParams',function PropertyOwnerAddController($http,$location,$routeParams) {

	    this.debug = "Made it here almost";
	    var self = this;	
	    this.submitForm = function() {
		var path = $location.absUrl();
		var index = path.indexOf("#!");
		var url = path.slice(0,index) + "../api/property-owners/";
		var config = {
		    headers : {
			'Content-Type' : 'application/json;charset=utf-8;'
		    }
		}
		var data = {
		    name: self.name,
		    taxID: self.taxid,
		    address: self.address,
		};
		$http.post(url, data, config).then(function(response) {
		    self.getResultMessage = "Success!";
		    self.name = "";
		    self.taxid = "";
		    self.address = "";
		}, function(response) {
		    self.getResultMessage = "Fail!";
		});
	    };
	    this.name = "";
	    this.taxid = "";
	    this.address = "";
	}]
    });
