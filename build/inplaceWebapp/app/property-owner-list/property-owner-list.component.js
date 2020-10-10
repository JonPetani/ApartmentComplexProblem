angular.module('propertyOwnerList').component('propertyOwnerList', {
templateUrl: 'property-owner-list/property-owner-list.template.html',
controller: ['$http','$location','$routeParams', function PropertyOwnerListController($http,$location,$routeParams) {

	    var path = $location.absUrl();
	    var index = path.indexOf("#!");
	    var url = path.slice(0,index) + "../api/property-owners";	
	    var config = {
		headers : {
		    'Content-Type' : 'application/json;charset=utf-8;'
		}
	    }
	    var self = this;
	    $http.get(url, config).then(function(response) {
		if (response != [] ) {
		    self.propertyOwners = response.data;
		    self.showPropertyOwners = true;
		} else {
		    self.getResultMessage = "No owners can be found";
		}
	    }, function(response) {
		self.getResultMessage = "Fail!";
	    });
	}]
});

