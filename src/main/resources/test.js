window.onload = function () {
  // Creates canvas 320 × 200 at 10, 50
  var paper = Raphael(0, 0, 800, 600);
  // Creates circle at x = 50, y = 40, with radius 10
  var circle = paper.circle(50, 40, 10);
  // Sets the fill attribute of the circle to red (#f00)
  circle.attr("fill", "#777");
  // Sets the stroke attribute of the circle to white (#fff)
  circle.attr("stroke", "#ddd");
};
