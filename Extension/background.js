var blacklist;
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
chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
  if (changeInfo.status != "complete") return;
  checkBlacklist(tab.url);
});

chrome.tabs.onCreated.addListener(function (tab) {
  checkBlacklist(tab.url);
});
chrome.tabs.onActivated.addListener(function (activeInfo) {
  chrome.tabs.get(activeInfo.tabId, function (tab) {
    if (!checkBlacklist(tab.url)) {
      chrome.notifications.clear("blacklisted_visiting");
    }
  });
});

function updated() {
  chrome.storage.sync.set({blacklist: blacklist});
}

function checkBlacklist(url) {
  if (!url) return;
  for (var i = 0; i < blacklist.length; i++) {
    var site = blacklist[i];
    if (url.indexOf(site) === -1) continue;
    chrome.notifications.create("blacklisted_visiting", {
      iconUrl: "logo.png",
      type: "basic",
      title: "Blacklisted site",
      message: "Visiting a blacklisted site. Turn back now or get ready to pay!",
      contextMessage: site
    });
    console.log("Visiting blacklisted site: " + url);
    return true;
  }
  return false;
}