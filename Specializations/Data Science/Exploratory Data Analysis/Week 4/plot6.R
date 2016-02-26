## Reading data files
NEI <- readRDS("exdata-data-NEI_data/summarySCC_PM25.rds")
SCC <- readRDS("exdata-data-NEI_data/Source_Classification_Code.rds")

# Question 6
# Compare emissions from motor vehicle sources in Baltimore City with emissions 
# from motor vehicle sources in Los Angeles County, California (fips == "06037").
# Which city has seen greater changes over time in motor vehicle emissions?

motorVehicles <- SCC[grep("vehicle", SCC$EI.Sector, ignore.case = TRUE), "SCC"]

isBaltimoreAndVehicle <- NEI$fips == "24510" & NEI$SCC %in% motorVehicles
isLaAndVehicle <- NEI$fips == "06037" & NEI$SCC %in% motorVehicles
baltimore <- subset(NEI, isBaltimoreAndVehicle)
la <- subset(NEI, isLaAndVehicle)
rm(isBaltimoreAndVehicle)
rm(isLaAndVehicle)

emissionBaltimore <- with(baltimore, tapply(Emissions, year, sum, na.rm = TRUE))
emissionLa <- with(la, tapply(Emissions, year, sum, na.rm = TRUE))

rng <- range(emissionBaltimore[1:4], emissionLa[1:4])

plot(x = names(emissionBaltimore), y = emissionBaltimore, 
     type = "p", pch = 16, col = "blue", axes = FALSE, panel.first = grid(),
     ylab = "PM2.5 Emission (in tons)", xlab = "Year", ylim = rng, 
     main = "Motor vehicle related PM2.5 Emission in LA & Baltimore from 1999 to 2008")
points(x = names(emissionLa), y = emissionLa, pch = 16, col = "green")
lines(x = names(emissionBaltimore), y = emissionBaltimore, col = "blue")
lines(x = names(emissionLa), y = emissionLa, col = "green")
legend("right", legend = c("LA", "Baltimore"), pch = 20, lty=1, col = c("green", "blue"), title = "City")
axis(2) # x axis
axis(side = 1, at = names(emissionBaltimore)) # y axis
box()