#!/bin/bash
for file in ../dump/test/pht/all/*
do
  find "$file/unparse/parsed" -name "*.pht" -exec cp {} "../src/test/resources/test/pht/all/${file:21}/unparse/parsed" \;
  find "$file/unparse/processed" -name "*.pht" -exec cp {} "../src/test/resources/test/pht/all/${file:21}/unparse/processed" \;
done
for file in ../dump/test/siberia/all/*
do
  find "$file/unparse/parsed" -name "*.pht" -exec cp {} "../src/test/resources/test/siberia/all/${file:24}/unparse/parsed" \;
  find "$file/unparse/processed" -name "*.pht" -exec cp {} "../src/test/resources/test/siberia/all/${file:24}/unparse/processed" \;
done