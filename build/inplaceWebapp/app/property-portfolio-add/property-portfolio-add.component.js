angular.module('propertyPortfolioAdd').component('propertyPortfolioAdd', { templateUrl: 'property-portfolio-add/property-portfolio-add.template.html',
	bindings: {
	    propertyOwner: '<',
	    bind: '='
	},
	controller: ['$http','$location','$routeParams',function PropertyPortfolioAddController($http,$location,$routeParams) {

	    var self = this;
	    this.submitForm = function() {
		var path = $location.absUrl();
		var index = path.indexOf("#!");
		var url = path.slice(0,index) + "../api/property-portfolios/";
		var config = {
		    headers : {
			'Content-Type' : 'application/json;charset=utf-8;'
		    }
		}
		var data = {
		    name: self.name,
		    owner: self.propertyOwner
		};
		self.debug = data;
		$http.post(url, data, config).then(function(response) {
		    self.bind = data;
		    self.getResultMessage = "Success!";
		}, function(response) {
		    self.getResultMessage = "Fail!";
		});
	    };
	    this.name = "";
	}]
});
