(ns lumo.util
  (:require-macros lumo.util)
  (:require [clojure.string :as string]))

(defn distinct-by
  ([f coll]
   (let [step (fn step [xs seen]
                (lazy-seq
                  ((fn [[x :as xs] seen]
                     (when-let [s (seq xs)]
                       (let [v (f x)]
                         (if (contains? seen v)
                           (recur (rest s) seen)
                           (cons x (step (rest s) (conj seen v)))))))
                    xs seen)))]
     (step coll #{}))))

(defn output-directory
  ([opts] (output-directory opts "out"))
  ([opts default]
   {:pre [(or (nil? opts) (map? opts))]}
   (or (:output-dir opts) default)))

(defn debug-prn
  [& args]
  (binding [*print-fn* *print-err-fn*]
    (apply println args)))

(defn directory? [path]
  (try
    (.isDirectory (fs.lstatSync path))
    (catch :default _
      false)))

(defn mkdirs [p]
  (let [target-dir (-> p path.resolve (path.resolve ".."))]
    (reduce (fn [acc d]
              (let [new-path (path.join acc d)]
                (println "dir" d new-path)
                (cond-> new-path
                  (not (fs.existsSync new-path))
                  fs.mkdirSync)
                new-path))
      "/" (rest (string/split target-dir #"/")))))
