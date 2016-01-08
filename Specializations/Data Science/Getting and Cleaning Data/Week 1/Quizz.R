question1Download <- function() {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  fileUrl <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv"
  download.file(fileUrl, destfile = "./data/cameras.csv", method = "curl")
  dateDownloaded <- date()
}

question1ReadFile <- function() {
  cameraData <- read.table("./data/cameras.csv", sep = ",", header = TRUE)
  head(cameraData)
}