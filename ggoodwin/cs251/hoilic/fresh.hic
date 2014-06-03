(def fresh 
  (bind count 0
    (fun (s)
      (str+ (str+ s ".")
            (toString (<- count (+ count 1)))))))

