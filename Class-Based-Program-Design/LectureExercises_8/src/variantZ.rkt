;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname variantZ) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; # Variant A
;;
;; Suppose you are given a list of integers. Your task is to determine if this list contains:
;;
;; - A number that is even
;; - A number that is positive and odd
;; - A number between 5 and 10, inclusive
;;
;; The order in which you find numbers in the list satisfying these requirements does not matter.
;; The list could have many more numbers than you need.
;; Any number in the list may satisfy multiple requirements.
;; For example, the list (in Racket notation) (list 6 5) satisfies all three requirements, while the list (list 4 3) does not.
;;
;; > Do Now!
;; > Design a method on lists of integers to check whether the list satisfies these criteria.
;; > Hint: what information do you need to propagate down the recursive calls as you process the list?


;; Number -> Boolean
;; produce true if the given number is even; false otherwise

(check-expect (is-even? 0)  true)
(check-expect (is-even? 1) false)
(check-expect (is-even? 2)  true)

;; (define (is-even n) false) ; stub

(define (is-even? n) (even? n))


;; Number -> Boolean
;; produce true if the given number is odd and positive; false otherwise

(check-expect (is-odd-and-positive? 0)  false)
(check-expect (is-odd-and-positive? -1) false)
(check-expect (is-odd-and-positive? 1)   true)

;; (define (is-odd-and-positive n) false) ; stub

(define (is-odd-and-positive? n) (and (odd? n) (> n 0)))

;; Number -> Boolean
;; produce true if the given number is [5,10]; false otherwise

(check-expect (is-between-5-and-10?  4) false)
(check-expect (is-between-5-and-10?  5)  true)
(check-expect (is-between-5-and-10?  8)  true)
(check-expect (is-between-5-and-10? 10)  true)
(check-expect (is-between-5-and-10? 11) false)

;; (define (is-odd-and-positive n) false) ; stub

(define (is-between-5-and-10? n) (and (>= n 5) (<= n 10)))


;; (listof Number) -> Boolean
;; consume a listof Number produce true if the list satisfy the following requirments:
;; at least 1 number is even
;; at least 1 number is odd and positive
;; at least 1 number is between 5 and 10
;; each number can satisfy more than one requirements

(check-expect (variantA empty) false)
(check-expect (variantA (list 6 5))    true)
(check-expect (variantA (list 4 3))   false)
(check-expect (variantA (list 1 2 3)) false)
(check-expect (variantA (list 1 2 5))  true)
(check-expect (variantA (list -1 2 5)) true)
(check-expect (variantA (list -1 2 5 43)) true)


;; (define (variantA lon) false) ; stub

(define (variantA lon0)
  ;; acc-even             true if one of the number in the list is even;             false otherwise. boolean  - context acc 
  ;; acc-odd-and-positive true if one of the number in the list is odd and positive; false otherwise. boolean  - context acc
  ;; acc-btw-5-and-10     true if one of the number in the list is between 5 and 10; false otherwise. boolean  - context acc

  (local
    [
     (define (satisfy? acc-e acc-op acc-btw)
       (and acc-e acc-op acc-btw))

     (define (fn-for-n n lon acc-e acc-op acc-btw) 
       (if (satisfy? acc-e acc-op acc-btw)  
           true
           (fn-for-lon lon
                       (or acc-e               (is-even? n))
                       (or acc-op  (is-odd-and-positive? n))
                       (or acc-btw (is-between-5-and-10? n))))) 

     (define (fn-for-lon lon acc-e acc-op acc-btw)
       (cond
         [(empty? lon) (satisfy? acc-e acc-op acc-btw)]
         [else (fn-for-n (first lon) (rest lon) acc-e acc-op acc-btw)]))]

    (fn-for-lon lon0 false false false)))


;; # Variant B
;;
;; Again, the list must contain numbers satisfying the three requirements above.
;; Again, order does not matter.
;; This time, a given number in the list may only be used to satisfy a single requirement;
;; however, duplicate numbers are permitted to satisfy multiple requirements.
;; So, (list 6 5) does not meet all the criteria for this variant, but (list 6 5 6) does.
;;
;; > Do Now!
;; > Design a new method on lists of integers to check for this stricter property.
;; > How does your design differ from Variant A?


;; (listof Number) -> Boolean
;; consume a listof Number produce true if the list satisfy the following requirments:
;; at least 1 number is even
;; at least 1 number is odd and positive
;; at least 1 number is between 5 and 10
;; each number can satisfy only one requirement

(check-expect (variantB empty)           false)
(check-expect (variantB (list 6 5))      false)
(check-expect (variantB (list 6 5 6))     true)
(check-expect (variantB (list 1 2 3))    false)
(check-expect (variantB (list 1 2 3))    false)
(check-expect (variantB (list 1 2 5))     true)
(check-expect (variantB (list -1 2 5))    false)
(check-expect (variantB (list -1 2 5 43)) true)

;; (define (variantB lon) false) ; stub

(define (variantB lon0)
  ;; acc-even             true if one of the number in the list is even;             false otherwise. boolean  - context acc 
  ;; acc-odd-and-positive true if one of the number in the list is odd and positive; false otherwise. boolean  - context acc
  ;; acc-btw-5-and-10     true if one of the number in the list is between 5 and 10; false otherwise. boolean  - context acc

  (local
    [
     (define (satisfy? acc-e acc-op acc-btw)
       (and 
        (not (false?   acc-e))
        (not (false?  acc-op))
        (not (false? acc-btw))))

     (define (fn-for-n n lon acc-e acc-op acc-btw) 
       (if (satisfy? acc-e acc-op acc-btw)  
           true
           (or
            (fn-for-lon lon 
                        (or (not (false? acc-e)) (is-even? n))
                        acc-op 
                        acc-btw)

            (fn-for-lon lon
                        acc-e 
                        (or (not (false? acc-op)) (is-odd-and-positive? n)) 
                        acc-btw)
            (fn-for-lon lon
                        acc-e
                        acc-op 
                        (or (not (false? acc-btw)) (is-between-5-and-10? n)))
            ))) 

     (define (fn-for-lon lon acc-e acc-op acc-btw)
       (cond
         [(empty? lon) (satisfy? acc-e acc-op acc-btw)]
         [else (fn-for-n (first lon) (rest lon) acc-e acc-op acc-btw)]))]

    (fn-for-lon lon0 false false false)))



;; # Variant C
;;
;; Again, the list must contain numbers satisfying the three requirements above.
;; Again, order does not matter.
;; Again, a given number in the list may only be used to satisfy a single requirement.
;; This time, however, the list may not contain any extraneous numbers.
;; So, (list 6 5 6) satisfies all our criteria for this variant, but (list 6 5 42 6) does not.
;;
;; > Do Now!
;; > Design a third method on lists of integers to check whether the list meets this new property.


;; (listof Number) -> Boolean
;; consume a listof Number produce true if the list satisfy the following requirments:
;; length of the list is 3
;; at least 1 number is even
;; at least 1 number is odd and positive
;; at least 1 number is between 5 and 10
;; each number can satisfy only one requirement and t

(check-expect (variantC empty)            false)
(check-expect (variantC (list 6 5 42 6))  false)
(check-expect (variantC (list 6 5 6))      true)
(check-expect (variantC (list 6 5))       false)
(check-expect (variantC (list 1 2 3))     false)
(check-expect (variantC (list 1 2 5))      true)
(check-expect (variantC (list -1 2 5))    false)
(check-expect (variantC (list -1 2 5 43)) false)

;; (define (variantC lon) false) ; stub

(define (variantC lon0)
  ;; acc-even             true if one of the number in the list is even;             false otherwise. boolean  - context acc 
  ;; acc-odd-and-positive true if one of the number in the list is odd and positive; false otherwise. boolean  - context acc
  ;; acc-btw-5-and-10     true if one of the number in the list is between 5 and 10; false otherwise. boolean  - context acc

  (local
    [

     (define (is-length-3? lon acc-e acc-op acc-btw) 
       (if (= (length lon) 3)
           (fn-for-lon lon0 false false false)
           false))

     (define (satisfy? acc-e acc-op acc-btw)
       (and 
        (not (false?   acc-e))
        (not (false?  acc-op))
        (not (false? acc-btw))))

     (define (fn-for-n n lon acc-e acc-op acc-btw) 
       (if (satisfy? acc-e acc-op acc-btw)  
           true
           (or
            (fn-for-lon lon 
                        (or (not (false? acc-e)) (is-even? n))
                        acc-op 
                        acc-btw)

            (fn-for-lon lon
                        acc-e 
                        (or (not (false? acc-op)) (is-odd-and-positive? n)) 
                        acc-btw)
            (fn-for-lon lon
                        acc-e
                        acc-op 
                        (or (not (false? acc-btw)) (is-between-5-and-10? n)))
            ))) 

     (define (fn-for-lon lon acc-e acc-op acc-btw)
       (cond
         [(empty? lon) (satisfy? acc-e acc-op acc-btw)]
         [else (fn-for-n (first lon) (rest lon) acc-e acc-op acc-btw)]))]
    (is-length-3? lon0 false false false)))
