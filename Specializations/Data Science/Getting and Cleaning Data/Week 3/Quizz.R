library(jpeg)
library(Hmisc)

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

#question 3
question3 <- function() {
  
  if (!file.exists("./data/FGDP.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FGDP.csv", "./data/FGDP.csv")
  }
  
  if (!file.exists("./data/FEDSTATS_Country.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FEDSTATS_Country.csv", "./data/FEDSTATS_Country.csv")
  }
  
  df <- read.csv("./data/FGDP.csv", sep = ",", header = FALSE, skip = 5)
  df2 <- read.csv("./data/FEDSTATS_Country.csv", sep = ",", header = TRUE)
  merged <- merge(df, df2, by.x = "V1", by.y = "CountryCode", all = TRUE)
  merged$V2 <- as.numeric(as.character(merged$V2))
  merged <- merged[(merged$V2 != "" & !(is.na(merged$V2))),]
  merged <- merged[order(merged$V2, decreasing = TRUE),]
  
  merged
}

#question 4. Execute question3() to get the data.frame to pass in
question4 <- function(df) {
  df1 <- df[(df$Income.Group %in% "High income: OECD"),]
  df2 <- df[(df$Income.Group %in% "High income: nonOECD"),]
  c(mean(df1$V2), mean(df2$V2))
}

#question 5. Execute question3() to get the data.frame to pass in
question5 <- function(df) {
  df$GDPgroups <- cut2(df$V2, g=5)
  table(df$GDPgroups, df$Income.Group)
}