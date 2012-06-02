(defproject projected "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [noir "1.3.0-beta7"
                  :exclusions [org.clojure/clojure]]
                 [korma "0.3.0-beta7"]
                 [lobos "1.0.0-SNAPSHOT"]
                 [postgresql "9.1-901.jdbc4"]
                 [crate "0.2.0-alpha2"]
                 [jayq "0.1.0-alpha4"]
                 [fetch "0.1.0-alpha2"]
                 [waltz "0.1.0-alpha1"]
                 [lamina "0.5.0-alpha1"]]
  :profiles {:dev {:resource-paths ["resources"
                                    "dummy-data"]
                   :dependencies [[speclj "2.1.2"]]}}
  :test-paths ["spec/"]
  :plugins [[lein-cljsbuild "0.1.8"]]
  :source-paths ["src/clj" "src/cljs"]
  :cljsbuild {:builds
              [{:source-path "src/cljs"
                :compiler {:output-to "resources/public/javascripts/main-debug.js"
                           :optimizations :whitespace
                           :pretty-print true}}
               {:source-path "src/cljs"
                :jar true
                :compiler {:output-to "resources/public/javascripts/main.js"
                           :optimizations :advanced
                           :externs ["externs/jquery-1.7.js"
                                     "externs/jquery-ui-1.8.js"
                                     "externs/bootstrap-2.0.js"]
                           :pretty-print false}}]})
