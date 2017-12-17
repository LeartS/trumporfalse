(ns trumporfalse.app
  (:require [rum.core :as rum]))


(defrecord User [name handle avatar])
(defrecord Tweet [user text context real])

(defn answer [answer]
  (js/alert answer))

(defn get-button-attrs [correct-answer button-answer]
  {:on-click #(answer button-answer)
   :class (if (= correct-answer button-answer) "correct" "wrong")})


(rum/defc tweet-header [{:keys [name handle avatar]} user]
  [:.content [:.permalink-header [[:img.avatar {:src avatar}]
                                  [:span.name [[:strong name]
                                               [:span]
                                               [:span.UserBadges [:span.Icon.Icon--verified]]]]
                                  [:span.username handle]]]])

(rum/defc tweet-body [text]
  [:.js-tweet-text-container [:p.js-tweet-text.tweet-text text]])

(rum/defc main-tweet [{:keys [user text]} tweet]
  [:div.tweet-container [:div.tweet [(tweet-header user)
                                     (tweet-body text)]]])

(rum/defc question-box [correct-answer given-answer]
  [:.question
   [:h1 "Did Donald Trump really tweet this?"]
   [:button.answer.yes (get-button-attrs correct-answer true) "Yes, it's probably real"]
   [:button.answer.no (get-button-attrs correct-answer false) "No, it's probably fake"]])

(rum/defc root-component [{correct :real :as tweet} tweet answer]
  [:.main [[(main-tweet tweet)]
           [(question-box correct answer)]]])

(def great
  (Tweet.
   (User.
    "Donald J. Trump"
    "@realDonaldTrump"
    "https://pbs.twimg.com/profile_images/874276197357596672/kUuht00m_bigger.jpg"
    )
   "Great numbers on Stocks and the Economy. If we get Tax Cuts and Reform, we'll really see some great results!"
   "Boh"
   true))

(defn init []
  (rum/mount (root-component great false) (.getElementById js/document "container")))
