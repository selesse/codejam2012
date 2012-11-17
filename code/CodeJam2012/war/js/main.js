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

function plot_graph(id, price, slow, fast) {
  var graph_name = id.substring(id.indexOf("#") + 1, id.indexOf("-")).toUpperCase();

  price = price || [ [0, 0], [2, 1] ];
  slow = slow || [ [0, 0], [1, 0.25], [2, 1] ];
  fast = fast || [ [0, 0], [1, 0.75], [2, 1] ];

  var labels = [
      { label : "Price", data: price },
      { label : graph_name + " [5]", data: fast },
      { label : graph_name + " [20]", data: slow },
    ];

  var options = {};

  // clear the HTML otherwise continually calling $.plot extends height
  $(id).html("");
  $.plot($(id), labels, options);
}

function get_work_times(time, half) {
  var times = [];
  half = half || 0;
  times[0] = [time, half];
  times[1] = [time + 2, half];

  if (half == 30) {
    times[2] = [time + 3, 0];
    times[3] = [time + 5, 0];
  }
  else {
    times[2] = [time + 2, 30];
    times[3] = [time + 4, 30];
  }

  return times;
}

function get_break_time(time, half) {
  var times = [];
  if (half == 30) {
    times[0] = [time + 2, 30];
    times[1] = [time + 3, 0];
  }
  else {
    times[0] = [time + 2, 0];
    times[1] = [time + 2, 30];
  }
  return times;
}

function show_manager_schedule(num_managers, schedule) {
  var id = "div#schedule";
  var num_managers = num_managers || 4;

  m4_times = get_work_times(9);
  m4_break = get_break_time(9);

  m3_times = get_work_times(9, 30);
  m3_break = get_break_time(9, 30);

  m2_times = get_work_times(13, 30);
  m2_break = get_break_time(13, 30);

  m1_times = get_work_times(14);
  m1_break = get_break_time(14, 30);

  var schedule = schedule || [
    [Date.UTC(2012, 0, 1, m4_times[0][0], m4_times[0][1]), 4, Date.UTC(2012, 0, 1, m4_times[1][0], m4_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m4_times[2][0], m4_times[2][1]), 4, Date.UTC(2012, 0, 1, m4_times[3][0], m4_times[3][1]), "Working"],

    [Date.UTC(2012, 0, 1, m3_times[0][0], m3_times[0][1]), 3, Date.UTC(2012, 0, 1, m3_times[1][0], m3_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m3_times[2][0], m3_times[2][1]), 3, Date.UTC(2012, 0, 1, m3_times[3][0], m3_times[3][1]), "Working"],

    [Date.UTC(2012, 0, 1, m2_times[0][0], m2_times[0][1]), 2, Date.UTC(2012, 0, 1, m2_times[1][0], m2_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m2_times[2][0], m2_times[2][1]), 2, Date.UTC(2012, 0, 1, m2_times[3][0], m2_times[3][1]), "Working"],

    [Date.UTC(2012, 0, 1, m1_times[0][0], m1_times[0][1]), 1, Date.UTC(2012, 0, 1, m1_times[1][0], m1_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m1_times[1][0], m1_times[1][1]), 1, Date.UTC(2012, 0, 1, m1_times[3][0], m1_times[3][1]), "Working"],
  ];

  var breaks = [
    [Date.UTC(2012, 0, 1, m4_break[0][0], m4_break[0][1]), 4, Date.UTC(2012, 0, 1, m4_break[1][0], m4_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, m3_break[0][0], m3_break[0][1]), 3, Date.UTC(2012, 0, 1, m3_break[1][0], m3_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, m2_break[0][0], m2_break[0][1]), 2, Date.UTC(2012, 0, 1, m2_break[1][0], m2_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, m1_break[0][0], m1_break[0][1]), 1, Date.UTC(2012, 0, 1, m1_break[1][0], m1_break[1][1]), "Break"],
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
