;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname simple-text-editor-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; simple-text-editor-starter.rkt

;  
;  In this problem, you will be designing a simple one-line text editor.
;  
;  The constants and data definitions are provided for you, so make sure 
;  to take a look through them after completing your own Domain Analysis. 
;  
;  Your text editor should have the following functionality:
;  - when you type, characters should be inserted on the left side of the cursor 
;  - when you press the left and right arrow keys, the cursor should move accordingly  
;  - when you press backspace (or delete on a mac), the last character on the left of 
;    the cursors should be deleted
;  



(require 2htdp/image)
(require 2htdp/universe)

;; A simple editor

;; Constants
;; =========

(define WIDTH 300)
(define HEIGHT 20)
(define MTS (empty-scene WIDTH HEIGHT))

(define CURSOR (rectangle 2 14 "solid" "red"))

(define TEXT-SIZE 14)
(define TEXT-COLOR "black")

;; Data Definitions
;; ================

(define-struct editor (pre post))
;; Editor is (make-editor String String)
;; interp. pre is the text before the cursor, post is the text after
(define E0 (make-editor "" ""))
(define E1 (make-editor "a" ""))
(define E2 (make-editor "" "b"))

#;
(define (fn-for-editor e)
  (... (editor-pre e)
       (editor-post e)))

;; Functions
;; ================

;; Editor -> Editor 
;; start the world with (main (make-editor "" ""))

(define (main c)
  (big-bang c                    ; Editor
    (on-tick   current-text)     ; Editor -> Editor 
    (to-draw   render)           ; Editor -> Image
    (on-key    handle-key)       ; Editor KeyEvent -> Editor 
    )) 

;; Editor -> Editor
;; return the current editor
(check-expect (current-text E0) E0)
(check-expect (current-text E1) E1)
(check-expect (current-text E2) E2)

; (define (current-text e) E0) ; stub

(define (current-text e) e)



;; Editor -> Natural
;; return the correct position of the CURSOR if the editor is empty return the position X 0 otherwise TEXT-SIZE

(check-expect (handle-cursor-pos (make-editor "" "")) 0)
(check-expect (handle-cursor-pos (make-editor "b" "")) TEXT-SIZE)
(check-expect (handle-cursor-pos (make-editor "b" "a")) TEXT-SIZE)

; (define (handle-cursor-pos e) 0) ; stub

(define (handle-cursor-pos e) 
  (if
   (and 
    (string=? (editor-pre e) "") 
    (string=? (editor-post e) "")
    )
   0
   ;;(* (string-length (editor-pre e)) (/ TEXT-SIZE 2))
   TEXT-SIZE
   )
  )

;; Editor -> Image
;; draw the current status of the editor

(check-expect
 (render E0)
 (overlay/align "left" "middle" CURSOR MTS)
 )

(check-expect
 (render E1)
 (overlay/align "left" "middle"
                (beside
                 (text "a" TEXT-SIZE TEXT-COLOR)
                 CURSOR
                 )
                MTS)
 )

(check-expect
 (render E2)
 (overlay/align "left" "middle"
                (beside
                 CURSOR
                 (text "b" TEXT-SIZE TEXT-COLOR)
                 )
                MTS)
 )


; (define (render e) MTS) ; stub

(define (render e)
  (overlay/align "left" "middle"
                 (beside
                  (text (editor-pre e)  TEXT-SIZE TEXT-COLOR)
                  CURSOR
                  (text (editor-post e) TEXT-SIZE TEXT-COLOR)
                  )
                 MTS)
  )


;; String -> Boolean
;; return true if the string is empty (has length = 0) otherwise false

(check-expect (is-empty? "") true)
(check-expect (is-empty? " ") false)

; (define (is-empty? s) false) ; stub

(define (is-empty? s) (= (string-length s) 0))

;; String -> String
;; return the first character of a string

(check-expect (get-first "ab") "a")
(check-expect (get-first  " ") " ")

; (define (get-first s) "")  stub

(define (get-first s)
  (if (> (string-length s) 0)
      (substring s 0 1)
      s
      )
  )


;; String -> String
;; return the last character of a strign

(check-expect (get-last "ab") "b")
(check-expect (get-last  " ") " ")
(check-expect (get-last  "") "")

; (define (get-last s) "") ; stub

(define (get-last s) 
  (if (> (string-length s) 0)
      (substring s (- (string-length s) 1) (string-length s))
      s
      )
  )


;; String -> String
;; return the string without the last character

(check-expect (all-no-last "") "")
(check-expect (all-no-last " ") "")
(check-expect (all-no-last "hello") "hell")

; (define (all-no-last s) "") ; stub

(define (all-no-last s)
  (if (> (string-length s) 0)
      (substring 
       s 0 (- (string-length s) 1)
       )
      s
      )
  )


;; String -> String
;; return the string without the first character

(check-expect (all-no-first "") "")
(check-expect (all-no-first " ") "")
(check-expect (all-no-first "hello") "ello")

; (define (all-no-first s) "") ; stub

(define (all-no-first s)
  (if (> (string-length s) 0)
      (substring 
       s 1 (string-length s) 
       )
      s
      )
  )



;; Editor KeyEvent -> Editor
;; insert the current key or exectue the command if it is a special key

(check-expect (handle-key (make-editor "" "") " ")  (make-editor " " "")) ;; spacebar
(check-expect (handle-key (make-editor "ab" "") " ")  (make-editor "ab " ""))
(check-expect (handle-key (make-editor "a" "b") " ")  (make-editor "a " "b"))

(check-expect (handle-key (make-editor "a" "")  "b")  (make-editor  "ab" "")) ;; normal typing
(check-expect (handle-key (make-editor "ab" "") "c")  (make-editor "abc" ""))

(check-expect (handle-key (make-editor "" "abc") "left")  (make-editor "" "abc")) ;; moving with arrows
(check-expect (handle-key (make-editor "abc" "") "left")  (make-editor "ab" "c")) 
(check-expect (handle-key (make-editor "ab" "c") "right")  (make-editor "abc" ""))
(check-expect (handle-key (make-editor "abc" "") "right")  (make-editor "abc" ""))

(check-expect (handle-key (make-editor "abc" "") "\b")  (make-editor "ab" "")) ;; backspace 
(check-expect (handle-key (make-editor "ab" "") "\b")  (make-editor "a" "")) 
(check-expect (handle-key (make-editor "a" "") "\b")  (make-editor "" "")) 
(check-expect (handle-key (make-editor "" "") "\b")  (make-editor "" ""))

(check-expect (handle-key (make-editor "abc" "")   "shift")  (make-editor "abc" "")) ;; special keys 
(check-expect (handle-key (make-editor "abc" "") "control")  (make-editor "abc" "")) 
(check-expect (handle-key (make-editor "abc" "")      "\r")  (make-editor "abc" "")) ;; enter key

; (define (handle-key e ke)  E0) ; stub

(define (handle-key e ke)
  (cond 
    [
     (or 
      (key=? ke "shift") (key=? ke "control") (key=? ke "\r")
      ) 
     e 
     ]


    [
     (key=? ke "left")  
     (make-editor 
      (all-no-last (editor-pre e))
      (string-append (get-last (editor-pre e)) (editor-post e))
      ) 
     ]

    [ 
     (key=? ke "right") 
     (make-editor 
      (string-append (editor-pre e) (get-first (editor-post e)))
      (all-no-first (editor-post e))
      ) 
     ]

    [ 
     (key=? ke "\b")  
     (make-editor (all-no-last (editor-pre e)) (editor-post e)) 
     ]

    [else 
     (make-editor (string-append (editor-pre e) ke) (editor-post e))]))
