## Reading data files
NEI <- readRDS("exdata-data-NEI_data/summarySCC_PM25.rds")
SCC <- readRDS("exdata-data-NEI_data/Source_Classification_Code.rds")

# Question 5
# How have emissions from motor vehicle sources changed from 1999–2008 in Baltimore City?

motorVehicles <- SCC[grep("vehicle", SCC$EI.Sector, ignore.case = TRUE), "SCC"]

isBaltimoreAndVehicle <- NEI$fips == "24510" & NEI$SCC %in% motorVehicles
baltimore <- subset(NEI, isBaltimoreAndVehicle)
rm(isBaltimoreAndVehicle)

totalEmissionByYear <- with(baltimore, tapply(Emissions, year, sum, na.rm = TRUE))

plot(x = names(totalEmissionByYear), y = totalEmissionByYear, 
     type = "p", pch = 16, col = "green", axes = FALSE,
     ylab = "PM2.5 Emission (in tons)", xlab = "Year", 
     main = "Motor vehicle related PM2.5 Emission in Baltimore from 1999 to 2008")
lines(x = names(totalEmissionByYear), y = totalEmissionByYear, col = "blue")
axis(2) # x axis
axis(side = 1, at = names(totalEmissionByYear)) # y axis
box()