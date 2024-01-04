#!/bin/bash
for file in ../dump/test/pht/all/*
do
  find "$file" -name "*.pht" -exec cp {} "../src/test/resources/test/pht/all/${file:21}/unparse" \;
done
for file in ../dump/test/siberia/all/*
do
  find "$file" -name "*.pht" -exec cp {} "../src/test/resources/test/siberia/all/${file:24}/unparse" \;
done