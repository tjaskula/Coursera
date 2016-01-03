rankall <- function(outcome, num = "best") {
  ## Read outcome data
  df <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
  
  ## Check that state and outcome are valid
  if (outcome != "heart attack" && outcome != "heart failure" && outcome != "pneumonia")
    stop("invalid outcome")
  
  simpleCap <- function(x) {
    s <- strsplit(x, " ")[[1]]
    paste(toupper(substring(s, 1, 1)), substring(s, 2),
          sep = "", collapse = " ")
  }
  
  ## For each state, find the hospital of the given rank
  ## Return a data frame with the hospital names and the
  ## (abbreviated) state name
  colSubName <- gsub(" ", ".", simpleCap(outcome))
  colName <- paste("Hospital.30.Day.Death..Mortality..Rates.from.", colSubName, sep="")
  
  states <- unique(df$State)
  states <- states[order(states)]
  
  data <- data.frame(hospital = character(), s = character(), stringsAsFactors = F)
  for (state in states) {
    stateDf <- df[df$State == state, ]
    stateDf[, colName] <- as.numeric(stateDf[, colName])
    stateDf <- stateDf[!is.na(stateDf[colName]), ]
    
    indx <- NULL
    if (num == "best")
      indx <- 1
    else if (num == "worst")
      indx <- nrow(stateDf)
    else
      indx <- num
    
    hospital <- stateDf[order(stateDf[colName], stateDf$Hospital.Name), ][indx, "Hospital.Name"]
    newrow <- c(hospital = hospital, state = state)
    data <- rbind(data, data.frame(as.list(newrow), stringsAsFactors=F))
  }
  rownames(data) <- states
  data
}