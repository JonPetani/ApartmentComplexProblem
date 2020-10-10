angular.module('propertyPortfolioList').component('propertyPortfolioList', { templateUrl: 'property-portfolio-list/property-portfolio-list.template.html',
	bindings: {
	    addPortfolio: '<'
	},
	controller: ['$http','$location','$routeParams', function PropertyPortfolioListController($http,$location,$routeParams) {

	    this.showPropertyPortfolios = false;
	    var path = $location.absUrl();
	    var index = path.indexOf("#!");
	    var url = path.slice(0,index) + "../api/property-portfolios";
	    var config = {
		headers : {
		    'Content-Type' : 'application/json;charset=utf-8;'
		}
	    }
	    var self = this;
	    $http.get(url, config).then(function(response) {
		if (response != [] ) {
		    self.propertyPortfolios = response.data;
		    self.showPropertyPortfolios = true;
		} else {
		    self.getResultMessage = "No owners can be found";
		}
	    }, function(response) {
		self.getResultMessage = "Fail!";
	    });
	    this.$onChanges = function( changes ) {
		console.log('second-child changed one-way bindings', changes)
		console.log('url', url)
		console.log('config', config)				
		$http.get(url, config).then(function(response) {
		    if (response != [] ) {
			self.propertyPortfolios = response.data;
			self.showPropertyPortfolios = true;
			console.log('Should have a new portfolio', self.propertyPortfolios)
		    } else {
			self.getResultMessage = "Owners could not be found";
		    }
		}, function(response) {
		    self.getResultMessage = "Error! Fail!";
		});
	    }
	}]
});
