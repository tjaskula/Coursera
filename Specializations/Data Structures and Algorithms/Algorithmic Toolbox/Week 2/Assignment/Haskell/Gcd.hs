{-# LANGUAGE BangPatterns #-}
import System.IO
import Control.Monad

main :: IO ()
main = do
    hSetBuffering stdout NoBuffering

    input_line <- getLine
    let split :: Eq a => a -> [a] -> [[a]]
        split d [] = []
        split d s = x : split d (drop 1 y) where (x,y) = span (/= d) s

    let splitted = split ' ' input_line
    let numbers = map (read::String->Int) splitted

    let gcd a b = gcdIter a b 2 1
            where
                gcdIter !a !b !d !biggest | d > a || d > b = biggest
                                          | otherwise      = gcdIter a b (d + 1) (if mod a d == 0 && mod b d == 0 && d > biggest then d else biggest)

    let res = gcd (numbers!!0) (numbers!!1)

    
    putStrLn $ show res

    return ()