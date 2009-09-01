
Raphael.fn.connection = function (obj1, obj2, line, bg) {
    if (obj1.line && obj1.from && obj1.to) {
        line = obj1;
        obj1 = line.from;
        obj2 = line.to;
    }
    var bb1 = obj1.getBBox();
    var bb2 = obj2.getBBox();
    var p = [{x: bb1.x + bb1.width / 2, y: bb1.y - 1},
        {x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1},
        {x: bb1.x - 1, y: bb1.y + bb1.height / 2},
        {x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2},
        {x: bb2.x + bb2.width / 2, y: bb2.y - 1},
        {x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1},
        {x: bb2.x - 1, y: bb2.y + bb2.height / 2},
        {x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2}];
    var d = {}, dis = [];
    for (var i = 0; i < 4; i++) {
        for (var j = 4; j < 8; j++) {
            var dx = Math.abs(p[i].x - p[j].x),
                dy = Math.abs(p[i].y - p[j].y);
            if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
                dis.push(dx + dy);
                d[dis[dis.length - 1]] = [i, j];
            }
        }
    }
    if (dis.length == 0) {
        var res = [0, 4];
    } else {
        var res = d[Math.min.apply(Math, dis)];
    }
    var x1 = p[res[0]].x,
        y1 = p[res[0]].y,
        x4 = p[res[1]].x,
        y4 = p[res[1]].y,
        dx = Math.max(Math.abs(x1 - x4) / 2, 10),
        dy = Math.max(Math.abs(y1 - y4) / 2, 10),
        x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
        y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
        x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
        y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
    var path = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
    if (line && line.line) {
        line.bg && line.bg.attr({path: path});
        line.line.attr({path: path});
    } else {
        var color = typeof line == "string" ? line : "#000";
        return {
            bg: bg && bg.split && this.path({stroke: bg.split("|")[0], fill: "none", "stroke-width": bg.split("|")[1] || 3}, path),
            line: this.path({stroke: color, fill: "none"}, path),
            from: obj1,
            to: obj2
        };
    }
};

var frame;

var commits = {};

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
      $("<span></span>").addClass('message').text(item.message)
    );

    var x_commit_sep = 30,
        y_commit_sep = 30,
        fill_color = "#777";

    var hover_color = Raphael.rgb2hsb(fill_color);
    hover_color.b = .3;

    commits[item.id] = frame.circle(
      frame.width - 20 - (i * x_commit_sep), // x
      (frame.height / 2) - (item.y * y_commit_sep), // y
      4 + ((item.size - 200) / 30) // radius
    ).attr("fill", fill_color).attr("stroke", "#ddd");

    var lbl = frame.text(commits[item.id].attr('cx'), commits[item.id].attr('cy') + 20, item.id.substring(0, 7))
      .attr({stroke: "none", fill: "#fff"}).hide();


    commits[item.id].node.id = 'commit_blob_' + item.id;
    // lbl.node. = 'commit-lbl';

    commits[item.id][0].onmouseover = function() {
      lbl.show();
      commits[item.id].attr("fill", Raphael.hsb2rgb(hover_color).hex);
    };
    commits[item.id][0].onmouseout = function() {
      lbl.hide();
      commits[item.id].attr("fill", fill_color);
    }
  });

  $.each(data.data, function(i, item) {
    $.each(item.parents, function(j, parent) {
      frame.connection(
        commits[item.id],
        commits[parent],
      '#fff');
    });
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
    url: document.location.pathname + "initial",
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
