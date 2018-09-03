(ns alex.core
  (:require
    [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Hi")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state
  (atom {:title "Welcome to Alex's site"
         :text "Cool"}))


(defn hello-world []
  [:div
   [:h1 (:title @app-state)]
   [:p (:text @app-state)]])


(reagent/render-component
  [hello-world]
  (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
