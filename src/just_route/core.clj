(ns just-route.core)

(defn router [route-map]
  (fn [{:keys [uri request-method] :as req}]
    (some-> route-map
      (get [request-method uri])
      (#(% req)))))

(defn wrap-routes [handler routes]
  (let [route-fn (router routes)]
    (fn [req]
      (or (route-fn req) (handler req)))))
