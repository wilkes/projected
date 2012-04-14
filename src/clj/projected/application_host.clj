(ns projected.application-host
  (:use [noir.core :only (defpartial)]
        [hiccup.page :only (html5
                            include-css
                            include-js)]
        [hiccup.element :only (javascript-tag)]))

(defn nav-link [li-class href txt]
  [:li {:class li-class} [:a {:href href} txt]])

(defn topbar [environment]
  [:div.navbar.navbar-fixed-top
   [:div.navbar-inner
    [:div.container-fluid
     [:a.brand {:href "/"} "Projected"]
     [:ul#navigation.nav
      (if (= :development environment)
        (nav-link "development" "/development" "Development"))
      (if (= :development environment)
        (nav-link "production" "/production" "Production"))]]]])

(defpartial dev-js []
  (include-js "javascripts/main-debug.js")
  (javascript-tag "projected.core.main();projected.core.repl();"))

(defpartial prod-js []
  (include-js "javascripts/main.js")
  (javascript-tag "projected.core.main();"))

(defpartial layout [environment]
  (html5
   [:head
    [:title "Projected"]
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "Content-Type" :content "text/html; charset=UTF-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=7;IE=8;IE=edge"}]
    "<!--[if lt IE 9]>
        <script src=\"http://html5shiv.googlecode.com/svn/trunk/html5.js\"></script>
     <![endif]-->"
    (include-css "/css/smoothness/jquery-ui-1.8.17.custom.css"
                    "/css/bootstrap.css"
                    "/css/projected.css"
                    "/css/bootstrap-responsive.css")]
   [:body (topbar environment)
    [:div.container-fluid
     [:div.row-fluid
      [:div#side-bar.span2]
      [:div#content.span10]]]
    (include-js "js/jquery-1.7.1.min.js"
                   "js/jquery-ui-1.8.17.custom.min.js"
                   "js/bootstrap.min.js")
    (if (= :development environment) (dev-js) (prod-js))]))
