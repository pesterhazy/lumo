(ns lumo.io)

(defn resource [path]
  (js/LUMO_RESOURCE path))

(defn spit [filename content]
  (fs.writeFileSync filename content "utf8"))

(defn slurp [file-or-resource]
  (cond
    (string? file-or-resource) (fs.readFileSync file-or-resource "utf8")

    (and (goog/isObject file-or-resource)
         (= (.-type file-or-resource) "path"))
    (fs.readFileSync (.-src file-or-resource) "utf8")

    (and (goog/isObject file-or-resource)
         (= (.-type file-or-resource) "jar"))
    (js/LUMO_READ_SOURCE_JAR file-or-resource)

    :else (do
            (js/console.log file-or-resource (.-constructor file-or-resource) (object? file-or-resource)
         (= (.-type file-or-resource) "jar"))
            (throw (ex-info "should never happen!" {:x file-or-resource})))))
