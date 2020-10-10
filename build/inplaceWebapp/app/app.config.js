angular.
    module('propertyManagementApp').
  config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
	$locationProvider.hashPrefix('!');

      $routeProvider.
        when('/candidateadd', {
          template: '<candidate-add></candidate-add>'
        }).
        when('/candidates', {
          template: '<candidate-list></candidate-list>'
        }).
        when('/candidates/:candidateId', {
          template: '<candidate-detail></candidate-detail>'
        }).
    }
  ]);
