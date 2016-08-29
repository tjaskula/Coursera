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

    let lcm a b = lcmIter a b 1
            where
                lcmIter !a !b !l | l > a * b                    = a * b
                                 | mod l a == 0 && mod l b == 0 = l
                                 | otherwise                    = lcmIter a b (l + 1)

    let res = lcm (numbers!!0) (numbers!!1)

    
    putStrLn $ show res

    return ()