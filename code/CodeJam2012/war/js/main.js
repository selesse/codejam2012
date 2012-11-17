$(document).ready(function() {
  $("button#go").click(function() {
    plot_graph("div#sma-graph");
    plot_graph("div#lwma-graph");
    plot_graph("div#ema-graph");
    plot_graph("div#tma-graph");
  });
});

function plot_graph(id) {
  // clear the HTML otherwise continually calling $.plot extends height
  $(id).html("");
  $.plot($(id),
  [ { label : "Price", data : [[0, 0], [1, 1]] } ],
  { yaxis: { max: 1 } } );
}
