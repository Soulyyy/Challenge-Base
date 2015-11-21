var blacklist = [
  "facebook.com",
  "buzzfeed.com"
];
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