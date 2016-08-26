import System.IO
import Control.Monad

main :: IO ()
main = do
    hSetBuffering stdout NoBuffering -- DO NOT REMOVE
    
    -- Auto-generated code below aims at helping you parse
    -- the standard input according to the problem statement.
    input_line <- getLine
    input_line <- getLine

    let split :: Eq a => a -> [a] -> [[a]]
        split d [] = []
        split d s = x : split d (drop 1 y) where (x,y) = span (/= d) s

    let splitted = split ' ' input_line
    let numbers = map (read::String->Int) splitted

    let maximum2 :: Ord a => [a] -> (a,a)
        maximum2 [] = undefined
        maximum2 [x] = undefined
        maximum2 [x1, x2] = (x1, x2)
        maximum2 (x1 : x2 : x3 : xs)
          | x2 > x1 = maximum2 (x2 : x1 : x3 : xs)
          | x3 > x1 = maximum2 (x3 : x1 : xs)
          | x3 > x2 = maximum2 (x1 : x3 : xs)
          | otherwise = maximum2 (x1 : x2 : xs)

    let tuples = maximum2 numbers
    let res = (fst tuples) * (snd tuples)

    putStrLn (show res)

    return ()