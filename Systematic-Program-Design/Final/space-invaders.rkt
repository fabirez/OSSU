;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname space-invaders) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/universe)
(require 2htdp/image)

;; Space Invaders


;; Constants:

(define WIDTH  300)
(define HEIGHT 500)

(define INVADER-X-SPEED 1.5)  ;speeds (not velocities) in pixels per tick
(define INVADER-Y-SPEED 1.5)

(define TANK-SPEED 2)
(define MISSILE-SPEED 10)

(define HIT-RANGE 10)

(define INVADE-RATE 100)

(define BG (empty-scene WIDTH HEIGHT))

(define INVADER
  (overlay/xy (ellipse 10 15 "outline" "blue")              ;cockpit cover
              -5 6
              (ellipse 20 10 "solid"   "blue")))            ;saucer

(define TANK
  (overlay/xy (overlay (ellipse 28 8 "solid" "black")       ;tread center
                       (ellipse 30 10 "solid" "green"))     ;tread outline
              5 -14
              (above (rectangle 5 10 "solid" "black")       ;gun
                     (rectangle 20 10 "solid" "black"))))   ;main body

(define TANK-HEIGHT/2 (/ (image-height TANK) 2))

(define MISSILE (ellipse 5 15 "solid" "red"))

(define TANK-POSY (- HEIGHT 15))
(define MISSILE-POSY (- HEIGHT 35))

(define TANK-RIGHT 1)
(define TANK-LEFT -1)

(define INVADER-MAX-RANDOM 5) 
(define INVADER-I-POSY -10) 
(define INVADER-I-DX -1) 
(define GAME-OVER
  (place-image
 (text "Space Invader" 16 "black") (/ WIDTH 2) 100
 (place-image
  (text "Game Over!!!" 32 "red") (/ WIDTH 2) 130
  (place-image (text "Try Again" 24 "black") (/ WIDTH 2) 230
               (place-image
                (beside INVADER (rectangle 20 20 "solid" "white") TANK) (/ WIDTH 2) 340 BG)))))


;; Data Definitions:

(define-struct game (invaders missiles tank))
;; Game is (make-game  (listof Invader) (listof Missile) Tank)
;; interp. the current state of a space invaders game
;;         with the current invaders, missiles and tank position

;; Game constants defined below Missile data definition

#;
(define (fn-for-game s)
  (... (fn-for-loinvader (game-invaders s))
       (fn-for-lom (game-missiles s))
       (fn-for-tank (game-tank s))))



(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, HEIGHT - TANK-HEIGHT/2 in screen coordinates
;;         the tank moves TANK-SPEED pixels per clock tick left if dir -1, right if dir 1

(define T0 (make-tank (/ WIDTH 2) TANK-RIGHT))   ;center going right
(define T1 (make-tank 50 TANK-RIGHT))            ;going right
(define T2 (make-tank 50 TANK-LEFT))             ;going left

#;
(define (fn-for-tank t)
  (... (tank-x t) (tank-dir t)))



(define-struct invader (x y dx))
;; Invader is (make-invader Number Number Number)
;; interp. the invader is at (x, y) in screen coordinates
;;         the invader along x by dx pixels per clock tick

(define I1 (make-invader 150 100 12))           ;not landed, moving right
(define I2 (make-invader 150 HEIGHT -10))       ;exactly landed, moving left
(define I3 (make-invader 150 (+ HEIGHT 10) 10)) ;> landed, moving right


#;
(define (fn-for-invader invader)
  (... (invader-x invader) (invader-y invader) (invader-dx invader)))


(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. the missile's location is x y in screen coordinates

(define M1 (make-missile 150 300))                       ;not hit I1
(define M2 (make-missile (invader-x I1) (+ (invader-y I1) 10)))  ;exactly hit I1
(define M3 (make-missile (invader-x I1) (+ (invader-y I1)  5)))  ;> hit I1

#;
(define (fn-for-missile m)
  (... (missile-x m) (missile-y m)))



(define G0 (make-game empty empty T0))
(define G1 (make-game empty empty T1))
(define G2 (make-game (list I1) (list M1) T1))
(define G3 (make-game (list I1 I2) (list M1 M2) T1))



;; [S]tub[G]ame an utily for stubs
(define SG (make-game empty empty (make-tank 0 TANK-RIGHT)))
;; an utility for last scene in the game
(define (game-over g) GAME-OVER)
;; =================
;; Functions:

;; Game -> Game
;; start the world with (main G0)
;; 
(define (main g)
  (big-bang g                            ; Game
    (on-tick                      tock)  ; Game -> Game
    (to-draw                    render)  ; Game -> Image
    (on-key                 handle-key)  ; Game KeyEvent -> Game
    (stop-when handle-stop game-over))) ; Game -> Boolean

;; Game -> Game
;; produce the next ...
;; !!!

;; (define (tock g) SG)

;; !!!
;; change name ceck-direction in chec-dir-taknk
(define (tock g)
  (make-game

   (check-collision
    (dir-invaders (move-invaders
                   (gen-invaders (game-invaders g))))
    (game-missiles g)
    )
   (filter-missiles (move-missiles (game-missiles g)))
   (check-direction (move-tank (game-tank g)))))



;; ListOfMissiles -> ListOfMissiles
;; filter out all the missiles out of the viewport (missile-y < 0)

(check-expect (filter-missiles empty) empty)
(check-expect (filter-missiles (list (make-missile 0 0))) (list (make-missile 0 0)))
(check-expect (filter-missiles (list (make-missile 150 150))) (list (make-missile 150 150)))
(check-expect (filter-missiles (list (make-missile WIDTH HEIGHT))) (list (make-missile WIDTH HEIGHT)))

(check-expect (filter-missiles (list (make-missile 150 -1))) empty)
(check-expect (filter-missiles (list (make-missile WIDTH -100))) empty)

(check-expect (filter-missiles (list (make-missile 0 0) (make-missile 150 -1))) (list (make-missile 0 0)))
(check-expect (filter-missiles (list (make-missile 150 -1) (make-missile 0 0))) (list (make-missile 0 0)))

;; (define (filter-missiles lom) empty) ; stub

(define (filter-missiles lom)
  (cond
    [(empty? lom) empty]
    [else
     (if
      (out-missile? (first lom))
      (rest lom)
      (cons (first lom) (filter-missiles (rest lom)))
      )
     ]
    ))

;; Missile -> Boolean
;; produces true if the pos-y of the missile is less than 0


(check-expect (out-missile? (make-missile 0 -10)) true)
(check-expect (out-missile? (make-missile -10 0)) false)
(check-expect (out-missile? (make-missile 0 0)) false)
(check-expect (out-missile? (make-missile 150 1500)) false)
(check-expect (out-missile? (make-missile WIDTH HEIGHT)) false)

;; (define (out-missile? m) false) ; stub

(define (out-missile? m) (< (missile-y m) 0)) 

;; ListOfInvaders ListOfMissiles -> ListOfInvaders 
;; !!!


;; (define (check-collision loi lom) empty) ; stub

(define (check-collision loi lom)
  (cond
    [(empty? loi) empty]
    [else
     (if (check-hit? (first loi) lom)
         (check-collision (rest loi) lom)
         (cons (first loi)
               (check-collision (rest loi) lom)))
     ]
    )) 





;; Invader ListOfMissiles -> Boolean
;; if hte invader go hitted by one of missiles return true otherwise false



;; (define (check-hit? i lom) false) ; stub

(define (check-hit? i lom) 
  (cond
    [(empty? lom) false]
    [else
     (if (hit? i (first lom))
         true
         (check-hit? i (rest  lom)))
     ]
    )
  ) 



;; Invader Misisle -> Boolean
;; prdouces true if the missile hit the invader otherwise false

(check-expect (hit? (make-invader 0 0 -1) (make-missile 10 10)) true)
(check-expect (hit? (make-invader 0 0 -1) (make-missile 15 15)) true)
(check-expect (hit? (make-invader 0 0 -1) (make-missile 0 15)) true)
(check-expect (hit? (make-invader 0 0 -1) (make-missile 15 0)) false)
(check-expect (hit? (make-invader 0 0 -1) (make-missile 20 20)) false)
(check-expect (hit? (make-invader 10 10 -1) (make-missile 10 10)) true)

;; (define (hit? i m) false) ; stub

(define (hit? i m)
  (and 
   (> (missile-y m) 0) ;; avoid hit outside of the viewport just to make sure
   (< (invader-x i)  (+ (missile-x m)  (image-width MISSILE)))
   (> (+ (invader-x i) (image-width INVADER)) (missile-x m))
   (< (invader-y i)  (+ (missile-y m)  (image-height MISSILE)))
   (> (+ (invader-y i) (image-height INVADER)) (missile-y m))))




;; ListOfInvaders -> ListOfInvaders
;; !!!

(check-expect (gen-invaders empty) empty)
;; (check-expect (gen-invaders (list I1) 3) (list I1))

;; (define (gen-invaders loi) empty) ; stub

(define (gen-invaders loi) (if (= (random INVADE-RATE) 1) (append loi (n-to-i (gen-naturals (random INVADER-MAX-RANDOM)))) loi))

;; ListOfNatural -> ListOfInvaders
;; produces a ListOfInvaders, based on the length of ListOfNatural 

(check-random (n-to-i (list 0))   (list (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)))
(check-random (n-to-i (list 1 0)) (list 
                                   (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)
                                   (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)))
(check-random (n-to-i (list 3 2 1 0)) 
              (list 
               (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)
               (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)
               (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)
               (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX)
               ))

;; (define (n-to-i lon) empty) ; stub

(define (n-to-i lon)
  (cond 
    [(empty? lon) empty]
    [else
     (cons (make-invader (random WIDTH) INVADER-I-POSY INVADER-I-DX) 
           (n-to-i (rest lon)))]
    ))



;; Natural -> ListOfNaturals
;; produce a lsit of natural numbers starting on the consumed natural
;; ASSUME: Natural can't be more than INVADER-MAX-RANDOM

(check-expect (gen-naturals 0) (list 0))
(check-expect (gen-naturals 1) (list 1 0))
(check-expect (gen-naturals 3) (list 3 2 1 0))
(check-expect (gen-naturals 6) (list 6 5 4 3 2 1 0))

;; (define (gen-naturals n) empty) ; stub

(define (gen-naturals n) 
  (cond 
    [(zero? n) (cons 0 empty)] 
    [else 
     (cons n (gen-naturals (- n 1)))
     ]
    ))



;; ListOfInvaders -> ListOfInvaders 
;; produce a new list of invaders with updated directions if needed
;; !!! Add more check-expect for the directions thing  (use list too)

(check-expect (dir-invaders empty) empty)
(check-expect (dir-invaders (cons (make-invader 150 100 12) empty)) (cons (make-invader 150 100 12) empty))
(check-expect (dir-invaders (cons (make-invader 150 100 12) (cons (make-invader  50 150 12) empty))) (cons (make-invader 150 100 12) (cons (make-invader 50 150 12) empty)))

;; (define (dir-invaders loi) empty) ; stub

(define (dir-invaders loi)
  (cond
    [(empty? loi) empty]
    [else
     (cons (dir-invader (first loi)) (dir-invaders (rest loi)))]
    ))


;; Invader -> Invader 
;; change the direction of the invader if x is < 0 or > WIDTH

(check-expect (dir-invader (make-invader 150 100 12)) (make-invader 150 100 12))
(check-expect (dir-invader (make-invader  50 150 -12)) (make-invader 50 150 -12))

(check-expect (dir-invader (make-invader  0 100 -12)) (make-invader 0 100 -12))
(check-expect (dir-invader (make-invader -2 100 -12)) (make-invader  -2 100  12))

(check-expect (dir-invader (make-invader  WIDTH       150 12)) (make-invader WIDTH       150  12))
(check-expect (dir-invader (make-invader  (+ 2 WIDTH) 150 12)) (make-invader (+ 2 WIDTH) 150 -12))


;; (define (dir-invader i) I1) ; stub

(define (dir-invader i) 
  (cond
    [(and (< (invader-x i) 0)      (< (invader-dx i) 0)) (make-invader (invader-x i) (invader-y i) (* -1 (invader-dx i)))]
    [(and (> (invader-x i) WIDTH)  (> (invader-dx i) 0)) (make-invader (invader-x i) (invader-y i) (* -1 (invader-dx i)))]
    [else
     i]
    )
  ) 


;; ListOfInvaders -> ListOfInvaders 
;; produces a new ListOfInvaders with y and x updated

(check-expect (move-invaders empty) empty)
(check-expect (move-invaders (cons (make-invader 50 100 1) empty)) (cons (move-invader (make-invader 50 100 1)) empty))
(check-expect (move-invaders (cons (make-invader 50 100 1) (cons (make-invader 100 150 -1) empty))) 
              (cons (make-invader (+ 50 INVADER-X-SPEED) (+ 100 INVADER-Y-SPEED) 1)
                    (cons (make-invader (- 100 INVADER-X-SPEED) (+ 150 INVADER-Y-SPEED) -1) empty)))             

;; (define (move-invaders loi) empty) ; stub

(define (move-invaders loi)
  (cond
    [(empty? loi) empty]
    [else
     (cons (move-invader (first loi)) (move-invaders (rest loi)))]
    ))


;; Invader -> Invader 
;; increment the position y and x of Invader by INVADER-X-SPEED and INVADER-Y-SPEED

(check-expect (move-invader (make-invader 150 100 12)) (make-invader (+ 150 INVADER-X-SPEED) (+ 100 INVADER-Y-SPEED) 12))
(check-expect (move-invader (make-invader  50 150 12)) (make-invader (+  50 INVADER-X-SPEED) (+ 150 INVADER-Y-SPEED) 12))

(check-expect (move-invader (make-invader 150 100 -12)) (make-invader (- 150 INVADER-X-SPEED) (+ 100 INVADER-Y-SPEED) -12))
(check-expect (move-invader (make-invader  50 150 -12)) (make-invader (-  50 INVADER-X-SPEED) (+ 150 INVADER-Y-SPEED) -12))


;; (define (move-invader i) I1) ; stub

(define (move-invader i)
  (if (>= (invader-dx i) 0)
      (make-invader 
       (+ (invader-x i) INVADER-X-SPEED)
       (+ (invader-y i) INVADER-Y-SPEED)
       (invader-dx i))
      (make-invader 
       (- (invader-x i) INVADER-X-SPEED)
       (+ (invader-y i) INVADER-Y-SPEED)
       (invader-dx i))
      ))

;; ListOfMissiles -> ListOfMissiles
;; increment the position y of every missiles by MISSILE-SPEED

;; (define-struct missile (x y))

(check-expect (move-missiles empty) empty)
(check-expect (move-missiles (cons (make-missile 50 MISSILE-POSY) empty)) (cons (make-missile 50 (- MISSILE-POSY MISSILE-SPEED)) empty))
(check-expect (move-missiles (cons (make-missile 50 MISSILE-POSY) (cons (make-missile 50 150) empty))) (cons (make-missile 50 (- MISSILE-POSY MISSILE-SPEED )) (cons (make-missile 50 (- 150 MISSILE-SPEED)) empty)))             

;; (define (move-missiles lom) empty) ; stub

(define (move-missiles lom)
  (cond
    [(empty? lom) empty]
    [else
     (cons (move-missile (first lom)) (move-missiles (rest lom)))]
    ))


;; Missile -> Missile
;; increment the position y of the missile by MISSILE-SPEED

(check-expect (move-missile (make-missile 50 MISSILE-POSY)) (make-missile 50 (- MISSILE-POSY MISSILE-SPEED)))
(check-expect (move-missile (make-missile 50 150)) (make-missile 50 (- 150 MISSILE-SPEED)))

;; (define (move-missile m) M1) ; stub

(define (move-missile m) (make-missile (missile-x m) (- (missile-y m) MISSILE-SPEED)))


;; Tank -> Tank
;; consume a tank and adjust the direction of the tank, when hits the edges

(check-expect (check-direction (make-tank  0 TANK-LEFT)) (make-tank  0 TANK-LEFT))  ;; reaching left-edge
(check-expect (check-direction (make-tank -1 TANK-LEFT)) (make-tank -1 TANK-RIGHT)) ;; reached left-edge

(check-expect (check-direction (make-tank WIDTH TANK-RIGHT)) (make-tank WIDTH TANK-RIGHT))             ;; reaching right-edge
(check-expect (check-direction (make-tank (+ WIDTH 1) TANK-RIGHT)) (make-tank (+ WIDTH  1) TANK-LEFT)) ;; reached right-edge

;; (define (check-direction t) T0) ; stub

(define (check-direction t) 
  (cond
    [(and (< (tank-x t) 0)  (= (tank-dir t) TANK-LEFT)) (make-tank (tank-x t) TANK-RIGHT)]
    [(and (> (tank-x t) WIDTH)  (= (tank-dir t) TANK-RIGHT)) (make-tank (tank-x t) TANK-LEFT)]
    [else
     t]
    )
  ) 

;; Tank -> Tank
;; consume a tank and increment or decrement by SPEED the current position X of the tank

(check-expect  (move-tank (make-tank (/ WIDTH 2)  TANK-RIGHT))  (make-tank (+ (/ WIDTH 2) TANK-SPEED)  TANK-RIGHT))
(check-expect  (move-tank (make-tank (/ WIDTH 2) TANK-LEFT))  (make-tank (- (/ WIDTH 2) TANK-SPEED) TANK-LEFT))
(check-expect  (move-tank (make-tank 50  TANK-RIGHT))  (make-tank (+ 50 TANK-SPEED)  TANK-RIGHT))
(check-expect  (move-tank (make-tank 50 TANK-LEFT))  (make-tank (+ 50 (* TANK-SPEED TANK-LEFT)) TANK-LEFT))

;; (define (move-tank t) T0) ; stub

(define (move-tank t) 
  (make-tank 
   (+ (tank-x t) (* TANK-SPEED (tank-dir t)))
   (tank-dir t)))


;; Game -> Image
;; render the game status in an image
(check-expect (render (make-game empty empty T0)) (place-image TANK (tank-x T0) TANK-POSY BG))
(check-expect (render (make-game
                       empty
                       (cons (make-missile (tank-x T0) MISSILE-POSY) empty)
                       T0))
              (place-image
               MISSILE (tank-x T0) MISSILE-POSY 
               (place-image TANK (tank-x T0) TANK-POSY BG)))

;; (define (render g) SG) ; stub

(define (render g)
  (place-images (gen-loi (game-invaders g)) (posn-inv (game-invaders g)) 
                (place-images (gen-loim (game-missiles g)) (gen-posn (game-missiles g)) 
                              (place-image TANK (tank-x (game-tank g)) TANK-POSY BG))))



;; ListOfInvaders -> ListOfPosn
;; produce a list of posn from a list of invaders 

(check-expect (posn-inv empty) empty)
(check-expect (posn-inv (cons (make-invader 10 10 1) empty)) (cons (make-posn 10 10) empty))
(check-expect (posn-inv (cons (make-invader 10 10 1) (cons (make-invader 50 50 -1) empty))) (cons (make-posn 10 10) (cons (make-posn 50 50) empty)))

;; (define (posn-inv loi) empty) ; stub

(define (posn-inv loi)
  (cond 
    [(empty? loi) empty]
    [else 
     (cons 
      (make-posn (invader-x (first loi)) (invader-y (first loi))) 
      (posn-inv (rest loi)))]))
     
;; ListOfMissiles -> ListOfImageInvaders 
;; produce a list of images of invaders, based on how many invaders in the current state

(check-expect (gen-loi empty) empty)
(check-expect (gen-loi (list (make-invader 0 0 0) (make-invader 10 10 10))) (list INVADER INVADER))

;; (define (gen-loi loi) empty) ; stub

(define (gen-loi loi)
  (cond 
    [(empty? loi) empty]
    [else (cons INVADER (gen-loi (rest loi)))]))



;; ListOfMissile -> ListOfPosn
;; produce a list of posn from a list of missile

(check-expect (gen-posn empty) empty)
(check-expect (gen-posn (cons (make-missile 10 10) empty)) (cons (make-posn 10 10) empty))
(check-expect (gen-posn (cons (make-missile 10 10) (cons (make-missile 50 50) empty))) (cons (make-posn 10 10) (cons (make-posn 50 50) empty)))

;; (define (gen-posn lom) empty) ; stub

(define (gen-posn lom)
  (cond 
    [(empty? lom) empty]
    [else 
     (cons 
      (make-posn (missile-x (first lom)) (missile-y (first lom))) 
      (gen-posn (rest lom)))]))
     
;; ListOfMissiles -> ListOfImageMissiles 
;; produce a list of images missiles, based on how many missile we have

(check-expect (gen-loim empty) empty)
   
(check-expect (gen-loim (list (make-missile 0 0) (make-missile 10 10))) (list MISSILE MISSILE))

;; (define (gen-loim loim) empty) ; stub

(define (gen-loim loim)
  (cond 
    [(empty? loim) empty]
    [else (cons MISSILE (gen-loim (rest loim)))]))



;; Game KeyEvent -> Game
;; consumes a game and KeyEvent based on the key produces differents game state

(check-expect (handle-key G0 " ")     (make-game empty 
                                                 (cons (make-missile (tank-x T0) MISSILE-POSY) 
                                                       empty) T0))
(check-expect (handle-key G0 "right") (make-game empty 
                                                 empty 
                                                 (make-tank (tank-x T0) TANK-RIGHT)))
(check-expect (handle-key G0  "left") (make-game empty 
                                                 empty 
                                                 (make-tank (tank-x T0) TANK-LEFT)))
(check-expect (handle-key G0    "up") G0)


;; (define (handle-key g ke) SG) ; stub

(define (handle-key g ke)
  (cond 
    [(key=? ke " ") 
     (make-game (game-invaders g) 
                (cons (make-missile (tank-x (game-tank g)) MISSILE-POSY)  (game-missiles g)) 
                (game-tank g))]

    [(key=? ke "right") 
     (make-game (game-invaders g) 
                (game-missiles g) 
                (make-tank (tank-x (game-tank g))  TANK-RIGHT)
                )]

    [(key=? ke "left") 
     (make-game (game-invaders g) 
                (game-missiles g) 
                (make-tank (tank-x (game-tank g)) TANK-LEFT)
                )]
    [else 
     g]))


;; Game -> Boolean
;; produces true when the y of one of the ivnaders is >= HEIGHT (land)

(check-expect (handle-stop G0) false)
(check-expect (handle-stop G1) false)
(check-expect (handle-stop G2) false)
(check-expect (handle-stop G3) true)

;; (define (handle-stop g) false) ; stub

(define (handle-stop g) (landed-invader? (game-invaders g)))

;; ListOfInvaders -> Boolean
;; produces true when the y of one of the ivnaders is >= HEIGHT (land)


(check-expect (landed-invader? (list (make-invader 0 0 -1))) false)
(check-expect (landed-invader? (list (make-invader 150 150 1))) false)
(check-expect (landed-invader? (list (make-invader WIDTH HEIGHT 10))) true)
(check-expect (landed-invader? (list (make-invader 0 HEIGHT -10))) true)
(check-expect (landed-invader? (list (make-invader WIDTH 0 10))) false)

;; (define (landed-invader? loi) false) ; stub

(define (landed-invader? loi)
  (cond
    [(empty? loi) false]
    [else
     (if (>= (invader-y (first loi)) HEIGHT)
         true
         (landed-invader? (rest loi)))])) 

