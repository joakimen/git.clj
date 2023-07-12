(ns git.config
  (:require [git.sh :refer [sh]]))

(defn get-value [k]
  (sh "git" "config" "--global" "--get" k))

(defn set-value [k v]
  (sh "git" "config" "--global" k v))

(defn user
  [] (get-value "user.name"))

(defn email
  [] (get-value "user.email"))
