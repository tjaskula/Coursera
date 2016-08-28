import System.IO
import Control.Monad

main :: IO ()
main = do
    hSetBuffering stdout NoBuffering

    input_line <- getLine
    let readInt :: String -> Int
        readInt = read
    let n = readInt input_line
    
    let fibonacci 0 = 0
        fibonacci 1 = 1
        fibonacci n =  mod (fibonacci(n - 1) + fibonacci(n - 2)) 10

    let res = fibonacci n
    putStrLn $ show res

    return ()