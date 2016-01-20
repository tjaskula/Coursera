library(jpeg)

download <- function(url, fileName, ...) {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  download.file(url, destfile = fileName, ...)
  dateDownloaded <- date()
}

#question 1
question1 <- function() {
  
  if (!file.exists("./data/agri.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv", "./data/agri.csv")
  }
  
  df <- read.table("./data/agri.csv", sep = ",", header = TRUE)

  agricultureLogical <- c(!is.na(df$ACR) & df$ACR >= 3 & !is.na(df$AGS) & df$AGS >= 6)
  which(agricultureLogical)
}

#question 2
question2 <- function() {
  
  if (!file.exists("./data/jeff.jpg")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fjeff.jpg", "./data/jeff.jpg", mode = 'wb')
  }
  
  img <- readJPEG("./data/jeff.jpg", native=TRUE)
  quantile(img, probs = c(.30, .80))
}