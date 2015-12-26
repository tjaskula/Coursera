complete <- function(directory, id = 1:332) {
  ## 'directory' is a character vector of length 1 indicating
  ## the location of the CSV files
  
  ## 'id' is an integer vector indicating the monitor ID numbers
  ## to be used
  
  ## Return a data frame of the form:
  ## id nobs
  ## 1  117
  ## 2  1041
  ## ...
  ## where 'id' is the monitor ID number and 'nobs' is the
  ## number of complete cases
  readData <- function(id) {
    path = paste(directory, paste(id, ".csv", sep=""), sep = "/")
    df <- read.csv(path, sep=",")
    df
  } 
  
  data = data.frame()
  for (i in id) {
    formatedId <- sprintf("%03d", i)
    v <- readData(formatedId)
    goodCount <- sum(complete.cases(v))
    newrow <- c(i, goodCount)
    data <- rbind(data, newrow)
    cols <- c("id","nobs")
    colnames(data) <- cols
  }
  data
}