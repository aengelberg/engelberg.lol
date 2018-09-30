(ns alex.core
  (:require
    [alex.icons :as icons]
    [manifold-cljs.deferred :as d]
    [manifold-cljs.executor :as ex]
    [manifold-cljs.time :as mt]
    [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Hi")

;; define your app data so that it doesn't get over-written on reload

(def app-state
  (atom
    {:title ""
     :subtitle ""}))


(defn animate-text!
  [data-key title]
  (d/loop [chars title]
    (when-not (empty? chars)
      (d/chain
        (mt/in (+ 30 (rand-int 30)) (constantly nil))
        (fn next-letter
          []
          (swap! app-state update-in data-key str (first chars))
          (d/recur (rest chars)))))))


(defn animate-titles!
  []
  (d/chain
    (animate-text! [:title] "ALEX ENGELBERG")
    (fn []
      (animate-text! [:subtitle] "Musical Engineer"))))


(defn titles
  []
  [:div.titles
   [:h1.center [:strong (:title @app-state)]]
   [:h3.center [:em (:subtitle @app-state)]]])


(defn social-media
  []
  [:div.social-media.center
   [:a
    {:href "https://twitter.com/@aengelbro"}
    [:img {:src icons/twitter}]]
   " "
   [:a
    {:href "https://github.com/aengelberg"}
    [:img {:src icons/github}]]
   " "
   [:a
    {:href "https://soundcloud.com/aengelberg"}
    [:img {:src icons/soundcloud}]]])


(defn music
  []
  [:div
   [:p.center
    [:img
     {:src "/img/musical-keyboard-emoji.png"
      :style {:height "100px"}}]]
   (for [i (range 10)]
     [:div "TODO"])])


(defn software
  []
  [:div
   [:p.center
    [:img
     {:src "/img/laptop-emoji.png"
      :style {:height "100px"}}]]
   (for [i (range 10)]
     [:div "TODO"])])


(defn page
  []
  [:div.main
   [titles]
   [:p.center
    [:img
     {:src "/img/alex-square.png"
      :style {:height "100px"}}]]
   [social-media]
   [:p "Welcome to my site! I make music and software."]
   [:hr]
   [music]
   [:hr]
   [software]
   [:hr]])


(reagent/render-component
  [page]
  (. js/document (getElementById "app")))


(animate-titles!)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
