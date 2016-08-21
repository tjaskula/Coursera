import System.IO
import Control.Monad
--import Data.List
--import Data.List.Split

main :: IO ()
main = do
    hSetBuffering stdout NoBuffering -- DO NOT REMOVE
    
    -- Auto-generated code below aims at helping you parse
    -- the standard input according to the problem statement.
    input_line <- getLine

    let split :: Eq a => a -> [a] -> [[a]]
        split d [] = []
        split d s = x : split d (drop 1 y) where (x,y) = span (/= d) s

    --let splitted = splitOn " " input_line
    let splitted = split ' ' input_line
    let numbers = map (read::String->Int) splitted
    
    let result = sum(numbers)

    putStrLn (show result)

    return ()