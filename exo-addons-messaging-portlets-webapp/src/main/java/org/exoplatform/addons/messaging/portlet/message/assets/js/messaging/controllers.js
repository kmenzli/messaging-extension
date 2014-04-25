define( "messagingControllers", [ "SHARED/jquery", "SHARED/juzu-ajax" ], function ( $ )
{

  var messagingCtrl = function($scope, $http,messagingService) {
    var messagingContainer = $('#messaging');

    $scope.stat = "happy";

    $scope.loadStat = function(stat) {
      $scope.stat = stat;
      if(stat == 'crazy') {

        $scope.loadStatistics();
      } else if (stat == 'freaky') {
          alert("I'm freaky");
      } else {
          alert("I'm happy");
      }
    };
      /** Load stat servers **/
      $scope.loadStatistics = function() {
        $http.get(messagingContainer.jzURL('MessageApplication.getStatistics')).success(function (data) {
          $scope.globalStatistics = data.statisticBeanList;
        });

      };


  };

  return messagingCtrl;
});