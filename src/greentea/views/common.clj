(ns greentea.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]
        [hiccup.element]))

(defpartial global [title]
  [:head
    [:title (str "CoGe Analytics - " title)]
    (include-css
      "/analytics/css/reset.css"
      "/analytics/css/chosen.css"
      "/analytics/css/style.css")
    (include-js
      "//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"
      "//ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js")])

(defpartial graph-nav []
  [:div#graph-nav
    [:span.nav]
    [:a#day.nav
      {:onclick "toggleGraphs(this)"
      :href "#"}
      [:li.nav "Day"]]
    [:span.nav]
    [:a#accumulated.nav
      {:onclick "toggleGraphs(this)"
      :href "#"}
      [:li.nav "Accumulated"]]
    [:span.nav]])

(defpartial wrapper [& content]
  [:div#wrapper
    (image {:id "logo" :alt "CoGe Logo"} "/analytics/img/logo.png")
    [:br]
    content]
  [:br])

(defpartial header []
  [:div#header])

(defpartial footer []
  [:div#footer])

(defpartial page [& content]
  (header)
  (wrapper content)
  (footer))

(defpartial graph-page [& content]
  (html5
    [:head
      (global "Graph - by Day")
      (include-js "/analytics/js/lib/spin.min.js"
                  "/analytics/js/lib/chosen.jquery.min.js"
                  "/analytics/js/graph-script.js"
                  "/analytics/js/spinner.js")]
    [:body
      {:onload "createChart()"}
      (page
        (graph-nav)
        [:div#chart]
        [:div.select
          [:input#rb1
            {:type "radio" :name "dayGroup" :onClick "setPanSelect()"}
              "Select&nbsp&nbsp"]
          [:input
            {:type "radio" :checked "true" :name "dayGroup" :onClick "setPanSelect()"}
              "Pan"]]
        content
        [:div#loader]
        [:h5.right "Data Starting from: " [:span#firstDate]]
        [:br][:br]
        [:h3
          [:select#type.chzn-select
            {:onchange "reloadChart()"
              :data-placeholder "Choose a CoGe Page"
              :style "width: 300px;" }
            [:option {:data ""} ""]
            [:option {:data ""} "Main 4 Jobs"]
            [:option {:data "synmap"} "SynMap"]
            [:option {:data "synfind"} "SynFind"]
            [:option {:data "gevo"} "GEvo"]
            [:option {:data "cogeblast"} "CoGeBlast"]
            [:option {:data "featview"} "FeatView"]
            [:option {:data "organismview"} "OrganismView"]
            [:option {:data "user"} "User Additions"]]]
        [:br]
        [:h3 "Search:" [:input#search
          {:onchange "searchChart()"}]])
      (javascript-tag "$(document).ready(function(){
                        $('.chzn-select').chosen({
                          no_results_text: 'No results matched'
                        });
                      })")
      (include-js "/analytics/js/lib/amcharts.js"
                  "/analytics/js/lib/underscore-min.js")]))

(defpartial day-page []
  (javascript-tag "$('#day').addClass('active')"))

(defpartial raw-page [& content]
  (html5
    [:head
      (global "Raw Data")]
    [:body
      (page content)]))
