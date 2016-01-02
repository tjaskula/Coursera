# Parameters : the 2-character abbreviated name of a state and an outcome name.
# returns a character vector with the name of the hospital that has the best (i.e. lowest) 
# 30-day mortality for the specied outcome in that state.
best <- function(state, outcome) {
  ## Read outcome data
  df <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
  
  ## Check that state and outcome are valid
  if ((state %in% df$State) != TRUE)
    stop("invalid state")
  if (outcome != "heart attack" && outcome != "heart failure" && outcome != "pneumonia")
    stop("invalid outcome")
  
  simpleCap <- function(x) {
    s <- strsplit(x, " ")[[1]]
    paste(toupper(substring(s, 1, 1)), substring(s, 2),
          sep = "", collapse = " ")
  }
  
  ## Return hospital name in that state with lowest 30-day death
  colSubName <- gsub(" ", ".", simpleCap(outcome))
  colName <- paste("Hospital.30.Day.Death..Mortality..Rates.from.", colSubName, sep="")
  
  df <- df[df$State == state, ]
  df[, colName] <- as.numeric(df[, colName])
  df <- df[!is.na(df[colName]), ]
  df[order(df[colName], df$Hospital.Name), ][1, "Hospital.Name"]
  
  ## rate
}