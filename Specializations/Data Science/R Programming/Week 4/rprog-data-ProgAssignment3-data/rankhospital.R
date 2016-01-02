rankhospital <- function(state, outcome, num = "best") {
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
  
  ## Return hospital name in that state with the given rank
  ## 30-day death rate
  colSubName <- gsub(" ", ".", simpleCap(outcome))
  colName <- paste("Hospital.30.Day.Death..Mortality..Rates.from.", colSubName, sep="")
  
  df <- df[df$State == state, ]
  df[, colName] <- as.numeric(df[, colName])
  df <- df[!is.na(df[colName]), ]
  
  indx <- NULL
  if (num == "best")
    indx <- 1
  else if (num == "worst")
    indx <- nrow(df)
  else
    indx <- num
  
  df[order(df[colName], df$Hospital.Name), ][indx, "Hospital.Name"]
}