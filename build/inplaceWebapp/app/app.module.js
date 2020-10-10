angular.module('propertyManagementApp', ['ngRoute', 'ngAnimate', 'list-of-candidates-for-apartments', 'adding-new-candidates', 'new-candidate-information']);
angular.module('propertyManagementApp').controller('AppNavCtrl',  function AppCtrl($scope) {
    $scope.active = "home";
    $scope.goto = function(page) {
	$scope.status = "Goto " + page;
    }
});
