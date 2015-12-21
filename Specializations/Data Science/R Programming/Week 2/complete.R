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

  formatedId <- sprintf("%03d", id)
  idPattern <- paste(formatedId, collapse="|")
  filenames <- list.files(path = directory, pattern="*.csv", full.names=TRUE)
  filenames <- filenames[grepl(idPattern, filenames)]
  ldf <- lapply(filenames, read.csv)
  
  df <- do.call("rbind", ldf)
  good <- complete.cases(df)
  df <- df[good, ]
  df <- aggregate(df$ID, by = list(id = df$ID), FUN = length)
  names(df)[names(df)=="x"] <- "nobs"
  o <- order(id)
  df <- df[with(df, o),]
  rownames(df) <- 1:nrow(df)
  df
  
}