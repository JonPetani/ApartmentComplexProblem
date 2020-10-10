angular.module('propertyOwnerDetail').component('propertyOwnerDetail', { 
templateUrl: 'property-owner-detail/property-owner-detail.template.html',
controller: ['$http','$location','$routeParams',function PropertyOwnerDetailController($http,$location,$routeParams) {

	    this.showPropertyOwner = false;
	    this.propertyOwner ={};	    
	    var self = this;
	    var path = $location.absUrl();
	    var index = path.indexOf("#!");
	    var url = path.slice(0,index) + "../api/property-owners/";
	    var config = {
		headers : {
		    'Content-Type' : 'application/json;charset=utf-8;'
		}
	    }
	    $http.get(url, config).then(function(response) {
	    	self.propertyOwners= response.data;
	    }, function(response) {
		self.getResultMessage = "Fail!";
	    });
	    this.getPropertyOwner = function(ss) {
		var path = $location.absUrl();
		var index = path.indexOf("#!");
		var url = path.slice(0,index) + "../api/property-owners/" + ss;
		var config = {
		    headers : {
		    'Content-Type' : 'application/json;charset=utf-8;'
		    }
		}
		$http.get(url, config).then(function(response) {
	    	    self.propertyOwner= response.data;
		    self.showPropertyOwner = true;
		}, function(response) {
		    self.getResultMessage = "Fail!";
		});
	    }
	}]
});
