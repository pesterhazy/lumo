(require '[lumo.closure :as b])

(b/build "example-src"
  {:output-dir "out"
   :main 'foo.core
   :output-to "out/main.js"
   :optimizations :none})
