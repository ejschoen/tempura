(defproject com.taoensso/tempura "1.5.0"
  :author "Peter Taoussanis <https://www.taoensso.com>"
  :description "Pure Clojure/Script i18n translations library"
  :url "https://github.com/ptaoussanis/tempura"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars
  {*warn-on-reflection* true
   *assert*             true
   *unchecked-math*     false #_:warn-on-boxed}

  :dependencies
  [[com.taoensso/encore "3.31.0"]]

  :plugins
  [[lein-pprint    "1.3.2"]
   [lein-ancient   "0.7.0"]
   [lein-codox     "0.10.8"]
   [lein-cljsbuild "1.1.8"]]

  :profiles
  {;; :default [:base :system :user :provided :dev]
   :server-jvm {:jvm-opts ^:replace ["-server"]}
   :provided {:dependencies [[org.clojure/clojurescript "1.11.60"]
                             [org.clojure/clojure "1.11.1"]]}
   :c1.11    {:dependencies [[org.clojure/clojure "1.11.1"]]}
   :c1.10    {:dependencies [[org.clojure/clojure "1.10.3"]]}
   :c1.9     {:dependencies [[org.clojure/clojure "1.9.0"]]}

   :depr     {:jvm-opts ["-Dtaoensso.elide-deprecated=true"]}
   :dev      [:c1.11 :test :server-jvm :depr]
   :test     {:dependencies [[org.clojure/test.check "1.1.1"]]}}

  :test-paths ["src" "test"]

  :cljsbuild
  {:test-commands {"node" ["node" "target/test.js"]}
   :builds
   [{:id :main
     :source-paths ["src"]
     :compiler
     {:output-to "target/main.js"
      :optimizations :advanced}}

    {:id :test
     :source-paths ["src" "test"]
     :compiler
     {:output-to "target/test.js"
      :target :nodejs
      :optimizations :simple}}]}

  :aliases
  {"start-dev"  ["with-profile" "+dev" "repl" ":headless"]
   "deploy-lib" ["do" ["build-once"] ["deploy" "clojars"] ["install"]]
   "build-once" ["do" ["clean"] "cljsbuild" "once"]

   "test-cljs"  ["with-profile" "+test" "cljsbuild" "test"]
   "test-all"
   ["do" ["clean"]
    "with-profile" "+c1.11:+c1.10:+c1.9" "test,"
    "test-cljs"]}
  
  :repositories
  {"sonatype-oss-public"
   "https://oss.sonatype.org/content/groups/public/"})
