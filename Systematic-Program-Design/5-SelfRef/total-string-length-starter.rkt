;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname total-string-length-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; ListOfString is one of: 
;;  - empty
;;  - (cons String ListOfString)
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

;; Template rules used: 
;; - one of: 2 cases
;; - atomic distinct: empty
;; - compound: (cons String ListOfString)
;; - atomic non-distinct: (first los) is  String
;; - self-reference: (rest los) is ListOfString

;; =================
;; Functions:

; PROBLEM:
; 
; Design a function that consumes a list of strings and determines the total 
; number of characters (single letters) in the list. Call it total-string-length. 
; 

;; ListOfString -> Number
;; consumes a list of string, and produces the total numbers of chars (single letters) in the list.

(check-expect (count-char empty) 0)
(check-expect (count-char (cons "a" empty)) 1)
(check-expect (count-char (cons "ab" empty)) 2)
(check-expect (count-char (cons "a" (cons "b" (cons "ab" empty)))) 4)

; (define (count-char los) 0) ; stub

(define (count-char los) 
  (cond [(empty? los) 0]
        [else
         (+ (string-length (first los)) 
            (count-char (rest los)))]))
