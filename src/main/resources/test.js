
var frame;

var rand = function (lim) { return Math.round(Math.random()*lim); };

function $$(obj, methods) {
  if (methods) $(obj).data('methods', methods);
  return $(obj).data('methods');
}

var message = function(message) {
  $('#message').removeClass('error').html(message);
}

var report = function(message) {
  $('#message').addClass('error').html(message);
}

var onUpdateSuccess = function(data, textStatus) {
  $("<ul></ul>").addClass('commits').appendTo('#world');
  $.each(data.data.sort(function(a, b) {
    return b.timestamp - a.timestamp;
  }), function(i, item) { // each commit
    $('li#commit_' + item.id).remove();
    $("<li></li>").addClass('commit').attr('id', 'commit_' + item.id).appendTo('.commits').append(
      $("<span></span>").addClass('id').html(item.id.substring(0, 7))
    ).append(
      $("<span></span>").addClass('message').html(item.message)
    ).hide();

    var fill_color = "#777";
    var hover_color = Raphael.rgb2hsb(fill_color);
    hover_color.b = .3;

    var x = frame.width - 20 - (i * 20),
        y = (frame.height / 2) - (item.y * 20),
        commit = frame.circle(
          x, y, 4 + rand(6)
        ).attr("fill", fill_color).attr("stroke", "#ddd");

    var lbl = frame.text(x, y + 20, item.id.substring(0, 7))
      .attr({stroke: "none", fill: "#fff"}).hide();

    commit.node.id = item.id;
    // lbl.node. = 'commit-lbl';

    commit[0].onmouseover = function() {
      lbl.show();
      $('li#commit_' + item.id).show();
      commit.attr("fill", Raphael.hsb2rgb(hover_color).hex);
    };
    commit[0].onmouseout = function() {
      lbl.hide();
      $('li#commit_' + item.id).hide();
      commit.attr("fill", fill_color);
    }

    // $.each(item, function(j, k) { // each item in the commit
    //   console.log(j + ": " + k);
    // });
  });
};

var onUpdateError = function(req, textStatus, errorThrown) {
  if ('parsererror' == textStatus) {
    report("The JSON was malformed.");
  } else {
    report(textStatus + " has been occurred in.")
  }
};

var update = function() {
  $.ajax({
    type: "GET",
    url: "babushka.json",
    dataType: "json",
    success: onUpdateSuccess,
    error: onUpdateError
  })
}

var init = function() {
  frame = Raphael('holder', window.innerWidth, window.innerHeight / 2.618);
  $('#world').css({
    position: 'absolute',
    left: 0,
    right: 0,
    top: frame.height,
    height: window.innerHeight - frame.height,
    overflow: 'hidden'
  });
};

$(function(){
  init();
  update();
});
