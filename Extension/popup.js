var key = 42;
function populate() {
  chrome.runtime.getBackgroundPage(function (bgPage) {
    $(".sites tr").remove();
    for (var i = 0; i < bgPage.blacklist.length; i++) {
      var url = bgPage.blacklist[i];
      $(".sites").append("<tr><td>" + url + "</td><td><a class='remove' href='#' data-id='" + i + "'>X</a></td></tr>");
    }
    $(".remove").click(function () {
      remove($(this).data("id"));
    });
  });
  $.getJSON("http://localhost:8080/rest/balance?key=" + key, function (data) {
    console.log(data);
    $(".balance").text(data.balance + "€");
  })
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