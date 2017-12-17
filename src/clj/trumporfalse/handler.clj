(ns trumporfalse.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer :all]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json-response :refer [wrap-json-response]]
            [yaml.core :as yaml]))

(def tweets
  (yaml/from-file "resources/private/tweets.yml"))


(defroutes app-routes
  (GET "/" []
       (content-type
        (resource-response "index.html" {:root "public"})
        "text/html")))

(defroutes api-routes
  (context "/api" []
           (GET "/tweet/:tweet" [tweet]
                (response (get tweets tweet) ))))

(def app
  (routes
   (wrap-json-response api-routes)
   (wrap-defaults app-routes (assoc-in site-defaults [:static :resources] "public"))
   (route/not-found "NON ESISTE")))
