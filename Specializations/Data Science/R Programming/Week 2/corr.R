corr <- function(directory, threshold = 0) {
  ## 'directory' is a character vector of length 1 indicating the location of
  ## the CSV files
  
  ## 'threshold' is a numeric vector of length 1 indicating the number of
  ## completely observed observations (on all variables) required to compute
  ## the correlation between nitrate and sulfate; the default is 0
  
  ## Return a numeric vector of correlations
  
  df = complete(directory)
  df <- df[df$nobs > threshold,]
  
  readData <- function(id) {
    path = paste(directory, paste(id, ".csv", sep=""), sep = "/")
    df <- read.csv(path, sep=",")
    df
  }
  
  data <- numeric()
  for (i in df[,1]) {
    formatedId <- sprintf("%03d", i)
    v <- readData(formatedId)
    good <- complete.cases(v)
    v <- v[good,]
    data <- c(data, cor(v$sulfate, v$nitrate))
  }
  data
}