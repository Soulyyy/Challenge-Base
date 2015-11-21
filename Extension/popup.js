var key = 42;
function populate() {
  chrome.runtime.getBackgroundPage(function (bgPage) {
    $(".sites tr").remove();
    for (var i = 0; i < bgPage.blacklist.length; i++) {
      var url = bgPage.blacklist[i];
      $(".sites").append("<tr><td>" + url + "</td><td><a class='remove' href='#' data-id='" + i + "'><span class='glyphicon glyphicon-remove'></a></td></tr>");
    }
    $(".remove").click(function () {
      remove($(this).data("id"));
    });
  });
  updateBalance();
}
populate();

$("#addBtn").click(function () {
  add();
});

$("#newPage").keypress(function (e) {
  if (e.which != 13) return;
  add();
});

function add() {
  chrome.runtime.getBackgroundPage(function (bgPage) {
    if (!$("#newPage").val()) return;
    bgPage.blacklist.push($("#newPage").val());
    bgPage.updated();
    $("#newPage").val("");
    populate();
  });
}

function remove(id) {
  chrome.runtime.getBackgroundPage(function (bgPage) {
    bgPage.blacklist.splice(id, 1);
    bgPage.updated();
    populate();
  });
}

function updateBalance() {
  $.getJSON("http://localhost:8080/rest/balance?key=" + key, function (data) {
    $(".balance").text(data.balance.toFixed(2) + "€");
  });
}
setInterval(updateBalance, 2000);
