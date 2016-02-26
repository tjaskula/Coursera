## Reading data files
NEI <- readRDS("exdata-data-NEI_data/summarySCC_PM25.rds")
SCC <- readRDS("exdata-data-NEI_data/Source_Classification_Code.rds")

# Question 2
# Have total emissions from PM2.5 decreased in the Baltimore City, Maryland (𝚏𝚒𝚙𝚜 == "𝟸𝟺𝟻𝟷𝟶") from 1999 
# to 2008? Use the base plotting system to make a plot answering this question.

isBaltimore <- NEI$fips == "24510"
baltimore <- subset(NEI, isBaltimore)
rm(isBaltimore)

totalEmissionByYear <- with(baltimore, tapply(Emissions, year, sum, na.rm = TRUE))

plot(x = names(totalEmissionByYear), y = totalEmissionByYear, 
     type = "p", pch = 16, col = "green", axes = FALSE,
     ylab = "PM2.5 Emission (in tons)", xlab = "Year", 
     main = "Total PM2.5 Emission for Baltimore from 1999 to 2008")
lines(x = names(totalEmissionByYear), y = totalEmissionByYear, col = "blue")
axis(2) # x axis
axis(side = 1, at = names(totalEmissionByYear)) # y axis
box()