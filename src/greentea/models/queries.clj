(ns greentea.models.queries
  (:use [korma.core]
        [greentea.db]))

(defn coge-log-user-data []
"This is to test json output from the coge db"
  (select log
    (fields :user_id)
    (group :user_id)))

(defn coge-log-comment-data []
"This is to test json output from the coge db"
  (select log
    (fields :comment)
    (where
      (not= :comment "nil"))))

(defn coge-log-all-data []
"Returns all log data from the CoGe database"
  (select log
    (group :link)
    (where
      (> :time 0))))

(defn coge-log-timeseries-data []
"Returns all data from the CoGe database useful
for representing unique jobs ran over time."
  (select log
    (group :link)
    (fields :time :log_id :page)
    (order :time)
    (where
      (> :time 0))))