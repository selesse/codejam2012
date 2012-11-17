$(document).ready(function() {
  show_manager_schedule();

  $("button#go").click(function() {
    // swap the hidden status of graphs & schedule... only at the beginning
    if ($("div#graphs").hasClass("hidden")) {
      $("div#schedule").toggleClass("hidden");
      $("div#graphs").toggleClass("hidden");
    }
    plot_graph("div#sma-graph");
    plot_graph("div#lwma-graph");
    plot_graph("div#ema-graph");
    plot_graph("div#tma-graph");
  });
});

function plot_graph(id, data) {
  data = data || [[0, 0], [1, 1]];
  // clear the HTML otherwise continually calling $.plot extends height
  $(id).html("");
  $.plot( $(id),
    [ { label : "Price", data : data } ],
    { yaxis: { max: 1 } }
  );
}

function show_manager_schedule(num_managers, schedule) {
  var id = "div#schedule";
  var num_managers = num_managers || 4;

  var schedule = schedule || [
    [Date.UTC(2012, 0, 1, 9), 4, Date.UTC(2012, 0, 1, 11), "Working"],
    [Date.UTC(2012, 0, 1, 11, 30), 4, Date.UTC(2012, 0, 1, 13, 30), "Working"],

    [Date.UTC(2012, 0, 1, 10), 3, Date.UTC(2012, 0, 1, 12), "Working"],
    [Date.UTC(2012, 0, 1, 12, 30), 3, Date.UTC(2012, 0, 1, 14, 30), "Working"],

    [Date.UTC(2012, 0, 1, 12), 2, Date.UTC(2012, 0, 1, 14), "Working"],
    [Date.UTC(2012, 0, 1, 14, 30), 2, Date.UTC(2012, 0, 1, 16, 30), "Working"],

    [Date.UTC(2012, 0, 1, 13, 30), 1, Date.UTC(2012, 0, 1, 15, 30), "Working"],
    [Date.UTC(2012, 0, 1, 16), 1, Date.UTC(2012, 0, 1, 18), "Working"],
  ];

  var breaks = [
    [Date.UTC(2012, 0, 1, 11), 4, Date.UTC(2012, 0, 1, 11, 30), "Break"],
    [Date.UTC(2012, 0, 1, 12), 3, Date.UTC(2012, 0, 1, 12, 30), "Break"],
    [Date.UTC(2012, 0, 1, 14), 2, Date.UTC(2012, 0, 1, 14, 30), "Break"],
    [Date.UTC(2012, 0, 1, 15, 30), 1, Date.UTC(2012, 0, 1, 16), "Break"],
  ];

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
