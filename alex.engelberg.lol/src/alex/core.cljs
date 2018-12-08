(ns alex.core
  (:require
    [alex.icons :as icons]
    [goog.userAgent :refer [MOBILE]]
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
      (animate-text! [:subtitle] "I write code and I play instruments"))))


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


(defn embed-youtube
  [src]
  [:iframe {:width "100%"
            :height "250"
            :src src
            :frameborder "0"
            :allow "autoplay; encrypted-media; fullscreen"
            :allow-full-screen true}])


(defn embed-soundcloud
  [src]
  [:iframe {:width "100%"
            :height "250"
            :scrolling "no"
            :frameborder "no"
            :allow "autoplay"
            :src src}])


(defn flow-table
  [& stuff]
  (if MOBILE
    (into [:div] stuff)
    (->> stuff
         (partition 2)
         (map (fn [[left right]]
                [:tr
                 [:td {:style {:width "50%"}} left]
                 [:td {:style {:width "50%"}} right]]))
         (into [:table]))))

(defn things-i-did
  []
  [:div
   [:p.center
    "Here are some things I did!"]
   (flow-table
    [:div
     [:p.center "A song!"]
     (embed-soundcloud "https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/507197334&color=%23ff5500&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false&show_teaser=true&visual=true")]
    [:div
     [:p.center "An improv show!"]
     (embed-youtube "https://www.youtube.com/embed/clhJgDNFjac")]
    [:div
     [:p.center "A pointless web app!"]
     [:a
      {:href "http://www.paren.party"}
      [:div
       {:style {:background-color "#001400"
                :height "250px"}}
       [:img {:src "img/paren-party-preview.png"
              :width "100%"}]]]]
    [:div
     [:p.center "An open-source library!"]
     [:a
      {:href "https://github.com/Engelberg/instaparse"}
      [:div
       {:style {:background-color "#f6f8fa"
                :height "250px"}}
       [:img {:src "img/instaparse.png"
              :width "100%"}]]]]
    [:div
     [:p.center "A tech talk!"]
     (embed-youtube "https://www.youtube.com/embed/jlPaby7suOc")]
    [:div
     [:p.center "An improvised musical score!"]
     (embed-youtube "https://www.youtube.com/embed/IEOifkUcShA")])])


(defn page
  []
  [:div.main
   [titles]
   [:p.center
    [:img
     {:src "/img/alex-square.png"
      :style {:height "100px"}}]]
   [social-media]
   [:hr]
   [things-i-did]
   [:hr]
   [:p.center "Thanks for checking out my site and stuff"]])


(reagent/render-component
  [page]
  (. js/document (getElementById "app")))


(animate-titles!)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
