## test_that (Wickham) unit test - Programming Assignment 1, Part 2, complete.R

library("testthat")
context("Read a directory of files, and report the number of complete cases in each file")

source("test-complete-df.r")
test_that("match the example output for this function", {
  expect_equal(complete("specdata",                 1 ), df1)
  expect_equal(complete("specdata", c(2, 4, 8, 10, 12)), df2)
  expect_equal(complete("specdata",             30:25 ), df3)
})