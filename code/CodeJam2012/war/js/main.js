// default refresh rate
var refresh = 30;
var refresh_rate_ms = refresh;
var yaxis = { };

$(document).ready(function() {
  show_manager_schedule();

  // set the default refresh rate on the UI
  $("div.modal-body input#data-refresh").val(refresh_rate_ms);

  // go swaps the primary visuals, swaps button enabling/disabling and starts
  // the data mining process
  $("button#go").click(function() {
    // swap the hidden status of graphs & schedule... only at the beginning
    if ($("div#graphs").hasClass("hidden")) {
      $("div#schedule").toggleClass("hidden");
      $("div#graphs").toggleClass("hidden");

      $("button#go").toggleClass("hidden");
    }
    start_data_mining();
  });

  // access link "mset?report", log the results
  $("button#report").click(function() {
    $.ajax( {
        url : "codejam2012/mset?report",
        type : 'GET',
        error : function (error) {
          console.log("Error accessing mset?report!");
          console.log(error);
        },
        success : function (results) {
          console.log("Successfully got report:\n" + JSON.stringify(results));
          json =
          {
            "team" : "Flying monkeys",
            "destination" : "mcgillcodejam2012@gmail.com",
            "transactions" : [
              {
                "time" : "8004",
                "type" : "buy",
                "price" : 120,
                "manager" : "Manager1",
                "strategy" : "EMA"
              },
              {
                "time" : "9589",
                "type" : "sell",
                "price" : 122,
                "manager" : "Manager2",
                "strategy" : "LWMA"
              },
              {
                "time" : "16542",
                "type" : "buy",
                "price" : 118,
                "manager" : "Manager1",
                "strategy" : "TMA"
              }
            ]
          };
          update_table(json); // TODO change to results
          if ($("button#report").html() == "Report") {
            console.log("sending to silanis");
            send_data_to_silanis(results);
          }

          if ($("div#report").hasClass("hidden")) {
            $("button#report").html("Graphs");
          } else {
            $("button#report").html("Report");
          }
          $("div#report").toggleClass("hidden");
          $("div#graphs").toggleClass("hidden");
        }
    } );
  });

  function update_table(json) {
    $("table#reportTable").find('tbody').html("");
	  for (var r in json.transactions) {
		  $("table#reportTable").find('tbody')
          .append($('<tr>')
		        .append($('<td>')
		          .append(json.transactions[r].time)
		        ).append($('<td>')
              .append(json.transactions[r].type)
            ).append($('<td>')
              .append(json.transactions[r].price)
            ).append($('<td>')
              .append(json.transactions[r].manager)
            ).append($('<td>')
              .append(json.transactions[r].strategy)
            )
          );
	  }
  }

  function send_data_to_silanis(results) {
    $.ajax( {
        type : 'POST',
        data : results,
        crossDomain : true,
        headers : { "Authentication" : "Basic Y29kZWphbTpBRkxpdGw0TEEyQWQx",
                    "Content-Type" : "application/json" },
        url : "https://stage-api.e-signlive.com/aws/rest/services/codejam",
        error : function (error) {
          console.log("Error sending results to Silanis!");
          console.log(error);
        },
        successs : function (results) {
          show_ceremony_id(results);
        }
    } );
  }

  function show_ceremony_id(json) {
    $("div#ceremony").html("Ceremony Id: " + json.ceremonyId);
    $("div#ceremony").toggleClass("hidden");
  }

  // save variables from graph settings, show a "success" then fade out
  $("button#saveModal").click(function() {
    refresh_rate_ms = $("div.modal-body input#data-refresh").val() || refresh;
    ymax = $("div.modal-body input#y-max").val() || null;
    ymin = $("div.modal-body input#y-min").val() || null;
    yaxis = { "max" : ymax, "min" : ymin };
    $("div#savealert").show();
    $("div#savealert").text("Successfully saved!");
    setTimeout(function() {
      $("div#savealert").fadeOut('slow');
    }, 2000);
  });
});

function start_data_mining() {
  $.ajax(
    {
      url : "codejam2012/mset?go",
      type : 'GET',
      error : function (error) {
        console.log("Error accessing mset?go!");
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
      error : function (error) {
        console.log("Error accessing mset?data");
        console.log(error)
      },
      success : function (results) {
        var price = results.price;
        plot_graph("div#sma-graph", price, results.smaSlow, results.smaFast);
        plot_graph("div#lwma-graph", price, results.lwmaSlow, results.lwmaFast);
        plot_graph("div#ema-graph", price, results.emaSlow, results.emaFast);
        plot_graph("div#tma-graph", price, results.tmaSlow, results.tmaFast);
        if (results.finished == true) {
          console.log("Received 'finish' signal.");
          $("button#report").toggleClass("hidden");
          $("a#downloadReport").toggleClass("hidden");
          return;
        }
        setTimeout(update_data, refresh_rate_ms);
      }
    }
  );
}

function plot_graph(id, price, slow, fast) {
  var graph_name = id.substring(id.indexOf("#") + 1, id.indexOf("-")).toUpperCase();

  price = price || [];
  slow = slow || [];
  fast = fast || [];

  var labels = [
      { label : "Price", data: price },
      { label : graph_name + " [5]", data: fast },
      { label : graph_name + " [20]", data: slow },
    ];

  var options = { "yaxis" : yaxis };

  // clear the HTML otherwise continually calling $.plot extends height
  $(id).html("");
  $.plot($(id), labels, options);
}

// get work times based on input time... **THIS ASSUMES FULL DAY**
// namely, if you input 9 AM, this function will return the following:
// [ [9,0], [11,0], [11,30], [13,30] ]
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

// get break time based on when you start...
// namely, if you input 9 AM, this function will return the following:
// [ [11,00] ]
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

