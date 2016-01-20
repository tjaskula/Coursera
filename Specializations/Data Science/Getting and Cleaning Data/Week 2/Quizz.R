library(httr)
library(httpuv) # only needed in R Studio
library(base64enc)
library(sqldf)
library(XML)
library(httr)

download <- function(url, fileName) {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  download.file(url, destfile = fileName)
  dateDownloaded <- date()
}

# question 1
question1 <- function() {
  githubapp <- oauth_app("github", key="c83e3dbb33b479d39a84", secret="6fa2b24bf58095a34065f3707076751bad04cf59")
  github_token <- oauth2.0_token(oauth_endpoints("github"), githubapp)
  jeffrepo <- GET("https://api.github.com/users/jtleek/repos", sig)
  # or with proxy
  jeffrepo <- GET("https://api.github.com/users/jtleek/repos", use_proxy(url = "localhost", port = 9978, username = "T.Jaskula", password = "Fd-seven-04$"))
  json <- content(jeffrepo)
  json2 <- jsonlite::fromJSON(jsonlite::toJSON(json))
  json2[grep("datasharing", json2$name), "created_at"] 
}

#question 2
question2 <- function() {
  
  if (!file.exists("./data/cameras.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06pid.csv", "./data/cameras.csv")
  }
  
  acs <- read.table("./data/cameras.csv", sep = ",", header = TRUE)
  
  sqldf("select pwgtp1 from acs where AGEP < 50")
}


#question 3
question3 <- function() {
  
  if (!file.exists("./data/cameras.csv")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06pid.csv", "./data/cameras.csv")
  }
  
  acs <- read.table("./data/cameras.csv", sep = ",", header = TRUE)
  
  sqldf("select distinct AGEP from acs")
}

#question 4
question4 <- function() {
  
  html <- GET("http://biostat.jhsph.edu/~jleek/contact.html", use_proxy("localhost", 9978))
  asText <- content(html, as = "text")
  parsedHtml <- htmlParse(asText)
  
  splitted <- strsplit(asText, "\n")
  
  lines <- c(10, 20, 30, 100)
  
  lengths <- c()
  for (line in lines) {
    lengths <- c(lengths, nchar(splitted[[1]][line]))
  }
  
  lengths
  
}

#question 5
question5 <- function() {
  
  if (!file.exists("./data/wksst8110.fwf")) {
    download("https://d396qusza40orc.cloudfront.net/getdata%2Fwksst8110.for", "./data/wksst8110.fwf")
  }
  
  library(readr)
  
  df <- read_fwf(file="./data/wksst8110.fwf", skip=4, fwf_widths(c(12, 7, 4, 9, 4, 9, 4, 9, 4)))
  sum(df[,4])
}