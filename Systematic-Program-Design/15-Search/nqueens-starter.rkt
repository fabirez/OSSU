;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname nqueens-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Constants

;; [E]mpty
(define E false)
;; [Q]uenn
(define Q true)

;; Data definitions
(define-struct pos (x y))
;; Pos is (make-pos Number Number)
;; interp. a position with x and y

(define POS0 (make-pos 0 0)) 
(define POS1 (make-pos 2 2)) 
(define POS2 (make-pos 4 4)) 

;; Template
(define (fn-for-pos p) (... (pos-x p) (pos-y p)))

(define-struct cell (pos v))
;; Cell is (make-cell Pos empty | true)
;; interp. a cell at current pos with the current [v]alue

(define CELL0 (make-cell POS0 empty)) ;; nothing on the cell at POS0
(define CELL1 (make-cell POS1  true)) ;; queen   on the cell at POS1


;; Template
(define (fn-for-cell c) (... (fn-for-pos (cell-pos c)) (cell-v c)))


(define B0 ;; Initial empty chess board 4x4
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
    (make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
))

(define B0_1 ;; First queen
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
    (make-cell (make-pos 0 2) Q)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
))

(define B0_2 ;; Second queen
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) Q) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
    (make-cell (make-pos 0 2) Q)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
))

(define B0_3 ;; Third queen
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) Q) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
    (make-cell (make-pos 0 2) Q)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) Q) (make-cell (make-pos 3 3) E)
))

(define B0_S ;; B0_Solution
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) Q) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) Q)
    (make-cell (make-pos 0 2) Q)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) Q) (make-cell (make-pos 3 3) E)
))

(define B0_NP ;; Not possible
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
    (make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
    (make-cell (make-pos 0 3) Q)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
))



(define B1 ;; Initial empty chess board 5x5
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E) (make-cell (make-pos 4 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E) (make-cell (make-pos 4 1) E)
    (make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E) (make-cell (make-pos 4 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E) (make-cell (make-pos 4 3) E)
    (make-cell (make-pos 0 4) E)   (make-cell (make-pos 1 4) E) (make-cell (make-pos 2 4) E) (make-cell (make-pos 3 4) E) (make-cell (make-pos 4 4) E)
))

(define B2 ;; Initial empty chess board 6x6
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E) (make-cell (make-pos 4 0) E)  (make-cell (make-pos 5 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E) (make-cell (make-pos 4 1) E)  (make-cell (make-pos 5 1) E)
    (make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E) (make-cell (make-pos 4 2) E)  (make-cell (make-pos 5 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E) (make-cell (make-pos 4 3) E)  (make-cell (make-pos 5 3) E)
    (make-cell (make-pos 0 4) E)   (make-cell (make-pos 1 4) E) (make-cell (make-pos 2 4) E) (make-cell (make-pos 3 4) E) (make-cell (make-pos 4 4) E)  (make-cell (make-pos 5 4) E)
    (make-cell (make-pos 0 5) E)   (make-cell (make-pos 1 5) E) (make-cell (make-pos 2 5) E) (make-cell (make-pos 3 5) E) (make-cell (make-pos 4 5) E)  (make-cell (make-pos 5 5) E)
))

(define B3 ;; Initial empty chess board 7x7
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E) (make-cell (make-pos 4 0) E)  (make-cell (make-pos 5 0) E) (make-cell (make-pos 6 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E) (make-cell (make-pos 4 1) E)  (make-cell (make-pos 5 1) E) (make-cell (make-pos 6 1) E)
    (make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E) (make-cell (make-pos 4 2) E)  (make-cell (make-pos 5 2) E) (make-cell (make-pos 6 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E) (make-cell (make-pos 4 3) E)  (make-cell (make-pos 5 3) E) (make-cell (make-pos 6 3) E)
    (make-cell (make-pos 0 4) E)   (make-cell (make-pos 1 4) E) (make-cell (make-pos 2 4) E) (make-cell (make-pos 3 4) E) (make-cell (make-pos 4 4) E)  (make-cell (make-pos 5 4) E) (make-cell (make-pos 6 4) E)
    (make-cell (make-pos 0 5) E)   (make-cell (make-pos 1 5) E) (make-cell (make-pos 2 5) E) (make-cell (make-pos 3 5) E) (make-cell (make-pos 4 5) E)  (make-cell (make-pos 5 5) E) (make-cell (make-pos 6 5) E)
    (make-cell (make-pos 0 6) E)   (make-cell (make-pos 1 6) E) (make-cell (make-pos 2 6) E) (make-cell (make-pos 3 6) E) (make-cell (make-pos 4 6) E)  (make-cell (make-pos 5 6) E) (make-cell (make-pos 6 6) E)
))


(define B4 ;; Initial empty chess board 8x8
  (list
    (make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E) (make-cell (make-pos 4 0) E)  (make-cell (make-pos 5 0) E) (make-cell (make-pos 6 0) E) (make-cell (make-pos 7 0) E)
    (make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E) (make-cell (make-pos 4 1) E)  (make-cell (make-pos 5 1) E) (make-cell (make-pos 6 1) E) (make-cell (make-pos 7 1) E)
    (make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E) (make-cell (make-pos 4 2) E)  (make-cell (make-pos 5 2) E) (make-cell (make-pos 6 2) E) (make-cell (make-pos 7 2) E)
    (make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E) (make-cell (make-pos 4 3) E)  (make-cell (make-pos 5 3) E) (make-cell (make-pos 6 3) E) (make-cell (make-pos 7 3) E)
    (make-cell (make-pos 0 4) E)   (make-cell (make-pos 1 4) E) (make-cell (make-pos 2 4) E) (make-cell (make-pos 3 4) E) (make-cell (make-pos 4 4) E)  (make-cell (make-pos 5 4) E) (make-cell (make-pos 6 4) E) (make-cell (make-pos 7 4) E)
    (make-cell (make-pos 0 5) E)   (make-cell (make-pos 1 5) E) (make-cell (make-pos 2 5) E) (make-cell (make-pos 3 5) E) (make-cell (make-pos 4 5) E)  (make-cell (make-pos 5 5) E) (make-cell (make-pos 6 5) E) (make-cell (make-pos 7 5) E)
    (make-cell (make-pos 0 6) E)   (make-cell (make-pos 1 6) E) (make-cell (make-pos 2 6) E) (make-cell (make-pos 3 6) E) (make-cell (make-pos 4 6) E)  (make-cell (make-pos 5 6) E) (make-cell (make-pos 6 6) E) (make-cell (make-pos 7 6) E)
    (make-cell (make-pos 0 7) E)   (make-cell (make-pos 1 7) E) (make-cell (make-pos 2 7) E) (make-cell (make-pos 3 7) E) (make-cell (make-pos 4 7) E)  (make-cell (make-pos 5 7) E) (make-cell (make-pos 6 7) E) (make-cell (make-pos 7 7) E)
))

;; Natural -> Board | False 
;; consume a natural, generate a board Natural x Natural and produce a solved board if there is any, otherwise false.

;; (check-expect (solve B0) B0_S)
;; (check-expect (solve B0_NP) false)
;;
;; ;; (define (solve b) false) ; stub
;;
;; (define (solve b)
;; 	(local
;; 		[(define (solve--bd b)									 ;; Board -> Board | False
;; 				(if (is-solved? b)									 ;; Board -> Boolean
;; 						b
;; 						(solve--lobd (next-boards b))))  ;; Board -> (listof Board) | False
;;
;; 		 (define (solve--lobd lobd)
;; 			 (cond 
;; 				 [(empty? lobd) false]
;; 				 [else
;; 					 (local [(define try (solve--bd (first lobd)))]
;; 					 (if (not (false? try))
;; 						 try 
;; 						 (solve--lobd (rest b))))]) ;; (listof Board) -> (listof Board) | False
;; 		 (solve--bd b)))											;; trampoline




;; Natural Number -> Board
;; consume a natural that rapresent how many rows and col the board should have and a number that rapresent the initial index for the cell and generate a board Natural x Natural

(check-expect (gen--bd 4 0) B0)
(check-expect (gen--bd 5 0) B1)
(check-expect (gen--bd 6 0) B2)
(check-expect (gen--bd 7 0) B3)
(check-expect (gen--bd 8 0) B4)

;; (define (gen--bd n) empty) ; stub

(define (gen--bd n i)
	(local [(define y (floor (/ i n))) (define x (modulo i n))]
	(cond
		[(>= i (* n n)) empty]
		[else
			(cons (make-cell (make-pos x y) E) (gen--bd n (add1 i)))])))

;; Board Natural -> Boolean
;; consume a board and a natrual that represent the size of the board produce true if the current board is solved

(check-expect (is-solved? empty 4) false)
(check-expect (is-solved?    B0 4) false)
(check-expect (is-solved?  B0_S 4)  true)

;; (define (is-solved? b) false) ;; stub

(define (is-solved? b n) 
	(local
		[(define (queen? c) (not (false? (cell-v c)))) ]
	(= (length (filter queen? b)) n)))


(define ALL (list
	(make-pos 0 0)   (make-pos 1 0) (make-pos 2 0) (make-pos 3 0)
	(make-pos 0 1)   (make-pos 1 1) (make-pos 2 1) (make-pos 3 1)
	(make-pos 0 2)   (make-pos 1 2) (make-pos 2 2) (make-pos 3 2)
	(make-pos 0 3)   (make-pos 1 3) (make-pos 2 3) (make-pos 3 3)))
(define ALL_1 (list (make-pos 1 0) (make-pos 3 0) (make-pos 2 1) (make-pos 3 1) (make-pos 2 3) (make-pos 3 3)))
(define ALL_2 (list (make-pos 3 1) (make-pos 2 3) (make-pos 3 3)))
(define ALL_3 (list (make-pos 3 1)))

#; (check-expect (next-boards B0) 
							(list
									(list
											(make-cell (make-pos 0 0) Q)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
											(make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
											(make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
											(make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
									)
									(list
											(make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
											(make-cell (make-pos 0 1) Q)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
											(make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
											(make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
									)
									(list
											(make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
											(make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
											(make-cell (make-pos 0 2) Q)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
											(make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E)
									)
									(list
											(make-cell (make-pos 0 0) E)   (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E) (make-cell (make-pos 3 0) E)
											(make-cell (make-pos 0 1) E)   (make-cell (make-pos 1 1) E) (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
											(make-cell (make-pos 0 2) E)   (make-cell (make-pos 1 2) E) (make-cell (make-pos 2 2) E) (make-cell (make-pos 3 2) E)
											(make-cell (make-pos 0 3) E)   (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E) (make-cell (make-pos 3 3) E))))

;; (check-expect (next-boards B0_1)  (list B0_2))
;; (check-expect (next-boards B0_2)  (list B0_3))
;; (check-expect (next-boards B0_3)  (list B0_S))

(define (next-boards b) empty)
;; insert-queen is based on the list of position available of the find-availbe-lcol output

#; (define (next-boards b) (insert-queen (find-available-col b)))

;; (listof Cell) Cell Number -> Number | false
;; consume a list, the value to be find and the initial index produce the current index of a value in a list or false if the value doesn't exist.
;; IMPORTANT: start with an idx = 0.

(check-expect (find-cell empty (make-cell (make-pos 0 0) E) 0) false)
(check-expect (find-cell (list (make-cell (make-pos 0 0) E) (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E)) (make-cell (make-pos 0 0) E) 0) 0)
(check-expect (find-cell (list (make-cell (make-pos 0 0) E) (make-cell (make-pos 1 0) E) (make-cell (make-pos 2 0) E)) (make-cell (make-pos 2 0) E) 0) 2)

;; (define (find-cell loc c) 0) ; stub

(define (find-cell loc c idx)
	(cond 
		[(empty? loc) false]
		[else
		(local
			[
			 (define (equal-cell? c0 c1) 
				 (and
					 (= (get-x c0) (get-x c1))
					 (= (get-y c0) (get-y c1))
					 (or 
						 (and (false? (cell-v c0)) (false? (cell-v c1)))
						 (and (not (false? (cell-v c0))) (not (false? (cell-v c1)))))))
			]

			(if (equal-cell? (first loc) c)
					idx
					(find-cell (rest loc) c (add1 idx))))])) 

;; Board -> (listof Pos)
;; consume a board an produce the list of position that are available and valid 

(check-expect (filter-board B0) ;; Board empty
              (list   (make-pos 0 0)    (make-pos 1 0)  (make-pos 2 0)  (make-pos 3 0) 
                      (make-pos 0 1)    (make-pos 1 1)  (make-pos 2 1)  (make-pos 3 1) 
                      (make-pos 0 2)    (make-pos 1 2)  (make-pos 2 2)  (make-pos 3 2) 
                      (make-pos 0 3)    (make-pos 1 3)  (make-pos 2 3)  (make-pos 3 3)))

(check-expect (filter-board B0_1) ;; Board with 1 queen
              (list   (make-pos 1 0)  (make-pos 3 0) 
                      (make-pos 2 1)  (make-pos 3 1) 
                      (make-pos 2 3)  (make-pos 3 3)))

(check-expect (filter-board B0_2) ;; Board with 2 queen
              (list   (make-pos 3 1) (make-pos 2 3)  (make-pos 3 3)))

(check-expect (filter-board B0_3) ;; Board with 3 queen
              (list (make-pos 3 1)))

(check-expect (filter-board B0_S) ;; Board with 4 queen
              empty)

(define (filter-board b) empty) ; stub

#; (define (filter-board b)  
(local
  [(define queens (only-queen b))] ;; Board -> (listof Queen)
    (no-pos-duplicate (all-queens-pos queens (length b)))))


;; (listof Pos) (listof Pos) -> (listof Pos)
;; consume
;; the  first list of pos, that is all the available position in a empty baord NxN 
;; the second list of pos, that is the position already occuped by queens inside the board.
;; produce
;; list of all available and vaild position not occupied by queen by filtering the first with the second
;; (occoupied means that the queen cannot eat)
;; [A]ll [P]os [4]x4
(define AP4 (list                 
                      (make-pos 0 0)    (make-pos 1 0)  (make-pos 2 0)  (make-pos 3 0) 
                      (make-pos 0 1)    (make-pos 1 1)  (make-pos 2 1)  (make-pos 3 1) 
                      (make-pos 0 2)    (make-pos 1 2)  (make-pos 2 2)  (make-pos 3 2) 
                      (make-pos 0 3)    (make-pos 1 3)  (make-pos 2 3)  (make-pos 3 3)))


(check-expect (only-valid-pos AP4 (queen-pos (make-cell (make-pos 0 2) Q) 4))
              (list                 
                      (make-pos 1 0)  (make-pos 3 0) 
                      (make-pos 2 1)  (make-pos 3 1) 
                      (make-pos 2 3)  (make-pos 3 3)))
      

;; (define (only-valid-pos lop0 lop1) empty) ; stub

(define (only-valid-pos lop0 lop1)
  (local
    [(define (same-pos? p) (check-pos? p lop1))
     (define (check-pos? p lop1)
      (cond
        [(empty? lop1) true]
        [else
          (if (and (= (pos-x p) (pos-x (first lop1))) (= (pos-y p) (pos-y (first lop1))))
          false 
          (check-pos? p (rest lop1)))]))]

  (filter same-pos? lop0)))

;; Board -> (listof Pos) 
;; consume a board and produce a list of all the pos inside the board.

(check-expect (get-lop B0)
              (list   (make-pos 0 0)    (make-pos 1 0)  (make-pos 2 0)  (make-pos 3 0) 
                      (make-pos 0 1)    (make-pos 1 1)  (make-pos 2 1)  (make-pos 3 1) 
                      (make-pos 0 2)    (make-pos 1 2)  (make-pos 2 2)  (make-pos 3 2) 
                      (make-pos 0 3)    (make-pos 1 3)  (make-pos 2 3)  (make-pos 3 3)))

;; (define (get-lop b) empty)

(define (get-lop b)
  (local
    [(define (only-pos c) (cell-pos c))]
  (map only-pos b)))

;; (listof Pos) -> (listof Pos)
;; consume a list of positions and remove the duplicate pos inside of it.

(check-expect (no-pos-duplicate empty) empty)
(check-expect (no-pos-duplicate (list (make-pos 0 0) (make-pos 0 1) (make-pos 1 0)))
              (list (make-pos 0 0) (make-pos 0 1) (make-pos 1 0)))
(check-expect (no-pos-duplicate (list (make-pos 0 0) (make-pos 0 1) (make-pos 0 0))) 
              (list (make-pos 0 1) (make-pos 0 0)))

;; (define (no-pos-duplicate lop) empty) ;; stub

(define (no-pos-duplicate lop)
  (cond 
    [(empty? lop) empty]
    [else
      (local
        [
         (define (duplicate-pos? p lop)
           (cond
             [(empty? lop) false]
             [else
              (if (and (= (pos-x p) (pos-x (first lop))) (= (pos-y p) (pos-y (first lop))))
               true
               (duplicate-pos? p (rest lop)))]))]

      (if (duplicate-pos? (first lop) (rest lop))
        (no-pos-duplicate (rest lop))
        (cons (first lop) (no-pos-duplicate (rest lop)))))]))


;; (listof Queen) Natural -> (listof Pos)
;; consumes a list of queens and the size of the board and produce the list of position that each queen occupy

(check-expect (all-queens-pos empty 4) empty)
(check-expect (all-queens-pos  (list (make-cell (make-pos 0 2) Q)) 4) (queen-pos (make-cell (make-pos 0 2) Q) 4))
(check-expect (all-queens-pos (list (make-cell (make-pos 0 2) Q) (make-cell (make-pos 1 0) Q)) 4) 
              (append (queen-pos (make-cell (make-pos 0 2) Q) 4) (queen-pos (make-cell (make-pos 1 0) Q) 4)))

;; (define (all-queens-pos loq sb) emtpy) ; stub

(define (all-queens-pos loq sb) 
      (cond
        [(empty? loq) empty]
        [else (append (queen-pos (first loq) sb) (all-queens-pos (rest loq) sb))]))

;; Board -> (listof Queen)
;; consume a boarda and produce only the queens 
(check-expect (only-queen B0) empty)
(check-expect (only-queen B0_1) (list (make-cell (make-pos 0 2) Q)))
(check-expect (only-queen B0_2) (list (make-cell (make-pos 1 0) Q) (make-cell (make-pos 0 2) Q)))
(check-expect (only-queen B0_3) (list (make-cell (make-pos 1 0) Q) (make-cell (make-pos 0 2) Q) (make-cell (make-pos 2 3) Q)))
(check-expect (only-queen B0_S) (list (make-cell (make-pos 1 0) Q) (make-cell (make-pos 3 1) Q) (make-cell (make-pos 0 2) Q) (make-cell (make-pos 2 3) Q)))

;; (define (only-queen b) empty) ; stub

(define (only-queen b)
  (local [(define (is-queen? c) (not (false? (cell-v c))))]
    (filter is-queen? b)))

;; Board (listof pos) -> Board
;; consume a list of pos,  and filter the board them by the pos in listof pos
;; the produced board will have less cells (only the available ones)

(define P1 (list                                                ;; Quenn 0 0
                (make-pos 0 0)                                  ;; current Quenn position
                (make-pos 1 0) (make-pos 2 0) (make-pos 3 0)    ;; Horizontal
                (make-pos 0 1) (make-pos 0 2) (make-pos 0 3)    ;; Vertical
                (make-pos 1 1) (make-pos 2 2) (make-pos 3 3)		;; Diagonal br
              ))


(check-expect (format-board B0 P1)
              (list
                     (make-cell (make-pos 2 1) E) (make-cell (make-pos 3 1) E)
                     (make-cell (make-pos 1 2) E) (make-cell (make-pos 3 2) E)
                     (make-cell (make-pos 1 3) E) (make-cell (make-pos 2 3) E)))

;; (define (format-board b lop) empty) ; stub

(define (format-board b lop)
    (local
      [(define (same-p? c) (same-c? c lop))
       (define (same-c? c lop) 
         (cond 
           [(empty? lop) true]
           [else 
             (if 
               (and (= (pos-x (first lop)) (pos-x (cell-pos c))) (= (pos-y (first lop)) (pos-y (cell-pos c))))
               false
               (same-c? c (rest lop)))]))]
    (filter same-p? b)))

;; Queen Natural -> (listof pos) 
;; consume a queen and the size of the board and produe the list of position that she occupy
;; !!! try to call this function only on the last inserted queen
;; ASSUME: The cell contains a queen

(check-expect (queen-pos (make-cell (make-pos 0 0) Q) 4)
              (list 
                (make-pos 0 0)                                  ;; current Quenn position
                (make-pos 1 0) (make-pos 2 0) (make-pos 3 0)    ;; Horizontal
                (make-pos 0 1) (make-pos 0 2) (make-pos 0 3)    ;; Vertical
                (make-pos 1 1) (make-pos 2 2) (make-pos 3 3)		;; Diagonal br
              ))

(check-expect (queen-pos (make-cell (make-pos 0 2) Q) 4)
              (list 
                (make-pos 0 2)                                  ;; current Quenn position
                (make-pos 1 2) (make-pos 2 2) (make-pos 3 2)    ;; Horizontal
                (make-pos 0 3) (make-pos 0 1) (make-pos 0 0)    ;; Vertical
                (make-pos 1 3)  		                            ;; Diagonal br
                (make-pos 1 1) (make-pos 2 0) 		              ;; Diagonal tr
              ))


;; (define (queen-pos q sb) empty)  ; stub

(define (queen-pos q sb) (append (list (make-pos (get-x q) (get-y q))) (queen-h-lr q sb) (queen-h-rl q) (queen-v-tb q sb) (queen-v-bt q) (queen-d-br q sb) (queen-d-bl q sb) (queen-d-tl q) (queen-d-tr q sb)))



;; Queen Number -> (listof Pos)
;; consume a queen and the length of the board produe the list of Pos that the queen occupy HORIZONTALLY form left to right

(check-expect (queen-h-lr (make-cell (make-pos 0 0) Q) 4) (list (make-pos 1 0) (make-pos 2 0) (make-pos 3 0)))
(check-expect (queen-h-lr (make-cell (make-pos 2 0) Q) 4) (list (make-pos 3 0)))
(check-expect (queen-h-lr (make-cell (make-pos 3 0) Q) 4) empty)

(define (queen-h-lr q sb)
     (local [
             (define pos-queen (make-pos (+ (get-x q) 1) (get-y q))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (>= (get-x new-queen) sb)
       empty
       (cons pos-queen (queen-h-lr new-queen sb)))))


;; Queen -> (listof Pos)
;; consume a queen produe the list of Pos that the queen occupy HORIZONTALLY form right to left 

(check-expect (queen-h-rl (make-cell (make-pos 0 0) Q)) empty)
(check-expect (queen-h-rl (make-cell (make-pos 2 0) Q)) (list (make-pos 1 0) (make-pos 0 0)))
(check-expect (queen-h-rl (make-cell (make-pos 3 0) Q)) (list (make-pos 2 0) (make-pos 1 0) (make-pos 0 0)))

(define (queen-h-rl q)
     (local [
             (define pos-queen (make-pos (- (get-x q) 1) (get-y q))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (< (get-x new-queen) 0)
       empty
       (cons pos-queen (queen-h-rl new-queen)))))



;; Queen Number -> (listof Pos)
;; consume a queen and the length of the board produe the list of Pos that the queen occupy VERTICALLY form top to bottom

(check-expect (queen-v-tb (make-cell (make-pos 0 0) Q) 4) (list (make-pos 0 1) (make-pos 0 2) (make-pos 0 3)))
(check-expect (queen-v-tb (make-cell (make-pos 0 2) Q) 4) (list (make-pos 0 3)))
(check-expect (queen-v-tb (make-cell (make-pos 0 3) Q) 4) empty)

(define (queen-v-tb q sb)
     (local [
             (define pos-queen (make-pos (get-x q) (+ (get-y q) 1))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (>= (get-y new-queen) sb)
       empty
       (cons pos-queen (queen-v-tb new-queen sb)))))

;; Queen -> (listof Pos)
;; consume a queen and the length of the board produe the list of Pos that the queen occupy VERTICALLY form bottom to top

(check-expect (queen-v-bt (make-cell (make-pos 0 0) Q)) empty)
(check-expect (queen-v-bt (make-cell (make-pos 0 2) Q)) (list (make-pos 0 1) (make-pos 0 0)))
(check-expect (queen-v-bt (make-cell (make-pos 0 3) Q)) (list (make-pos 0 2) (make-pos 0 1) (make-pos 0 0)))

(define (queen-v-bt q)
     (local [
             (define pos-queen (make-pos (get-x q)  (- (get-y q) 1))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (< (get-y new-queen) 0)
       empty
       (cons pos-queen (queen-v-bt new-queen)))))


;; Queen Number -> (listof Pos)
;; consume a queen and the length of the board produce the list of Pos that the queen occupy DIAGONALLY form bottom to right

(check-expect (queen-d-br (make-cell (make-pos 0 0) Q) 4) (list (make-pos 1 1) (make-pos 2 2) (make-pos 3 3)))
(check-expect (queen-d-br (make-cell (make-pos 2 0) Q) 4) (list (make-pos 3 1)))
(check-expect (queen-d-br (make-cell (make-pos 3 0) Q) 4) empty)

(define (queen-d-br q sb)
     (local [
             (define pos-queen (make-pos (+ (get-x q) 1) (+ (get-y q) 1))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (or (>= (get-x new-queen) sb) (>= (get-y new-queen) sb))
       empty
       (cons pos-queen (queen-d-br new-queen sb)))))


;; Queen Number -> (listof Pos)
;; consume a queen and the length of the board produce the list of Pos that the queen occupy DIAGONALLY form bottom to left

(check-expect (queen-d-bl (make-cell (make-pos 0 0) Q) 4) empty)
(check-expect (queen-d-bl (make-cell (make-pos 2 0) Q) 4) (list (make-pos 1 1) (make-pos 0 2)))
(check-expect (queen-d-bl (make-cell (make-pos 3 0) Q) 4) (list (make-pos 2 1) (make-pos 1 2) (make-pos 0 3)))

(define (queen-d-bl q sb)
     (local [
             (define pos-queen (make-pos (- (get-x q) 1) (+ (get-y q) 1))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (or (< (get-x new-queen) 0) (>= (get-y new-queen) sb))
       empty
       (cons pos-queen (queen-d-bl new-queen sb)))))


;; Queen Number -> (listof Pos)
;; consume a queen and the length of the board produce the list of Pos that the queen occupy DIAGONALLY from top to left

(check-expect (queen-d-tl (make-cell (make-pos 0 0) Q)) empty)
(check-expect (queen-d-tl (make-cell (make-pos 2 2) Q)) (list (make-pos 1 1) (make-pos 0 0)))
(check-expect (queen-d-tl (make-cell (make-pos 3 3) Q)) (list (make-pos 2 2) (make-pos 1 1) (make-pos 0 0)))

(define (queen-d-tl q)
     (local [
             (define pos-queen (make-pos (- (get-x q) 1) (- (get-y q) 1))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (or (< (get-x new-queen) 0) (< (get-y new-queen) 0))
       empty
       (cons pos-queen (queen-d-tl new-queen)))))


;; Queen Number -> (listof Pos)
;; consume a queen and the length of the board produce the list of Pos that the queen occupy DIAGONALLY from top to right

(check-expect (queen-d-tr (make-cell (make-pos 0 0) Q) 4) empty)
(check-expect (queen-d-tr (make-cell (make-pos 2 2) Q) 4) (list (make-pos 3 1)))
(check-expect (queen-d-tr (make-cell (make-pos 0 3) Q) 4) (list (make-pos 1 2) (make-pos 2 1) (make-pos 3 0)))

(define (queen-d-tr q sb)
     (local [
             (define pos-queen (make-pos (+ (get-x q) 1) (- (get-y q) 1))) 
             (define new-queen (make-cell pos-queen Q))
            ]
  (if  (or (>= (get-x new-queen) sb) (< (get-y new-queen) 0))
       empty
       (cons pos-queen (queen-d-tr new-queen sb)))))


;; Cell -> Number
;; consume a cell and produce he current x of the cell

(check-expect (get-x (make-cell (make-pos 3 0) Q)) 3)
(check-expect (get-x (make-cell (make-pos 0 3) E)) 0)

;; (define (get-x c) 0) ; stub

(define (get-x c) (pos-x (cell-pos c)))

;; Cell -> Number
;; consume a cell and produce the current y of the cell

(check-expect (get-y (make-cell (make-pos 3 0) Q)) 0)
(check-expect (get-y (make-cell (make-pos 0 3) E)) 3)

;; (define (get-y c) 0) ; stub

(define (get-y c) (pos-y (cell-pos c)))

