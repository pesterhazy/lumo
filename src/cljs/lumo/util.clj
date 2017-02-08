(ns lumo.util)

(defmacro measure
  "Like cljs.core/time but toggleable and takes a message string."
  {:added "1.0"}
  ([msg expr] `(measure true ~msg ~expr))
  ([enable msg expr]
    `(if ~enable
       (let [start# (cljs.core/system-time)
             ret# ~expr]
         (debug-prn (str ~msg ", elapsed time:") (/ (double (- (cljs.core/system-time) start#)) 1000000.0) "msecs")
         ret#)
       ~expr)))
