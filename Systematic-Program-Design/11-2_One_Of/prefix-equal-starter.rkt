;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname prefix-equal-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))

;; prefix-equal-starter.rkt

;; PROBLEM: Design a function that consumes two list of stringd and produces true
;; if the first list is a prefix of the second. Prefix means that the elements of 
;; the first list match the lements of the second list 1 for 1, and the first list
;; is at least as long as the second. If the preceding is ture the second list
;; may be longer than the first.
;;
;; For reference, the ListOfString Data definition is provided below

;; ==================
;; Data definitions:

;; ListOfString is one of:
;;	- empty
;;	- (cons String ListOfString)
;; interp. a list of strings


(define LS0 empty)
(define LS1 (cons "a" empty))
(define LS2 (cons "a" (cons "b" empty)))
(define LS3 (cons "c" (cons "b" (cons "a" empty))))

#; 
(define (fn-for-los los)
  (cond [(empty? los) (...)]
        [else
         (... (first los)
              (fn-for-los (rest los)))]))


;; ==================
;; Functions:

;; ListOfString ListOfString -> Boolean
;; produce true if lsta is a prefix of lstb

;; examples from the Cross Product of type comments
(check-expect (prefix=? empty empty) true)
(check-expect (prefix=? (list "x") empty) false)
(check-expect (prefix=? empty (list "x")) true) ;; yeah, this is true because: (list <empty> "x")
(check-expect (prefix=? (list "x") (list "x")) true)
(check-expect (prefix=? (list "x") (list "y")) false)
;; ====
(check-expect (prefix=? (list "x" "y") (list "x" "y")) true)
(check-expect (prefix=? (list "x" "x") (list "x" "y")) false)
(check-expect (prefix=? (list "x") (list "x" "y")) true)
(check-expect (prefix=? (list "x" "y" "z" ) (list "x" "y")) false)


;; (define (prefix=? lsta lstb) false) ; stub
(define (prefix=? lsta lstb) 
  (cond
    [(empty? lsta) true]
    [(empty? lstb) false]
    [else (and (string=? (first lsta)
                         (first lstb))
               (prefix=? (rest lsta) (rest lstb)))]))
