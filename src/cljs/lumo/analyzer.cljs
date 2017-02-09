(ns lumo.analyzer
  (:require [lumo.util :as util]))

(defn gen-user-ns [src]
  (let [full-name (str src)
        name (.substring full-name
               (inc (.lastIndexOf full-name "/"))
               (.lastIndexOf full-name "."))]
    (symbol
      (apply str
        "cljs.user." name
        (take 7 (util/content-sha full-name))))))
