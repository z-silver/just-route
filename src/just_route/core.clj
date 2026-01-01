(ns just-route.core
  (:require [clojure.string :as str]))

(defn normalize-uri [uri]
  (-> uri
      (str/replace #"/+" "/")
      (str/replace #"/$" "")
      (str/replace #"^$" "/")))

(defn router [route-map]
  (fn [{:keys [uri request-method] :as req}]
    (some-> route-map
      (get [request-method (normalize-uri uri)])
      (#(% req)))))

(defn wrap-routes [handler routes]
  (let [route-fn (router routes)]
    (fn [req]
      (or (route-fn req) (handler req)))))
