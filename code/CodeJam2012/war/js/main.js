$(document).ready(function() {
  show_manager_schedule();

  $("button#go").click(function() {
    // swap the hidden status of graphs & schedule... only at the beginning
    if ($("div#graphs").hasClass("hidden")) {
      $("div#schedule").toggleClass("hidden");
      $("div#graphs").toggleClass("hidden");

      $("button#go").toggleClass("disabled");
      $("button#report").toggleClass("disabled");

      $("button#go").off("click");

      $("button#go").click(function() { });
      $("button#report").click(function() {
        alert("Shiver me timber!");
      });
    }
    start_data_mining();

    plot_graph("div#sma-graph");
    plot_graph("div#lwma-graph");
    plot_graph("div#ema-graph");
    plot_graph("div#tma-graph");
  });
});

function start_data_mining() {
  $.ajax(
    {
      url : "codejam2012/mset?go",
      type : 'GET',
      error : function (error) {
        console.log(error);
      },
      success : function (results) {
        update_data();
      }
  });
}

function update_data() {
  $.ajax(
    {
      url : "codejam2012/mset?data",
      type : 'GET',
      error : function (error)
        { console.log(error) },
      success : function (results) {
        var price = results.price;
        if (price.length == 0) {
          console.log("early return!");
          console.log(results);

          return;
        }
        plot_graph("div#sma-graph", price, results.smaSlow, results.smaFast);
        plot_graph("div#lwma-graph", price, results.lwmaSlow, results.lwmaFast);
        plot_graph("div#ema-graph", price, results.emaSlow, results.emaFast);
        plot_graph("div#tma-graph", price, results.tmaSlow, results.tmaFast);
        setTimeout(update_data, 1000);
      }
    }
  );
}

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

  var options = {  };

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
  var num_managers = num_managers || 8;

  m6_times = get_work_times(9);
  m6_break = get_break_time(9);

  m5_times = get_work_times(9);
  m5_break = get_break_time(9);

  m2_times = get_work_times(13, 30);
  m2_break = get_break_time(13, 30);

  m1_times = get_work_times(13, 30);
  m1_break = get_break_time(13, 30);

  var schedule = schedule || [
    [Date.UTC(2012, 0, 1, 15, 30), 8, Date.UTC(2012, 0, 1, 17, 30), "Working"],
    [Date.UTC(2012, 0, 1, 15, 30), 7, Date.UTC(2012, 0, 1, 17, 30), "Working"],

    [Date.UTC(2012, 0, 1, m6_times[0][0], m6_times[0][1]), 1, Date.UTC(2012, 0, 1, m6_times[1][0], m6_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m6_times[2][0], m6_times[2][1]), 1, Date.UTC(2012, 0, 1, m6_times[3][0], m6_times[3][1]), "Working"],

    [Date.UTC(2012, 0, 1, m5_times[0][0], m5_times[0][1]), 2, Date.UTC(2012, 0, 1, m5_times[1][0], m5_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m5_times[2][0], m5_times[2][1]), 2, Date.UTC(2012, 0, 1, m5_times[3][0], m5_times[3][1]), "Working"],

    [Date.UTC(2012, 0, 1, 11, 0), 4, Date.UTC(2012, 0, 1, 13), "Working"],

    [Date.UTC(2012, 0, 1, 11, 0), 3, Date.UTC(2012, 0, 1, 13), "Working"],

    [Date.UTC(2012, 0, 1, m2_times[0][0], m2_times[0][1]), 5, Date.UTC(2012, 0, 1, m2_times[1][0], m2_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m2_times[2][0], m2_times[2][1]), 5, Date.UTC(2012, 0, 1, m2_times[3][0], m2_times[3][1]), "Working"],

    [Date.UTC(2012, 0, 1, m1_times[0][0], m1_times[0][1]), 6, Date.UTC(2012, 0, 1, m1_times[1][0], m1_times[1][1]), "Working"],
    [Date.UTC(2012, 0, 1, m1_times[1][0], m1_times[1][1]), 6, Date.UTC(2012, 0, 1, m1_times[3][0], m1_times[3][1]), "Working"],
  ];

  var breaks = [
    [Date.UTC(2012, 0, 1, m6_break[0][0], m6_break[0][1]), 1, Date.UTC(2012, 0, 1, m6_break[1][0], m6_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, m5_break[0][0], m5_break[0][1]), 2, Date.UTC(2012, 0, 1, m5_break[1][0], m5_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, 13), 3, Date.UTC(2012, 0, 1, 13, 30), "Break"],
    [Date.UTC(2012, 0, 1, 13), 4, Date.UTC(2012, 0, 1, 13, 30), "Break"],
    [Date.UTC(2012, 0, 1, m2_break[0][0], m2_break[0][1]), 5, Date.UTC(2012, 0, 1, m2_break[1][0], m2_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, m1_break[0][0], m1_break[0][1]), 6, Date.UTC(2012, 0, 1, m1_break[1][0], m1_break[1][1]), "Break"],
    [Date.UTC(2012, 0, 1, 17, 30), 7, Date.UTC(2012, 0, 1, 18), "Break"],
    [Date.UTC(2012, 0, 1, 17, 30), 8, Date.UTC(2012, 0, 1, 18), "Break"],
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
