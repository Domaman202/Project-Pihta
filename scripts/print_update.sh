#!/bin/bash
for file in ../dump/test/pht/all/*
do
  cp -a $file/print/. ../src/test/resources/test/pht/all/${file:21}/print
done
for file in ../dump/test/siberia/all/*
do
  cp -a $file/print/. ../src/test/resources/test/siberia/all/${file:24}/print
done