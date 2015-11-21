var blacklist;
var key = 42;
var visiting = false;
chrome.storage.sync.get("blacklist", function (ret) {
  if (!ret.blacklist) {
    blacklist = [
      "facebook.com",
      "buzzfeed.com"
    ];
    chrome.storage.sync.set({blacklist: blacklist});
    return;
  }
  blacklist = ret.blacklist;
});
var loggedin = false;
var startBalance;
chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
  if (changeInfo.status != "complete") return;
  checkBlacklist();
});

chrome.tabs.onCreated.addListener(function (tab) {
  checkBlacklist();
});
chrome.tabs.onActivated.addListener(function (activeInfo) {
  chrome.tabs.get(activeInfo.tabId, function (tab) {
    checkBlacklist();
  });
});

function updated() {
  chrome.storage.sync.set({blacklist: blacklist});
}

function updateBalance(site, cb) {
  $.getJSON("http://localhost:8080/rest/action?key=" + key + "&type=BLACKLISTCLICK", function () {
    $.getJSON("http://localhost:8080/rest/balance?key=" + key, function (data) {
      var balance = data.balance.toFixed(2);
      chrome.notifications.create("blacklisted_visiting", {
        iconUrl: "logo.png",
        type: "basic",
        title: "Blacklisted site",
        message: "Visiting a blacklisted site. Balance remaining: " + balance,
        contextMessage: site
      });
      if (cb) cb();
    });
  });
}
var tid;
function checkBlacklist() {
  chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
    var tab = tabs[0];
    var url = tab.url;
    for (var i = 0; i < blacklist.length; i++) {
      var site = blacklist[i];
      if (url.indexOf(site) === -1) continue;
      if (tid) clearInterval(tid);
      $.getJSON("http://localhost:8080/rest/balance?key=" + key, function (data) {
        startBalance = data.balance;
      });
      tid = setInterval(function () {
        updateBalance(site);
      }, 2000);
      console.log("Visiting blacklisted site: " + url);
      visiting = true;
      return;
    }
    if (visiting) {

    }
    visiting = false;
    if (tid) clearInterval(tid);
    chrome.notifications.clear("blacklisted_visiting");
  });
}