// Dark mode toggle for Creative Scala Theme
(function() {
  var storageKey = 'theme';
  var classNameDark = 'dark';
  var html = document.documentElement;

  function setTheme(dark) {
    if (dark) {
      html.classList.add(classNameDark);
      try { localStorage.setItem(storageKey, 'dark'); } catch(e) {}
    } else {
      html.classList.remove(classNameDark);
      try { localStorage.setItem(storageKey, 'light'); } catch(e) {}
    }
  }

  function getStoredTheme() {
    try {
      return localStorage.getItem(storageKey);
    } catch(e) {
      return null;
    }
  }

  function getPreferredTheme() {
    var stored = getStoredTheme();
    if (stored === 'dark') return 'dark';
    if (stored === 'light') return 'light';
    var systemDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    return systemDark ? 'dark' : 'light';
  }

  // Global toggle function
  window.toggleTheme = function() {
    var isDark = html.classList.contains(classNameDark);
    setTheme(!isDark);
  };

  // Apply theme immediately
  setTheme(getPreferredTheme() === 'dark');
})();
