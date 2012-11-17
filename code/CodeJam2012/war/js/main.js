$(document).ready(function() {
  show_manager_schedule();

  $("button#go").click(function() {
    $("div#schedule").toggleClass("hidden");
    $("div#graphs").toggleClass("hidden");
    plot_graph("div#sma-graph");
    plot_graph("div#lwma-graph");
    plot_graph("div#ema-graph");
    plot_graph("div#tma-graph");
  });
});

function plot_graph(id) {
  // clear the HTML otherwise continually calling $.plot extends height
  $(id).html("");
  $.plot( $(id),
    [ { label : "Price", data : [[0, 0], [1, 1]] } ],
    { yaxis: { max: 1 } }
  );
}

function show_manager_schedule(num_managers) {
  var id = "div#schedule";
  var schedule = [
    [Date.UTC(2012, 0, 1, 11), 1, Date.UTC(2012, 0, 1, 13), "Working..."],
    [Date.UTC(2012, 0, 1, 13, 30), 1, Date.UTC(2012, 0, 1, 15, 30), "Working..."],
    [Date.UTC(2012, 0, 1, 9), 2, Date.UTC(2012, 0, 1, 14), "Working..."],
  ];

  var breaks = [
    [Date.UTC(2012, 0, 1, 13), 1, Date.UTC(2012, 0, 1, 13, 30), "Break"],
  ];

  var num_managers = num_managers || 3;
  var managers = [];
  var ticks = [];

  for (var i = 0; i < num_managers; i++) {
    managers[i] = i+1;
    ticks[i] = [i+1, "Manager " + (i+1)];
  }

  var options = {
    series : {
      gantt : { active : true, show : true, barHeight : .5 },
    } ,
    xaxis: { min: Date.UTC(2012, 0, 1, 9), max: Date.UTC(2012, 0, 1, 18), mode: "time" },
    yaxis: { min: 0, max: managers.length + 1, ticks: ticks},
    grid: { hoverable: false, clickable: false }
  };

  $.plot($(id), [
         { label: "Work", data: schedule },
         { label: "Break", data: breaks } ], options);

}
