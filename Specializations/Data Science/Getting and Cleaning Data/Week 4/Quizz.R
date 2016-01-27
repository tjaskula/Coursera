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

#question 4
question4 <- function() {
  
  if (!file.exists("./data/FGDP.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FGDP.csv", "./data/FGDP.csv")
  }
  
  if (!file.exists("./data/FEDSTATS_Country.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FEDSTATS_Country.csv", "./data/FEDSTATS_Country.csv")
  }
  
  df <- read.csv("./data/FGDP.csv", sep = ",", header = FALSE, skip = 5)
  df2 <- read.csv("./data/FEDSTATS_Country.csv", sep = ",", header = TRUE)
  merged <- merge(df, df2, by.x = "V1", by.y = "CountryCode", all = TRUE)
  
  merged <- merged[grep("^Fiscal year.*June.*", merged$Special.Notes),]
  
  nrow(merged)
}

# question 5
question5 <- function() {
  library(quantmod)
  amzn = getSymbols("AMZN",auto.assign=FALSE)
  filtered <- amzn['2012']
  lyear <- nrow(filtered)
  lmon <- nrow(filtered[.indexwday(filtered)==1]) # Mondays
  c(lyear, lmon)
}