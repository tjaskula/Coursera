download <- function(url, fileName, ...) {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  download.file(url, destfile = fileName, ...)
  dateDownloaded <- date()
}

question1 <- function() {
  
  if (!file.exists("./data/agri.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv", "./data/agri.csv")
  }
  
  df <- read.table("./data/agri.csv", sep = ",", header = TRUE)
  
  res <- strsplit(names(df), "wgtp")
  res[123]
}

#question 2
question2 <- function() {
  
  if (!file.exists("./data/FGDP.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FGDP.csv", "./data/FGDP.csv", mode = 'wb')
  }
  
  df <- read.csv("./data/FGDP.csv", sep = ",", header = FALSE, skip = 5)
  df$V5 <- as.numeric(gsub(",", "", df$V5))
  mean(df[1:190, "V5"])
}