{- 
This program displays the evolving boards during a game of Threes! 
v1.0, Lyndon While, 23/4/14 
-} 


import System 
import List 
import Char 
import Control.Monad 


type Tile   = Int 
type Square = Tile -- 0 represents blank 
type Board = [[Square]] 

type Point    = (Float, Float) 
type Color    = (Int, Int, Int) 
type Polygon  = [Point] 
type ColoredP = (Color, Polygon) 


side :: Num a => a 
side = 100 

functions 'L' = (id,          id,        map reverse . reverse, id) 
functions 'R' = (map reverse, id,        id,                    id) 
functions 'U' = (transpose,   id,        transpose,             reverse) 
functions 'D' = (map reverse, transpose, reverse,               transpose) 

outfile s    = concat ["X", filter isDigit s, ".svg"] 
movefile d s = concat [x, "_", show d, y] 
               where (x, y) = break (== '.') s 


main = do boardfile <- putStr " Input file please: " >> getLine 
          cs <- readFile boardfile 
          let (bs, _ : ps) = splitAt 4 $ drop 2 $ lines cs 
          let b = map (map read . words) bs 
          let outf = outfile boardfile
          writeFile outf $ mkFile b 
          planfile  <- putStr "Output file please: " >> getLine 
          cs <- readFile planfile 
          (_, b) <- foldM (g outf) (False, b) $ zip3 [1 ..] 
                                                     (filter (`elem` "LRUD") $ concat $ drop 2 $ lines cs) 
                                                     (concat $ map (map read . words) ps) 
          putStr "\nThe final board is worth " 
          print $ sum $ map (sum . map scoresq) b 
       where g bf (False, b') (k, m, p) = main' b' p m k bf 
             g _  (True,  b') _         = return (True, b') 

main' b p d n bf = do let [b', x'] = map (g . h) [b, (j . f) (mvBoard ((f . j) b))] 
                      let rs = [(rx, k) | (rx, k, rb) <- zip3 x' [0 ..] b', rx /= rb] 
                      if null rs 
                        then return (True, b) 
                        else do let (r, k) = minimum rs 
                                let (xs, _ : ys) = splitAt k x' 
                                let b' = (h . g) $ concat [xs, (p : tail r) : ys] 
                                mf $ mkFile b' 
                                return (False, b') 
               where (f, j, g, h) = functions d 
                     mf           = writeFile $ movefile n bf 

mvBoard b = [mvRow m m' ys | (ys, (m, m')) <- zip b $ canmove b] 

mvRow m m' xs | m  < m' && m  < 3 = concat [ys, zs] ++ [0] 
              | m' < m  && m' < 3 = concat [init us, [last us + v], vs] ++ [0] 
              | otherwise         = xs 
                where (ys, 0 : zs) = splitAt m  xs 
                      (us, v : vs) = splitAt (m' + 1) xs 

canmove b = [(blank x, dupl x) | x <- b] 
                where blank x = 4 - length (dropWhile (/= 0) x) 
                      dupl [x]           = 3 
                      dupl (x : x' : xs) | p (x, x') = 2 - length xs 
                                         | otherwise = dupl (x' : xs) 
                      p (x, y) = sort [x, y] == [1, 2] || x >= 3 && x == y 

scoresq :: Int -> Int 
scoresq x | x <= 2 = (x + 1) `div` 2 
          | x >= 3 = round (3 ** (logBase 2 (fromIntegral x / 3) + 1)) 


-- display code 

mkFile b = writePolygons [x | (j, ss) <- zip [0 ..] b, (i, s)  <- zip [0 ..] ss, 
                              x <- [writePolygon $ mkSquare i j s, text i j s]] 

mkSquare :: Float -> Float -> Square -> ColoredP 
mkSquare x y s = (colours !! (min 3 s + 1), [(x', y'), (x' + side, y'), (x' + side, y' + side), (x', y' + side), (x', y')]) 
                 where x' = (x + 1) * side 
                       y' = (y + 1) * side 

text :: Float -> Float -> Square -> String 
text x y 0 = "" 
text x y s = concat ["<text x=\"", show ((x + 1) * side + (4 - genericLength s') * 14 - 2), 
                     "\" y=\"", show ((y + 2) * side - 35), 
                     "\" font-family=\"Verdana\" font-size=\"40\" fill=\"black\">", s', "</text>"] 
             where s' = show s 

writePolygons :: [String] -> String 
writePolygons p = unlines ["<svg xmlns=\"http://www.w3.org/2000/svg\">", unlines p, "</svg>"] 

writePolygon :: ColoredP -> String 
writePolygon (c, p) = concat ["<polygon points=\"", concatMap writePoint p, "\" style=\"fill:", 
                              writeColor c, ";stroke:", writeColor (head colours), ";stroke-width:2\"/>"] 

writePoint :: Point -> String 
writePoint (x, y) = concat [show x, ",", show y, " "] 

writeColor :: Color -> String 
writeColor (r, g, b) = concat ["rgb(", show r, ",", show g, ",", show b, ")"] 

colours :: [Color] 
colours = [(0,0,0), (230,230,230), (0,255,255), (255,150,255), (100,255,100)] 
--         black    grey           cyan         pink           green 