(ns git.sh
  (:require [babashka.process :as p]
            [clojure.string :as str]))

(defn sh [& args]
  (let [{:keys [exit out err]} (apply p/sh args)]
    (when-not (zero? exit)
      (throw (ex-info err {:babashka/exit 1})))
    (str/trim out)))
