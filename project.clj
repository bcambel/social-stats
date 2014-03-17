(defproject social-stats "0.1.0-SNAPSHOT"
  :description "Retrieve stats from Social Platforms"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.taoensso/timbre "3.1.4"]
                 [clj-time "0.6.0"]
                 [cheshire "5.3.1"]
                 [clj-http "0.9.1"]
                 [compojure "1.1.6"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 ]
  :plugins [[lein-ring "0.8.10"]]
  :main ^:skip-aot social-stats.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [
                                   [javax.servlet/servlet-api "2.5"]
                                   [ring-mock "0.1.5"]]}})
